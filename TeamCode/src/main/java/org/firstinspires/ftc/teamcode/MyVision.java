package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.utility.RobotConfig.imu;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.ProfileParams;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilderParams;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
//TO DO:
// line 100 do the initial poses with the limelight
//do the remembering the pattern from the cases 21-23

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.autonomous.FirstRoadRunnerAuton;

import java.util.EnumSet;
import java.util.List;
import java.util.Vector;

public class MyVision {
    private Limelight3A limelight;
    private IMU imu;
    private int rememberTagID;
    private DcMotor beltMotor, flyingWheelMotor;

    public enum MyVisionState {none, pattern, goal}

    // Limelight calibration values (tune these for your robot)
    private final double limelightMountAngleDegrees = 25.0; //var?
    private final double limelightLensHeightInches = 20.0; //var?
    private final double tagHeightInches = 60.0; //var?

    ;

    public EnumSet<MyVisionState> activeStates = EnumSet.of(MyVisionState.none, MyVisionState.pattern, MyVisionState.goal);


    public void limelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // 100 times per second
        limelight.start();
        limelight.pipelineSwitch(8); // ???

        imu = hardwareMap.get(IMU.class, "imu"); // field orientation through imu
        RevHubOrientationOnRobot revHubOrientationOnRobot =
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD, //var?
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD); //var?
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    public void setActiveStatesMyVisionStates(EnumSet<MyVisionState> states) {
        this.activeStates = EnumSet.copyOf(states);
    }


    public void setLimelight() {
        activeStates.add(MyVisionState.none);


        LLResult result = limelight.getLatestResult();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                int id = fiducial.getFiducialId();
                telemetry.addData("the ID tag is:", fiducial.getFiducialId());

                rememberTagID = id;

                switch (id) {
                    case 21:
                        telemetry.addLine("id 21 GPP");
                        activeStates.add(MyVisionState.pattern);
                        break;

                    case 22:
                        telemetry.addLine("id 22 PGP");
                        activeStates.add(MyVisionState.pattern);
                        break;

                    case 23:
                        telemetry.addLine("id 23 PPG");
                        activeStates.add(MyVisionState.pattern);
                        break;

                    case 20:
                        telemetry.addLine("BLUE");
                        activeStates.add(MyVisionState.goal);
                        break;

                    case 24:
                        telemetry.addLine("RED");
                        activeStates.add(MyVisionState.goal);
                        break;
                }
            }
        } else
            telemetry.addData("Limelight", "No Targets");
        }

    public class GoToBlue implements Action {
        private boolean initialized = false;


        public void run() { // do the initial pose (get it throught the pose 3d limelight) but otherwise done
            MyShooterOne myShooterOne = new MyShooterOne(hardwareMap);
            //MecanumDrive mecanumDrive = new MecanumDrive(HardwareMap hardwareMap);
            activeStates.add(MyVisionState.goal);

            LLResult llResult = limelight.getLatestResult();
            if (llResult != null && llResult.isValid()) {
                Pose3D botPose = llResult.getBotpose_MT2();
                double tx = botPose.getPosition().x; // horizontal offset (degrees)
                double ty = botPose.getPosition().y; // vertical offset (degrees)

                double xInches = tx * 39.3701; //convertion to the roadrunner
                double yInches = ty * 39.3701;

                double yawDegrees = botPose.getOrientation().getYaw();
                double yawRadians = Math.toRadians(yawDegrees);

                Pose2d rrPose = new Pose2d(xInches, yInches, yawRadians);


                // distance calculation using the angle (limelight documentation)
                double angleToGoalDegrees = limelightMountAngleDegrees + ty;
                double angleToGoalRadians = Math.toRadians(angleToGoalDegrees); //convertion to radians
                double distanceInches = (tagHeightInches - limelightLensHeightInches) /
                        Math.tan(angleToGoalRadians);
            }

            MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));


            if (!initialized) {
                if (rememberTagID == 20) {
                    telemetry.addLine("Going to Blue target!");
                    telemetry.update();
                    Pose2d currentPose = drive.getPoseEstimate();

                    double x = currentPose.position.x;    // or currentPose.getX()
                    double y = currentPose.position.y;    // or currentPose.getY()
                    double heading = currentPose.heading.toDouble(); // or currentPose.getHeading()


                    /*new TrajectoryBuilder(new TrajectoryBuilderParams(eps,
                            new ProfileParams(dispResolution, angResolution, eps)),
                            beginPose, beginEndVel,
                            baseVelConstraint, baseAccelConstraint,
                            pose -> new Pose2dDual<>(
                                    pose.position.x, pose.position.y.unaryMinus(), pose.heading.inverse()));*/

                    com.acmerobotics.roadrunner.ftc.Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(x, y, heading))
                                    .splineTo(new Vector2d(36, 36), Math.PI / 2)
                                    .build());
                    //customizable
                    Actions.runBlocking(myShooterOne.addState(MyShooterOne.MyShooterOneState.belt));
                    Actions.runBlocking(myShooterOne.addState(MyShooterOne.MyShooterOneState.shoot));
                }
            }
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return false;
        }
    }

            /* abstract class GoToRed implements Action {
                private boolean initialized = false;

                public void run() {
                    MyShooterOne myShooterOne = new MyShooterOne(hardwareMap);
                    activeStates.add(MyVisionState.goal);
                    MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
                    if (!initialized) {
                        if (rememberTagID == 20) {
                            telemetry.addLine("Going to Blue target!");
                            telemetry.update();

                            com.acmerobotics.roadrunner.ftc.Actions.runBlocking(
                                    drive.actionBuilder(new Pose2d(0, 0, 0))
                                            .splineTo(new Vector2d(-36, 36), Math.PI/2)
                                            .build());
                            //customizable
                            Actions.runBlocking(myShooterOne.addState(MyShooterOne.MyShooterOneState.belt));
                            Actions.runBlocking(myShooterOne.addState(MyShooterOne.MyShooterOneState.shoot));
                        }
                    }
                }

            }*/
}

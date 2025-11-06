package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.EnumSet;
import java.util.List;

public class MyVision {
    private Limelight3A limelight;
    private IMU imu;
    private HardwareMap hardwareMap;
    private int rememberTagID = -1;

    public enum MyVisionState {none, pattern, goal}

    // Limelight calibration values (tune these for your robot)
    private final double limelightMountAngleDegrees = 25.0;
    private final double limelightLensHeightInches = 20.0;
    private final double tagHeightInches = 60.0;

    public EnumSet<MyVisionState> activeStates = EnumSet.of(MyVisionState.none);

    public MyVision(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(8);

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot =
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    public void setActiveStatesMyVisionStates(EnumSet<MyVisionState> states) {
        this.activeStates = EnumSet.copyOf(states);
    }

    public int getRememberedTagID() {
        return rememberTagID;
    }

    public void updateLimelight() {
        LLResult result = limelight.getLatestResult();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                int id = fiducial.getFiducialId();
                rememberTagID = id;

                switch (id) {
                    case 21:
                        activeStates.add(MyVisionState.pattern);
                        break;
                    case 22:
                        activeStates.add(MyVisionState.pattern);
                        break;
                    case 23:
                        activeStates.add(MyVisionState.pattern);
                        break;
                    case 20:
                        activeStates.add(MyVisionState.goal);
                        break;
                    case 24:
                        activeStates.add(MyVisionState.goal);
                        break;
                }
            }
        }
    }

    public Pose2d getRobotPoseFromLimelight() {
        LLResult llResult = limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            Pose3D botPose = llResult.getBotpose_MT2();
            double xInches = botPose.getPosition().x * 39.3701;
            double yInches = botPose.getPosition().y * 39.3701;
            double yawRadians = Math.toRadians(botPose.getOrientation().getYaw());

            return new Pose2d(xInches, yInches, yawRadians);
        }
        return null;
    }

    public class GoToBlue implements Action {
        private boolean initialized = false;
        private MecanumDrive drive;

        public GoToBlue(MecanumDrive drive) {
            this.drive = drive;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!initialized) {
                initialized = true;

                if (rememberTagID == 20) {
                    MyShooterOne myShooterOne = new MyShooterOne(hardwareMap);

                    Pose2d currentPose = drive.localizer.getPose();

                    Actions.runBlocking(
                            drive.actionBuilder(currentPose)
                                    .splineTo(new Vector2d(36, 36), Math.PI / 2)
                                    .build());

                    myShooterOne.setActiveOneStates(EnumSet.of(
                            MyShooterOne.MyShooterOneState.belt,
                            MyShooterOne.MyShooterOneState.shoot
                    ));
                }
            }
            return false;
        }
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


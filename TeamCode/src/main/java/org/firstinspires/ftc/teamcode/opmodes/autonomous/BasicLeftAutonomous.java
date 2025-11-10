package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.utility.RobotConfig;
import org.firstinspires.ftc.teamcode.utility.teaminfo.InitialSide;

@Autonomous(name="Basic Autonomous - LEFT")
public class BasicLeftAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() {
        //initialize components
        Arm arm = new Arm(hardwareMap);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        arm.setArmExtensionPosition(0);
        arm.resetArm();
        arm.resetArmExtension();

        telemetry.addLine("waiting to start!");
        telemetry.update();

        while (!isStarted() && !isStopRequested()) {
            RobotConfig.initialSide = InitialSide.RIGHT;
//            if(gamepad1.b || gamepad2.b) RobotConfig.teamColor = TeamColor.RED;
//            if(gamepad1.x || gamepad2.x) RobotConfig.teamColor = TeamColor.BLUE;

            if(gamepad1.right_bumper || gamepad2.right_bumper) arm.resetArmExtension();
            if(gamepad1.left_bumper || gamepad2.left_bumper) arm.resetArm();

//            telemetry.addData("Team color", RobotConfig.teamColor);
            telemetry.addData("Initial side", RobotConfig.initialSide);
            telemetry.update();

            sleep(100);
        }

        //set the initial pose of drive
//        boolean isRed = RobotConfig.teamColor == TeamColor.RED;
        Pose2d initialPose;
//        //SOMEHOW X AND Y ARE FLIPPED
//        if (isRed) initialPose = new Pose2d(-24, 60, Math.toRadians(-90));
//        else initialPose = new Pose2d(24, -60, Math.toRadians(90));
        initialPose = new Pose2d(0, 0, 0);
        drive.pose = initialPose;

        //go to place
        Action tab1 = drive.actionBuilder(initialPose)
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(50, 20), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(55, 0), Math.toRadians(90))
                .build();

        waitForStart();

        //untangle the robot
        Actions.runBlocking(new SequentialAction(new SequentialAction(
//                arm.armExToPosition(300),
                arm.armToPosition(500)
        ))); //extend armEx
        arm.stopWrist(); //flip claw

        Actions.runBlocking(new SequentialAction(
                tab1,
                arm.armExToPosition(0),
                arm.armToPosition(5000)
        ));
    }
}

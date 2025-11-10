package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.components.Vision;

import org.firstinspires.ftc.teamcode.components.drive.MecanumDrive;

@Disabled
@TeleOp(name="Base TeleOp")
public class _BaseTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        //initialize components
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));


        //log data
        telemetry.addLine("waiting to start!");
        telemetry.update();
        waitForStart();

        //basic joystick control
        double speed = 1.0;
        while (opModeIsActive()) {
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y * speed,
                            -gamepad1.left_stick_x * speed
                    ),
                    -gamepad1.right_stick_x * speed
            ));

            drive.updatePoseEstimate();
        }
    }
}

package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.components.Control;
import org.firstinspires.ftc.teamcode.components.drive.MecanumDrive;

@TeleOp(name="Oct 7 Teleop Decode")
public class BasicTeleOp extends LinearOpMode {
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    @Override
    public void runOpMode() {
        //initialize components
       MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        Control control = new Control(hardwareMap);


        waitForStart();



        while (opModeIsActive()) {


            double speed = 1.0;

            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y * speed,
                            -gamepad1.left_stick_x * speed
                    ),
                    gamepad1.right_stick_x * speed
            ));

            drive.updatePoseEstimate();

            if(gamepad1.x){
                control.setArmExtensionPosition(1000);
            }

            if(gamepad1.b){
                control.setArmExtensionPosition(0);
            }


            if(gamepad1.a){
                control.runLauncher();
            }
            if(gamepad1.y){
                control.stopLauncher();
            }

            if(gamepad1.right_trigger > 0.75){
                control.setinLauncher(true);
            }else if (gamepad1.right_trigger < 0.75 && !gamepad1.right_bumper){
                control.setinLauncher(false);
            }else            if(gamepad1.right_bumper)
                control.setIntake(true,false);

            if(gamepad1.left_trigger > 0.75){
                control.setBelt(true);
            }else if (gamepad1.left_trigger < 0.75 && !gamepad1.left_bumper){
                control.setBelt(false);
            }else if(gamepad1.right_bumper)
                control.setBelt(true,false);

            control.update();

            telemetry.update();
        }
    }
}
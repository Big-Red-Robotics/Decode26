package org.firstinspires.ftc.teamcode.teleops;


import static org.firstinspires.ftc.teamcode.MyShooterOne.MyShooterOneState.belt;
import static org.firstinspires.ftc.teamcode.MyShooterOne.MyShooterOneState.intake;
import static org.firstinspires.ftc.teamcode.MyShooterOne.MyShooterOneState.shoot;
import static org.firstinspires.ftc.teamcode.MyVision.MyVisionState.none;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Chassis;
import org.firstinspires.ftc.teamcode.MyIntake;
import org.firstinspires.ftc.teamcode.MyShooterOne;
import org.firstinspires.ftc.teamcode.MyVision;

import java.util.EnumSet;

@TeleOp(name = "October 21 test teleop")
public class TeleOpOne extends LinearOpMode {

    @Override
    public void runOpMode() {
        Chassis chassis = new Chassis(hardwareMap);
        MyIntake myintake = new MyIntake(hardwareMap);
        MyVision myVision = new MyVision();
        MyShooterOne myShooterOne = new MyShooterOne(hardwareMap);

        double forwardSpeed, strafeSpeed, rotateSpeed;

        telemetry.addLine("press to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double left_y = gamepad1.left_stick_y;
            double left_x = gamepad1.left_stick_x;
            if (Math.abs(Math.atan2(Math.abs(left_x), Math.abs(left_y))) < Math.PI / 6.0 && left_y != 0) {
                strafeSpeed = 0;
            } else if (Math.abs(Math.atan2(Math.abs(left_x), Math.abs(left_y))) < Math.PI / 6.0 && left_y != 0) {
                forwardSpeed = 0;
            }
            // with the new pose bla bla bla


            if (gamepad2.x) myVision.activeStates.add(MyVision.MyVisionState.goal);
            if (gamepad2.left_bumper) {
                myShooterOne.activeOneStates.addAll(EnumSet.of(
                        intake,
                        belt
                ));
            }
            if (gamepad2.left_trigger > 0) {
                myShooterOne.activeOneStates.addAll(EnumSet.of(
                        MyShooterOne.MyShooterOneState.inversebelt,
                        MyShooterOne.MyShooterOneState.inverseintake
                ));
            }
            if (gamepad2.right_bumper) myShooterOne.activeOneStates.add(shoot);

            //add the luift on the gamepad1.y
        }


    }
}


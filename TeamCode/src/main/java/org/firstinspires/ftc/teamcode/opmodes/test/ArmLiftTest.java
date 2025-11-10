package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.Arm;

@TeleOp(name = "Arm Lift Test", group = "Test")
public class ArmLiftTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Arm arm = new Arm(hardwareMap);
        arm.resetArm();
        arm.resetArmExtension();

        telemetry.addLine("Arm lift test ready");
        telemetry.addLine("D-pad: Up=HIGH, Right=LOW, Left=VERY_LOW, Down=GROUND");
        telemetry.addLine("Left bumper: reset lift encoder");
        telemetry.addLine("Right bumper: reset arm extension");
        telemetry.update();

        int target = Arm.GROUND;
        int lastCommandedTarget = Integer.MIN_VALUE;
        boolean armPowerEnabled = false;

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                target = Arm.HIGH;
            } else if (gamepad1.dpad_right) {
                target = Arm.LOW;
            } else if (gamepad1.dpad_left) {
                target = Arm.VERY_LOW;
            } else if (gamepad1.dpad_down) {
                target = Arm.GROUND;
            }

            if (gamepad1.left_bumper) {
                arm.resetArm();
                target = Arm.GROUND;
                lastCommandedTarget = Integer.MIN_VALUE; // allow reset to reissue target
                armPowerEnabled = false;
            }

            if (gamepad1.right_bumper) {
                arm.resetArmExtension();
            }

            if (target != lastCommandedTarget) {
                arm.setArmPosition(target);
                arm.arm.setPower(1.0); // start the lift moving toward the new target
                armPowerEnabled = true;
                lastCommandedTarget = target;
            }

            if (armPowerEnabled && !arm.arm.isBusy()) {
                arm.arm.setPower(0.0);
                armPowerEnabled = false;
            }

            telemetry.addData("Lift target", arm.getArmTargetPosition());
            telemetry.addData("Lift position", arm.getArmPosition());
            telemetry.addData("Lift power", arm.getArmPower());
            telemetry.update();

            idle();
        }
    }
}

package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "skibidisigma67opmode", group = "Linear Opmode")
public class skibidisigma67rizz extends LinearOpMode {

    private DcMotor leftFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;

    private DcMotor beltMotor = null;
    private DcMotor shooterMotor = null;
    private DcMotor LliftMotor = null;
    private DcMotor RliftMotor = null;
    private CRServo intakeServo = null;

    double driveSpeedMultiplier = 1;
    double beltSpeed = 1.0;
    double intakePower = 1.0;

    int LIFT_HEIGHT = 3000;

    DcMotorSimple.Direction LEFT_SIDE_DIR = DcMotorSimple.Direction.REVERSE;
    DcMotorSimple.Direction RIGHT_SIDE_DIR = DcMotorSimple.Direction.FORWARD;
    DcMotorSimple.Direction BELT_DIR = DcMotorSimple.Direction.FORWARD;
    DcMotorSimple.Direction SHOOTER_DIR = DcMotorSimple.Direction.FORWARD;

    DcMotorSimple.Direction R_LIFT_DIR = DcMotorSimple.Direction.FORWARD;
    DcMotorSimple.Direction L_LIFT_DIR = DcMotorSimple.Direction.REVERSE;

    @Override
    public void runOpMode() {

        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        leftBack   = hardwareMap.get(DcMotor.class, "LB");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        rightBack  = hardwareMap.get(DcMotor.class, "RB");

        beltMotor = hardwareMap.get(DcMotor.class, "belt");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        intakeServo = hardwareMap.get(CRServo.class, "intake");
        LliftMotor = hardwareMap.get(DcMotor.class, "LliftMotor");
        RliftMotor = hardwareMap.get(DcMotor.class, "RliftMotor");

        leftFront.setDirection(LEFT_SIDE_DIR);
        leftBack.setDirection(LEFT_SIDE_DIR);
        rightFront.setDirection(RIGHT_SIDE_DIR);
        rightBack.setDirection(RIGHT_SIDE_DIR);
        beltMotor.setDirection(BELT_DIR);
        shooterMotor.setDirection(SHOOTER_DIR);

        RliftMotor.setDirection(R_LIFT_DIR);
        LliftMotor.setDirection(L_LIFT_DIR);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        beltMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        LliftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RliftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LliftMotor.setTargetPosition(0);
        RliftMotor.setTargetPosition(0);
        LliftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RliftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        RliftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LliftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            leftFront.setPower(frontLeftPower * driveSpeedMultiplier);
            leftBack.setPower(backLeftPower * driveSpeedMultiplier);
            rightFront.setPower(frontRightPower * driveSpeedMultiplier);
            rightBack.setPower(backRightPower * driveSpeedMultiplier);

            if (gamepad1.a) {
                shooterMotor.setPower(0.7);
            } else if (gamepad1.b) {
                shooterMotor.setPower(0.8);
            } else if (gamepad1.dpad_right) {
                shooterMotor.setPower(0.0);
            }

            if (gamepad1.x) {
                LliftMotor.setTargetPosition(LIFT_HEIGHT);
                RliftMotor.setTargetPosition(LIFT_HEIGHT);

                LliftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RliftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                LliftMotor.setPower(1.0);
                RliftMotor.setPower(1.0);
            }

            if (gamepad1.y) {
                LliftMotor.setTargetPosition(0);
                RliftMotor.setTargetPosition(0);

                LliftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RliftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                LliftMotor.setPower(0.8);
                RliftMotor.setPower(0.8);
            }

            if (gamepad1.right_trigger > 0.1) {
                beltMotor.setPower(beltSpeed);
            } else if (gamepad1.right_bumper) {
                beltMotor.setPower(-beltSpeed);
            } else {
                beltMotor.setPower(0);
            }

            if (gamepad1.left_trigger > 0.1) {
                intakeServo.setPower(intakePower);
            } else if (gamepad1.left_bumper) {
                intakeServo.setPower(-intakePower);
            } else {
                intakeServo.setPower(0);
            }

            telemetry.addData("liftup", LliftMotor.getTargetPosition());
            telemetry.addData("Lift c", LliftMotor.getCurrentPosition());
            telemetry.addData("belton", beltMotor.getPower());
            telemetry.addData("Shooter Pwr", shooterMotor.getPower());
            telemetry.update();
        }
    }
}
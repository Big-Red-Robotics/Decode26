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
    private DcMotor liftMotor = null;
    private DcMotor shooterMotor = null;
    private CRServo intakeServo = null;

    double driveSpeedMultiplier = 1;
    double liftSpeed = 1.0;
    double intakePower = 1.0;
    double shooterSpeed = 1.0;

    DcMotorSimple.Direction LEFT_SIDE_DIR = DcMotorSimple.Direction.REVERSE;
    DcMotorSimple.Direction RIGHT_SIDE_DIR = DcMotorSimple.Direction.FORWARD;
    DcMotorSimple.Direction LIFT_DIR = DcMotorSimple.Direction.FORWARD;
    DcMotorSimple.Direction SHOOTER_DIR = DcMotorSimple.Direction.FORWARD;

    @Override
    public void runOpMode() {

        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        leftBack   = hardwareMap.get(DcMotor.class, "LB");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        rightBack  = hardwareMap.get(DcMotor.class, "RB");

        liftMotor = hardwareMap.get(DcMotor.class, "lift");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        intakeServo = hardwareMap.get(CRServo.class, "intake");

        leftFront.setDirection(LEFT_SIDE_DIR);
        leftBack.setDirection(LEFT_SIDE_DIR);
        rightFront.setDirection(RIGHT_SIDE_DIR);
        rightBack.setDirection(RIGHT_SIDE_DIR);
        liftMotor.setDirection(LIFT_DIR);
        shooterMotor.setDirection(SHOOTER_DIR);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            double leftPower = -gamepad1.left_stick_y * driveSpeedMultiplier;
            double rightPower = -gamepad1.right_stick_y * driveSpeedMultiplier;

            leftFront.setPower(leftPower);
            leftBack.setPower(leftPower);
            rightFront.setPower(rightPower);
            rightBack.setPower(rightPower);

            if (gamepad1.y) {
                liftMotor.setPower(liftSpeed);
                intakeServo.setPower(intakePower);
                shooterMotor.setPower(shooterSpeed);
            } else if (gamepad1.x) {
                liftMotor.setPower(-liftSpeed);
                intakeServo.setPower(-intakePower);
                shooterMotor.setPower(0);
            } else {

                if (gamepad1.right_trigger > 0.1) {
                    liftMotor.setPower(liftSpeed);
                } else if (gamepad1.left_trigger > 0.1) {
                    liftMotor.setPower(-liftSpeed);
                } else {
                    liftMotor.setPower(0);
                }

                if (gamepad1.a) {
                    intakeServo.setPower(intakePower);
                } else if (gamepad1.b) {
                    intakeServo.setPower(-intakePower);
                } else {
                    intakeServo.setPower(0);
                }

                shooterMotor.setPower(0);
            }

            telemetry.addData("Lift Pwr", liftMotor.getPower());
            telemetry.addData("Intake Pwr", intakeServo.getPower());
            telemetry.addData("Shooter Pwr", shooterMotor.getPower());
            telemetry.update();
        }
    }
}

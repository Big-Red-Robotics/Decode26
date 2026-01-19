package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.components.drive.MecanumDrive;

/**
 * DECODE 2025-2026 Simple Autonomous - RED ALLIANCE
 *
 * A RELIABLE autonomous that focuses on guaranteed points:
 * - LEAVE the launch line: 3 points
 * - Shoot pre-loaded artifacts: 9 points (3 classified)
 * - Park in BASE: 10 points
 *
 * Total: 22 guaranteed points with minimal complexity
 */
@Autonomous(name = "DECODE Simple - Red", group = "DECODE")
public class DecodeSimpleAutonomousRed extends LinearOpMode {

    private DcMotor beltMotor = null;
    private DcMotor shooterMotor = null;
    private CRServo intakeServo = null;

    private static final double SHOOTER_POWER = 0.8;
    private static final double BELT_POWER = 1.0;
    private static final double INTAKE_POWER = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize hardware
        beltMotor = hardwareMap.get(DcMotor.class, "belt");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        intakeServo = hardwareMap.get(CRServo.class, "intake");

        beltMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        beltMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Starting pose (mirrored for Red Alliance)
        Pose2d startPose = new Pose2d(new Vector2d(-60, -36), Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        // Key positions (mirrored)
        Vector2d goalPosition = new Vector2d(-24, -36);
        Vector2d baseZone = new Vector2d(-60, -60);

        Action shooterOn = (telemetryPacket) -> {
            shooterMotor.setPower(SHOOTER_POWER);
            return false;
        };

        Action shooterOff = (telemetryPacket) -> {
            shooterMotor.setPower(0.0);
            return false;
        };

        telemetry.addLine("=== DECODE Simple Autonomous ===");
        telemetry.addLine("Red Alliance - RELIABLE MODE");
        telemetry.addLine("");
        telemetry.addLine("Guaranteed Points:");
        telemetry.addLine("  LEAVE: 3 pts");
        telemetry.addLine("  SHOOT: 9 pts");
        telemetry.addLine("  PARK:  10 pts");
        telemetry.addLine("  TOTAL: 22 pts");
        telemetry.addLine("");
        telemetry.addLine("Waiting for START...");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        // Start feeding systems
        intakeServo.setPower(INTAKE_POWER);
        beltMotor.setPower(BELT_POWER);

        // Simple, reliable trajectory (mirrored for Red)
        Action autonomousPath = drive.actionBuilder(startPose)
                // LEAVE the launch line (3 pts)
                .lineToX(-48)

                // Go to goal and SHOOT (9 pts)
                .splineToLinearHeading(new Pose2d(goalPosition, Math.toRadians(-45)), Math.toRadians(0))
                .stopAndAdd(shooterOn)
                .waitSeconds(0.5)   // Spin up
                .waitSeconds(3.0)   // Shoot all 3 artifacts with margin
                .stopAndAdd(shooterOff)

                // Park in BASE (10 pts)
                .splineToLinearHeading(new Pose2d(baseZone, Math.toRadians(180)), Math.toRadians(-135))

                .build();

        Actions.runBlocking(autonomousPath);

        // Stop all
        shooterMotor.setPower(0);
        beltMotor.setPower(0);
        intakeServo.setPower(0);

        telemetry.addLine("Autonomous Complete!");
        telemetry.addLine("Expected: 22 points");
        telemetry.update();
    }
}

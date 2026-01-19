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
 * DECODE 2025-2026 Autonomous - RED ALLIANCE
 *
 * Mirror of the Blue Alliance autonomous for Red side starting position.
 *
 * Scoring Strategy:
 * - LEAVE the launch line: 3 points
 * - CLASSIFIED artifacts in GOAL: 3 points each
 * - BASE parking: 10 points (full)
 *
 * Potential score: 3 + 18 + 10 = 31+ points
 */
@Autonomous(name = "DECODE Autonomous - Red", group = "DECODE")
public class DecodeAutonomousRed extends LinearOpMode {

    // Hardware
    private DcMotor beltMotor = null;
    private DcMotor shooterMotor = null;
    private CRServo intakeServo = null;

    // Power levels
    private static final double SHOOTER_POWER = 0.8;
    private static final double BELT_POWER = 1.0;
    private static final double INTAKE_POWER = 1.0;

    // Timing constants
    private static final double SHOOTER_SPINUP_TIME = 0.5;
    private static final double SHOOT_DURATION = 2.0;
    private static final double INTAKE_DURATION = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize hardware
        beltMotor = hardwareMap.get(DcMotor.class, "belt");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        intakeServo = hardwareMap.get(CRServo.class, "intake");

        // Configure motors
        beltMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        beltMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Starting pose: Red Alliance (mirrored from Blue)
        // Red starts on opposite side, so Y coordinates are negated
        Pose2d startPose = new Pose2d(new Vector2d(-60, -36), Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        // Define key positions (mirrored for Red Alliance)
        Vector2d goalPosition = new Vector2d(-24, -36);
        Vector2d collectZone1 = new Vector2d(-12, -48);
        Vector2d collectZone2 = new Vector2d(0, -48);
        Vector2d collectZone3 = new Vector2d(12, -48);
        Vector2d baseZone = new Vector2d(-60, -60);

        // Create actions for motor control
        Action shooterOn = (telemetryPacket) -> {
            shooterMotor.setPower(SHOOTER_POWER);
            return false;
        };

        Action shooterOff = (telemetryPacket) -> {
            shooterMotor.setPower(0.0);
            return false;
        };

        // Telemetry for init
        telemetry.addLine("=== DECODE Autonomous ===");
        telemetry.addLine("Red Alliance - Right Start");
        telemetry.addLine("");
        telemetry.addLine("Strategy:");
        telemetry.addLine("1. LEAVE launch line (3 pts)");
        telemetry.addLine("2. Shoot pre-loaded (9 pts)");
        telemetry.addLine("3. Collect artifacts");
        telemetry.addLine("4. Shoot collected (9 pts)");
        telemetry.addLine("5. Park in BASE (10 pts)");
        telemetry.addLine("");
        telemetry.addLine("Waiting for START...");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        // Start intake and belt
        intakeServo.setPower(INTAKE_POWER);
        beltMotor.setPower(BELT_POWER);

        // Build and execute the autonomous trajectory (mirrored for Red)
        Action autonomousPath = drive.actionBuilder(startPose)
                // ===== PHASE 1: LEAVE (3 points) =====
                .lineToX(-48)

                // ===== PHASE 2: First Shooting Cycle =====
                .splineToLinearHeading(new Pose2d(goalPosition, Math.toRadians(-45)), Math.toRadians(0))
                .stopAndAdd(shooterOn)
                .waitSeconds(SHOOTER_SPINUP_TIME)
                .waitSeconds(SHOOT_DURATION)
                .stopAndAdd(shooterOff)

                // ===== PHASE 3: Collect More Artifacts =====
                .splineToLinearHeading(new Pose2d(collectZone1, Math.toRadians(-90)), Math.toRadians(-45))
                .waitSeconds(INTAKE_DURATION)
                .lineToY(-52)
                .waitSeconds(0.2)

                .strafeToLinearHeading(collectZone2, Math.toRadians(-90))
                .waitSeconds(INTAKE_DURATION)
                .lineToY(-52)
                .waitSeconds(0.2)

                .strafeToLinearHeading(collectZone3, Math.toRadians(-90))
                .waitSeconds(INTAKE_DURATION)
                .lineToY(-52)
                .waitSeconds(0.2)

                // ===== PHASE 4: Second Shooting Cycle =====
                .splineToLinearHeading(new Pose2d(goalPosition, Math.toRadians(-45)), Math.toRadians(180))
                .stopAndAdd(shooterOn)
                .waitSeconds(SHOOTER_SPINUP_TIME)
                .waitSeconds(SHOOT_DURATION)
                .stopAndAdd(shooterOff)

                // ===== PHASE 5: Park in BASE (10 points) =====
                .splineToLinearHeading(new Pose2d(baseZone, Math.toRadians(180)), Math.toRadians(-135))

                .build();

        // Execute the autonomous
        Actions.runBlocking(autonomousPath);

        // Stop all motors
        shooterMotor.setPower(0);
        beltMotor.setPower(0);
        intakeServo.setPower(0);

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}

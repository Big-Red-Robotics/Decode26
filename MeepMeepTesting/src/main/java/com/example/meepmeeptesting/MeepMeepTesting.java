package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

/**
 * MeepMeep Trajectory Testing for FTC DECODE 2025-2026
 *
 * DECODE Game Overview:
 * - 30 second autonomous period
 * - LEAVE the launch line: 3 points
 * - CLASSIFIED artifacts in GOAL: 3 points each
 * - OVERFLOW artifacts: 1 point each
 * - PATTERN match (MOTIF): 2 points per match
 * - BASE parking: 5 points partial, 10 points full
 *
 * Field is 12'x12' (144"x144") - RoadRunner coords: -72 to +72 inches
 *
 * MOTIF patterns: GPP (Gold-Purple-Purple), PGP, PPG
 */
public class MeepMeepTesting {

    // DECODE MOTIF patterns
    enum Motif {
        GPP,  // Gold, Purple, Purple
        PGP,  // Purple, Gold, Purple
        PPG   // Purple, Purple, Gold
    }

    public static void main(String[] args) {
        // Create a MeepMeep window with 800px size
        MeepMeep meepMeep = new MeepMeep(800);

        // Current MOTIF (would be read from OBELISK in real match)
        Motif motif = Motif.GPP;

        // Build the robot with realistic constraints
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 14.5)
                // Set bot dimensions in inches (18x18 robot)
                .setDimensions(18, 18)
                .build();

        /*
         * DECODE Autonomous Strategy:
         *
         * Starting Position: Blue Alliance, left side of field
         * Robot faces the GOAL (heading towards positive X)
         *
         * Phase 1: LEAVE (3 points)
         *   - Move forward off the launch line
         *
         * Phase 2: Score Pre-loaded Artifacts
         *   - Drive to GOAL position
         *   - Shoot artifacts (intake running, belt feeding, shooter spinning)
         *
         * Phase 3: Collect & Score More Artifacts (time permitting)
         *   - Move to artifact collection zone
         *   - Collect artifacts with intake
         *   - Return to GOAL and shoot
         *
         * Phase 4: Park in BASE (10 points)
         *   - Return to BASE zone for endgame points
         */
        // Trajectory from NetZoneAutonomous.java
        Pose2d beginPose = new Pose2d(new Vector2d(-70, 24), Math.toRadians(0));

        // Starting pose: Blue Alliance, near launch line
        // Positioned to face the GOAL (heading 0 = facing +X direction)
        Pose2d startPose = new Pose2d(new Vector2d(-60, 36), Math.toRadians(0));

        // GOAL position (where we shoot artifacts)
        // The GOAL is positioned to allow scoring
        Vector2d goalPosition = new Vector2d(-24, 36);

        // Artifact collection zone (where artifacts are on the field)
        Vector2d collectZone1 = new Vector2d(-12, 48);
        Vector2d collectZone2 = new Vector2d(0, 48);
        Vector2d collectZone3 = new Vector2d(12, 48);

        // BASE zone for parking (endgame)
        Vector2d baseZone = new Vector2d(-60, 60);

        // Define the autonomous trajectory
        myBot.runAction(myBot.getDrive().actionBuilder(startPose)
                // ===== PHASE 1: LEAVE (3 points) =====
                // Drive forward to leave the launch line
                .lineToX(-48)

                // ===== PHASE 2: First Shooting Cycle =====
                // Turn to face the goal and drive to shooting position
                .splineToLinearHeading(new Pose2d(goalPosition, Math.toRadians(45)), Math.toRadians(0))
                .waitSeconds(0.5)  // Spin up shooter
                .waitSeconds(1.5)  // Shoot pre-loaded artifacts (3 artifacts)

                // ===== PHASE 3: Collect More Artifacts =====
                // Drive to artifact collection zone
                .splineToLinearHeading(new Pose2d(collectZone1, Math.toRadians(90)), Math.toRadians(45))
                .waitSeconds(0.3)  // Intake artifact
                .lineToY(52)       // Push forward to ensure pickup
                .waitSeconds(0.2)

                // Move to next artifact
                .strafeToLinearHeading(collectZone2, Math.toRadians(90))
                .waitSeconds(0.3)
                .lineToY(52)
                .waitSeconds(0.2)

                // Move to third artifact
                .strafeToLinearHeading(collectZone3, Math.toRadians(90))
                .waitSeconds(0.3)
                .lineToY(52)
                .waitSeconds(0.2)

                // ===== PHASE 4: Second Shooting Cycle =====
                // Return to goal and shoot collected artifacts
                .splineToLinearHeading(new Pose2d(goalPosition, Math.toRadians(45)), Math.toRadians(180))
                .waitSeconds(0.5)  // Spin up
                .waitSeconds(1.5)  // Shoot

                // ===== PHASE 5: Park in BASE (10 points) =====
                // Drive to base zone for endgame points
                .splineToLinearHeading(new Pose2d(baseZone, Math.toRadians(180)), Math.toRadians(135))
                .waitSeconds(0.5)  // Settle in base

                .build());

        // Display the field with DECODE background
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

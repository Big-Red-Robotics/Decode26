package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    enum Pattern {
        GPP,
        PGP,
        PPG
    }
    public static void main(String[] args) {


        // Create a MeepMeep window with 800px size

        Pattern pattern = Pattern.GPP;

        MeepMeep meepMeep = new MeepMeep(800);

        // Go to blue side and shoot
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                // Set bot dimensions in inches
                .setDimensions(18, 18)
                .build();

        // Trajectory from testautonshi.java
        Pose2d beginPose = new Pose2d(new Vector2d(-70, 24), Math.toRadians(0));

        // Define the movement trajectory
        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .lineToXLinearHeading(-30, Math.toRadians(135))
                .waitSeconds(1.5) //shooting here
                .splineToSplineHeading(new Pose2d(-11, 30, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.05)
                .lineToY(37)
                .waitSeconds(0.2)
                .lineToY(42)
                .waitSeconds(0.2)
                .lineToY(47)
                .splineToSplineHeading(new Pose2d(-30, 24, Math.toRadians(135)), Math.toRadians(180))
                .waitSeconds(1) //Shooting point: (-30, 24) shooting here

                .splineToSplineHeading(new Pose2d(12, 30, Math.toRadians(90)), Math.toRadians(0)) //next position
                .waitSeconds(0.2)
                .lineToY(37)
                .waitSeconds(0.3)
                .lineToY(42)
                .waitSeconds(0.3)
                .lineToY(47)
                .splineToSplineHeading(new Pose2d(-30, 24, Math.toRadians(135)), Math.toRadians(180))
                .waitSeconds(1) //shooting here
                .splineToSplineHeading(new Pose2d(37, 33, Math.toRadians(0)), Math.toRadians(90))
                .build());

        // Display the field with coordinates and background styling
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                // Add coordinate grid and numbers// spacing between grid lines// label font size
                .start();
    }
}

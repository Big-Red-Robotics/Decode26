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

        double angle = 0.0;
        Vector2d ArtifactLocation = new Vector2d(0,0);
        switch (pattern){
            case GPP:
                ArtifactLocation = new Vector2d(-13,-30);
                angle = 210;
                break;
            case PPG:
                break;
            case PGP:
                break;
        }



        // Define the movement trajectory
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-(70-9), -18, 0)).setTangent(0)
                .strafeToLinearHeading(new Vector2d(-38, -34), Math.toRadians(45))
                        .strafeToLinearHeading(ArtifactLocation,angle)
                .build());
        /*
        shoot code ouhewigrbyvuihorjl3n
         */

        // Display the field with coordinates and background styling
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                // Add coordinate grid and numbers// spacing between grid lines// label font size
                .start();
    }
}

package com.example.meepmeeptesting1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepTesting3 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        //GPP
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-55, 65, Math.toRadians(315)))
                        .splineTo(new Vector2d(-36, 36), Math.toRadians(315))
                        .turn(Math.toRadians(90)) //look at the tag
                        .waitSeconds(1)
                        .turn(Math.toRadians(90))//shoot 3p
                        .waitSeconds(3)
                        .splineTo(new Vector2d(-35, 15), Math.toRadians(180))//if the pattern is ppg
                        .splineTo(new Vector2d(-60, 15), Math.toRadians(0))//intake
                        .splineTo(new Vector2d(-36, 36), Math.toRadians(135))//shoot
                        .waitSeconds(3)
                        .splineTo(new Vector2d(-35, -35), Math.toRadians(180))
                        .splineTo(new Vector2d(-60, -35), Math.toRadians(0))
                        .splineTo(new Vector2d(-36, 36), Math.toRadians(135))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .setAxesInterval(5)
                .start();

    }
}

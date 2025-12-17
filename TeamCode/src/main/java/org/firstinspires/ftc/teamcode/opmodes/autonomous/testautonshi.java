package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.Vector2d;

// Fixed import
import org.firstinspires.ftc.teamcode.components.drive.MecanumDrive;

@Autonomous (name="Skibidi Rizzy")
public class testautonshi extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{

        Pose2d beginPose = new Pose2d(new Vector2d(-70, 24), Math.toRadians(0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

        Action path = drive.actionBuilder(beginPose)
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

                /*
                .splineToSplineHeading(new Pose2d(36, 30, Math.toRadians(90)), Math.toRadians(0)) //next position
                .waitSeconds(0.2)
                .lineToY(37)
                .waitSeconds(0.3)
                .lineToY(42)
                .waitSeconds(0.3)
                .lineToY(70)
                .splineToSplineHeading(new Pose2d(-30, 24, Math.toRadians(135)), Math.toRadians(180))
                .waitSeconds(1) //shooting here
                .splineToSplineHeading(new Pose2d(37, 33, Math.toRadians(0)), Math.toRadians(90))
                //.splineToLinearHeading(new Pose2d(0, 15, Math.toRadians(0)), Math.toRadians(0))
                //.splineToConstantHeading(new Vector2d(40,-38), Math.toRadians(0))*/
                .build();
        Actions.runBlocking(new SequentialAction(path));
    }
}

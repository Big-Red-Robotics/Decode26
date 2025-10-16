package org.firstinspires.ftc.teamcode.autonomous;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MyVision;

@Autonomous(name="First RoadRunner Auton")
public class FirstRoadRunnerAuton extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        MyVision vision = new MyVision();
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();


        com.acmerobotics.roadrunner.ftc.Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        .lineToX(64)
                        .waitSeconds(6)
                        .lineToX(0)
                        .waitSeconds(5)
                        .build());
    }// drives it forward 64 inches,
}


package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MyIntake;
import org.firstinspires.ftc.teamcode.MyShooterOne;
import org.firstinspires.ftc.teamcode.MyVision;

import java.util.EnumSet;

@Config
@Autonomous(name = "nov 1 auton")
public class FullAutonOne extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(-55, 65, Math.toRadians(315));

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, initialPose);
        MyIntake myIntake = new MyIntake(hardwareMap);
        MyVision myVision = new MyVision(hardwareMap);
        MyShooterOne myShooterOne = new MyShooterOne(hardwareMap);

        // Build trajectory actions (call .build() to convert to Action)
        Action traj1 = mecanumDrive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-36, 36), Math.toRadians(315))
                .turn(Math.toRadians(90))
                .build(); //look at the tag

        Action traj2 = mecanumDrive.actionBuilder(initialPose)
                .turn(Math.toRadians(90))
                .build();//shoot 3p

        Action traj3 = mecanumDrive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-35, 15), Math.toRadians(180))//if the pattern is ppg
                .splineTo(new Vector2d(-60, 15), Math.toRadians(0))//intake
                .splineTo(new Vector2d(-36, 36), Math.toRadians(135))
                .build();//shoot

        Action traj4 = mecanumDrive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-30, -55), Math.toRadians(180))//for the other ppg
                .splineTo(new Vector2d(-60, -55), Math.toRadians(180))
                .splineTo(new Vector2d(-60, -35), Math.toRadians(0))
                .splineTo(new Vector2d(-35, -35), Math.toRadians(0))
                .splineTo(new Vector2d(-36, 36), Math.toRadians(135))
                .build();

        telemetry.addLine("Ready to start!");
        telemetry.update();

        waitForStart();

        // Execute the autonomous routine
        Actions.runBlocking(
                new SequentialAction(
                        // Move to observation position
                        traj1,

                        // Update vision to detect tag
                        new InstantAction(() -> myVision.updateLimelight()),

                        traj2,

                        // Shoot preloaded samples
                        new InstantAction(() -> {
                            myShooterOne.setActiveOneStates(EnumSet.of(
                                    MyShooterOne.MyShooterOneState.belt,
                                    MyShooterOne.MyShooterOneState.shoot
                            ));
                        }),

                        new SleepAction(3.0),
                        traj3,

                        // Stop shooter
                        new InstantAction(() -> {
                            myShooterOne.setActiveOneStates(EnumSet.of(
                                    MyShooterOne.MyShooterOneState.belt,
                                    MyShooterOne.MyShooterOneState.shoot
                            ));
                        }),

                        // Execute appropriate trajectory based on pattern
                        traj4,

                        new InstantAction(() -> {
                            myShooterOne.setActiveOneStates(EnumSet.of(
                                    MyShooterOne.MyShooterOneState.belt,
                                    MyShooterOne.MyShooterOneState.shoot
            ));
        })
                )
        );

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}
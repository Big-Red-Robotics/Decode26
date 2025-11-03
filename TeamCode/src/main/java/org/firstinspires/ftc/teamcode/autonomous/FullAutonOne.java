package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Chassis;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MyIntake;
import org.firstinspires.ftc.teamcode.MyShooterOne;
import org.firstinspires.ftc.teamcode.MyVision;

@Config
@Autonomous(name = "nov 1 auton")
public class FullAutonOne extends LinearOpMode {
    public class MyShooter {
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(-55, 65, Math.toRadians(315));

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, initialPose);
        MyIntake myIntake = new MyIntake(hardwareMap);
        MyVision myVision = new MyVision();
        MyShooterOne myShooterOne = new MyShooterOne(hardwareMap);

        TrajectoryActionBuilder traj1 = mecanumDrive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-36, 36), Math.toRadians(315))
                .turn(Math.toRadians(90)); //look at the tag

        TrajectoryActionBuilder traj2 = mecanumDrive.actionBuilder(initialPose)
                .turn(Math.toRadians(90));//shoot 3p
        TrajectoryActionBuilder traj3 = mecanumDrive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-35, 15), Math.toRadians(180))//if the pattern is ppg
                .splineTo(new Vector2d(-60, 15), Math.toRadians(0))//intake
                .splineTo(new Vector2d(-36, 36), Math.toRadians(135));//shoot
        TrajectoryActionBuilder traj4 = mecanumDrive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-30, -55), Math.toRadians(180))//for the other ppg
                .splineTo(new Vector2d(-60, -55), Math.toRadians(180))
                .splineTo(new Vector2d(-60, -35), Math.toRadians(0))
                .splineTo(new Vector2d(-35, -35), Math.toRadians(0))
                .splineTo(new Vector2d(-36, 36), Math.toRadians(135));

    }


}

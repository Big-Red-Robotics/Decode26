package org.firstinspires.ftc.teamcode.autonomous;

import static org.firstinspires.ftc.teamcode.utility.RobotConfig.limelight;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MyIntake;
import org.firstinspires.ftc.teamcode.MyShooterOne;
import org.firstinspires.ftc.teamcode.MyVision;

@Autonomous(name = "2025-26 DECODE for the robot1") //this is for robot1
public class TestAutonOne extends LinearOpMode {
    enum State {
        START,
        TRAJ_GOAL,
        SHOOT,
        TRAJ_ART,
        INTAKE,
        PARK,
        WAIT
    }

    TestAuton.State currentState = TestAuton.State.WAIT;

    Pose2d startPose = new Pose2d(10, -10, Math.toRadians(90)); //coordinates of the start

    Vector2d parkBlue = new Vector2d(10, 10); //coordinates park if blue
    Vector2d parkRed = new Vector2d(-10, -10); //coordinates park if red
    boolean parked = false;


    @Override
    public void runOpMode() {
        MecanumDrive chassis = new MecanumDrive(hardwareMap);
        MyIntake intake = new MyIntake(hardwareMap);
        MyVision vision = new MyVision(hardwareMap);
        MyShooterOne shooterone = new MyShooterOne(hardwareMap);

    }
}

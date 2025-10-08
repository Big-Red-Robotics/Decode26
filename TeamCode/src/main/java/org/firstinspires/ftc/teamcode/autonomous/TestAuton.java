package org.firstinspires.ftc.teamcode.autonomous;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "2025-26 DECODE") //this is for robot2
public class TestAuton extends LinearOpMode {
    enum State {
        START,
        TRAJ_GOAL,
        SHOOT,
        TRAJ_ART,
        INTAKE,
        SPINDEXTER,
        PARK,
        WAIT
    }

    State currentState = State.WAIT;

    Pose2d startPose = new Pose2d(10, -10, Math.toRadians(90)); //coordinates of the start

    Vector2d parkBlue = new Vector2d(10, 10); //coordinates park if blue
    Vector2d parkRed = new Vector2d(-10, -10); //coordinates park if red


    @Override
    public void runOpMode() throws InterruptedException {

    }
}

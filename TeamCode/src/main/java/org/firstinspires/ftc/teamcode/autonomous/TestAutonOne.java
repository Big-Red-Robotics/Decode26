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
        TRAJ_PATTERN1,
        TRAJ_PATTERN2,
        TRAJ_PATTERN3,
        INTAKE,
        PARK,
        WAIT
    }

    TestAuton.State currentState = TestAuton.State.WAIT;

    Pose2d startPose = new Pose2d(10, -10, Math.toRadians(90)); //coordinates of the start

    Vector2d parkBlue = new Vector2d(10, 10); //coordinates park if blue
    Vector2d parkRed = new Vector2d(-10, -10); //coordinates park if red


    Vector2d goalRed = new Vector2d(-36, 36);
    Vector2d goalBlue = new Vector2d(36, 36);


    //this or lowkey just mirror it    ????????? mb
    //BLUE
    Vector2d bPattern1 = new Vector2d(-35, 15); //drive forward untill -60
    Vector2d bPattern2 = new Vector2d(-35, -11);
    Vector2d bPattern3 = new Vector2d(-35, -35);


    //RED
    Vector2d rPattern1 = new Vector2d(35, 15); //drive forward untill 60
    Vector2d rPattern2 = new Vector2d(35, -11);
    Vector2d rPattern3 = new Vector2d(35, -35);


    boolean parked = false;


    @Override
    public void runOpMode() {

    }
}

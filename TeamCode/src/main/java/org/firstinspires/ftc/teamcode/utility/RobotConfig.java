package org.firstinspires.ftc.teamcode.utility;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.utility.teaminfo.InitialSide;
import org.firstinspires.ftc.teamcode.utility.teaminfo.TeamColor;

@Config
public class RobotConfig {
    public static TeamColor teamColor = TeamColor.BLUE; //or RED
    public static InitialSide initialSide = InitialSide.LEFT; //or RIGHT


    public static String motorFL = "mFL", motorBL = "mBL", motorFR = "mFR", motorBR = "mBR";
    public static String intake = "mi";
    public static String shooterMotorL = "sL";

    public static String shooterMotorR = "sR";

    public static String flyingWheelMotor = "fM";
    public static String beltMotor = "Mb";

    public static String pushingUpServo = "pS";
    public static String backSpinServoL = "sL", backspinservoR = "sR";
    public static String spindexter = "spindexter";
    public static String turret = "turret";
    public static String imu = "imu";
    public static String colorSensor = "color sensor"; //in the spindexter
    public static String beamBreaker = "beam breaker sensor"; //in the intake to detect balls
    public static String limelight = "april tag vision";
    public static int limelightWidth = 640; //plug in real values
    public static int limelightHeight = 480;
    public static String camera = "webcam";
    public static int cameraWidth = 640;
    public static int cameraHeight = 480; //plug in real values

}



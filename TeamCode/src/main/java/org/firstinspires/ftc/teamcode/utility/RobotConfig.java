package org.firstinspires.ftc.teamcode.utility;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.utility.teaminfo.InitialSide;
import org.firstinspires.ftc.teamcode.utility.teaminfo.TeamColor;

@Config
public class RobotConfig {
    public static TeamColor teamColor = TeamColor.BLUE;
    public static InitialSide initialSide = InitialSide.LEFT;

    public static String motorFL = "dFL", motorFR = "dFR", motorBL = "dBL", motorBR = "dBR";
    public static String arm = "arm";

    public static String claw = "claw";
    public static String wrist = "ldservo", intake = "rdservo";

    /*
     * Decode stuff
     */
    public static String decode_intake = "intake", belt = "belt", launcher = "launcher";

    public static String armExtensionL = "armExL";

    public static String armExtensionR = "armExR";
    public static String launchDrone = "droneLauncher";
    public static String positionDrone = "dronePositioner";

    public static String LDServo = "ldservo";

    public static String RDServo = "rdservo";

    // unused
    public static String imu = "imu";
    public static String distanceSensor = "distanceSensor";

    // Webcam: Logitech HD Webcam C270
    public static String cameraName = "Webcam 1";
    public static int cameraWidth = 640;
    public static int cameraHeight = 480;

    public static String limelightName = "Limelight";
    /*
     * Configure this to match the pipeline index on the Limelight that is set up
     * for QR/Barcode decoding (set in the Limelight web UI).
     */
    public static int limelightQrPipeline = 0;
}

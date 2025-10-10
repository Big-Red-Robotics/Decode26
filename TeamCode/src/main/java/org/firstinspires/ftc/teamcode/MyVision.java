package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.utility.RobotConfig.imu;
import static org.firstinspires.ftc.teamcode.utility.RobotConfig.limelight;


import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;


import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class MyVision {
    private Limelight3A limelight;
    private IMU imu;


    public void limelight() {
        limelight = hardwareMap.get(Limelight3A.class, "LimeLight");
        limelight.pipelineSwitch(8); // AprilTag pipeline
        limelight.start();

        imu = hardwareMap.get(IMU.class, "imu"); // field orientation through imu
        RevHubOrientationOnRobot revHubOrientationOnRobot =
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD, //var?
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD); //var?
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    public void processAprilTags() {
        // Get the current fiducial (AprilTag) target from Limelight
        LimelightTarget_Fiducial target = limelight.getFiducialTarget();

        // Check that a valid target is detected
        if (target != null && target.isValid()) {
            int tagId = target.getFiducialId();

            // Match against known tag IDs
            switch (tagId) {
                case 7:
                    // triggerSomething();
                    break;
                case 3:
                    // doSomethingElse();
                    break;
            }
        }
    }
}

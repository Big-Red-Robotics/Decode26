package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.components.lib.vision.IndicatorProcessor;
import org.firstinspires.ftc.teamcode.components.lib.vision.TestProcessor;
import org.firstinspires.ftc.teamcode.utility.RobotConfig;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Vision {
    private VisionPortal visionPortal;
    private final int screenWidth = RobotConfig.cameraWidth;

    //List of Processors that will be used
    AprilTagProcessor aprilTag;
    TestProcessor testProcessor;
    public IndicatorProcessor indicatorProcessor = new IndicatorProcessor();

    VisionProcessor[] processors = {aprilTag, testProcessor, indicatorProcessor};

    public Vision(HardwareMap hardwareMap){
        //Setup vision portal
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, RobotConfig.cameraName));
        builder.setCameraResolution(new Size(RobotConfig.cameraWidth, RobotConfig.cameraHeight));
        builder.enableLiveView(true);
        builder.setAutoStopLiveView(false);

        //add processors to the builder
        buildAprilTagProcessor();

        //add built processors to VisionPortal
        builder.addProcessor(aprilTag);  // Added aprilTag processor!
        builder.addProcessor(indicatorProcessor);

        visionPortal = builder.build();
    }

    void buildAprilTagProcessor() {
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();
    }

    public void setProcessor(VisionProcessor processor) {
        visionPortal.setProcessorEnabled(processor, true);
    }

    /**
     * Get distance from camera to the first detected AprilTag
     * @return distance in CM, or -1 if no tag detected
     */
    public double getDistanceFromTag() {
        List<AprilTagDetection> detections = aprilTag.getDetections();

        // Check if any tags are detected
        if (detections.isEmpty()) {
            return -1.0;  // No tag detected
        }

        // Get the first detected tag
        AprilTagDetection detection = detections.get(0);

        // Return the range (direct distance from camera to tag)
        if (detection.ftcPose != null) {
            return detection.ftcPose.range;
        }

        return -1.0;  // Pose not available
    }

    /**
     * Get distance from camera to a specific AprilTag ID
     * @param targetId The ID of the tag to find
     * @return distance in CM, or -1 if tag not found
     */
    public double getDistanceFromTag(int targetId) {
        List<AprilTagDetection> detections = aprilTag.getDetections();

        for (AprilTagDetection detection : detections) {
            if (detection.id == targetId && detection.ftcPose != null) {
                return detection.ftcPose.range;
            }
        }

        return -1.0;  // Tag not found
    }

    /**
     * Get the closest AprilTag distance
     * @return distance to closest tag in CM, or -1 if none detected
     */
    public double getClosestTagDistance() {
        List<AprilTagDetection> detections = aprilTag.getDetections();

        if (detections.isEmpty()) {
            return -1.0;
        }

        double minDistance = Double.MAX_VALUE;

        for (AprilTagDetection detection : detections) {
            if (detection.ftcPose != null) {
                double distance = detection.ftcPose.range;
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return (minDistance == Double.MAX_VALUE) ? -1.0 : minDistance;
    }

    /**
     * Get all AprilTag detections with distance info
     * @return List of detections
     */
    public List<AprilTagDetection> getAprilTags() {
        return aprilTag.getDetections();
    }

    /**
     * Check if any AprilTags are detected
     * @return true if at least one tag is detected
     */
    public boolean isTagDetected() {
        return !aprilTag.getDetections().isEmpty();
    }

    /**
     * Get detailed pose information for first detected tag
     */
    public AprilTagPoseFtc getTagPose() {
        List<AprilTagDetection> detections = aprilTag.getDetections();

        if (!detections.isEmpty() && detections.get(0).ftcPose != null) {
            return detections.get(0).ftcPose;
        }

        return null;
    }

    /**
     * Close the vision portal when done
     */
    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
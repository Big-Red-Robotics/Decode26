package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.util.Pose3D; //work through gradle as i have it in my personal aprill tag thingy

import java.util.List;

public class MyVision {
    private Limelight3A limelight;

    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // 100 times per second
        limelight.start();
        limelight.pipelineSwitch(8); // ???
    }

    public void update() {
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            double tx = result.getTx(); // left/right degrees
            double ty = result.getTy(); // up/down degrees
            double ta = result.getTa(); // area (% of image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);

            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
// this whole thing is just recognizing the ID tag
            // except for the target x degrees. put it switch and then cases for each pattern and then write what to do for what in auton
            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                int id = fiducial.getFiducialId();
                double x = fiducial.getTargetXDegrees();
                double y = fiducial.getTargetYDegrees();
                double strafeDistance3D = fiducial.getRobotPoseTargetSpace().getY();

            }
        } else {
            telemetry.addData("Limelight", "No Targets");
        }

    }
}

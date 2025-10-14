package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
//april tag recognition DONEEEEEEE

import java.util.EnumSet;
import java.util.List;

public class MyVision {
    private Limelight3A limelight;
    private int rememberTagID;


    public enum MyVisionState {none, pattern, goal}

    ;

    public EnumSet<MyVisionState> activeStates = EnumSet.of(MyVisionState.none, MyVisionState.pattern, MyVisionState.goal);

    public void limelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // 100 times per second
        limelight.start();
        limelight.pipelineSwitch(8); // ???
    }

    public void setActiveStatesMyVisionStates(EnumSet<MyVisionState> states) {
        this.activeStates = EnumSet.copyOf(states);
    }


    public void setLimelight() {
        activeStates.add(MyVisionState.none);

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                int id = fiducial.getFiducialId();
                telemetry.addData("the ID tag is:", fiducial.getFiducialId());

                rememberTagID = id;

                switch (id) {
                    case 21:
                        telemetry.addLine("id 21 GPP");
                        activeStates.add(MyVisionState.pattern);
                        break;

                    case 22:
                        telemetry.addLine("id 22 PGP");
                        activeStates.add(MyVisionState.pattern);
                        break;

                    case 23:
                        telemetry.addLine("id 23 PPG");
                        activeStates.add(MyVisionState.pattern);
                        break;

                    case 20:
                        telemetry.addLine("BLUE");
                        activeStates.add(MyVisionState.goal);
                        break;

                    case 24:
                        telemetry.addLine("RED");
                        activeStates.add(MyVisionState.goal);
                        break;
                }
            }
        } else {
            telemetry.addData("Limelight", "No Targets");
        }


    }
}

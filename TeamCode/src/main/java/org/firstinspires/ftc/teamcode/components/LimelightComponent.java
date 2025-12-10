package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.List;

public class LimelightComponent extends _BaseComponent {
    private Limelight3A limelight;
    private String lastBarcode = "none";
    private final int qrPipelineIndex;

    public LimelightComponent(HardwareMap hardwareMap) {
        super();
        qrPipelineIndex = RobotConfig.limelightQrPipeline;
        try {
            limelight = hardwareMap.get(Limelight3A.class, RobotConfig.limelightName);
        } catch (IllegalArgumentException e) {
            limelight = null;
        }
        if (limelight != null) {
            limelight.pipelineSwitch(qrPipelineIndex);
            // Starts polling for data. Without this, getLatestResult() returns null.
            limelight.start();
        }
    }

    /**
     * Poll once for a QR/barcode reading and cache the last non-null result.
     * @return latest barcode data if available; otherwise null
     */
    public String pollQRCode() {
        if (limelight == null) {
            return null;
        }
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            List<LLResultTypes.BarcodeResult> barcodes = result.getBarcodeResults();
            if (barcodes != null && !barcodes.isEmpty()) {
                String data = barcodes.get(0).getData();
                if (data != null) {
                    lastBarcode = data;
                }
                return data;
            }
        }
        return null;
    }

    public String getLastBarcode() {
        return lastBarcode;
    }

    /**
     * Emit useful Limelight status/diagnostics to telemetry to help debug pipeline issues.
     */
    public void addTelemetry(Telemetry telemetry) {
        if (limelight == null) {
            telemetry.addData("Limelight", "Not found: check config name (%s)", RobotConfig.limelightName);
            return;
        }

        LLStatus status = limelight.getStatus();
        if (status == null) {
            telemetry.addData("Limelight", "Status unavailable");
            return;
        }
        telemetry.addData("LL Pipeline", "Idx %d type %s", status.getPipelineIndex(), status.getPipelineType());
        telemetry.addData("LL Sys", "Temp %.1fC CPU %.1f%% FPS %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        telemetry.addData("LL Last QR", lastBarcode);
    }

    public void stop() {
        if (limelight != null) {
            limelight.stop();
        }
    }

    public void switchPipeline(int index) {
        if (limelight != null) {
            limelight.pipelineSwitch(index);
        }
    }
}

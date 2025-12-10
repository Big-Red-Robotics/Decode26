package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

/**
 * Control
 * -------
 * Simple, side‑effect‑free helpers to operate the intake, belt, and launcher motors.
 *
 * Goals:
 *  - Fix inconsistent direction/power usage.
 *  - Remove unused state and side effects (old update/belt flag).
 *  - Provide clear, intention‑revealing methods with inline documentation.
 *  - Keep backward‑compatible aliases for existing callers where possible.
 */
public class Control {
    // === Tunable constants ===
    // Flip the sign (+/-) here if any motor runs the wrong direction due to wiring.
    private static final double INTAKE_POWER   = -1.0;   // nominal intake speed
    private static final double BELT_POWER     = 1.0;   // nominal belt speed
    private static final double LAUNCHER_POWER = 0.5;   // nominal launcher speed

    // === Hardware ===
    private final CRServo intake;
    private final DcMotor belt;
    private final DcMotor launcher;

    private final DcMotor armExtensionL;

    private final DcMotor armExtensionR;

    /**
     * Map hardware names from RobotConfig to DcMotor instances.
     */
    public Control(HardwareMap hardwareMap) {
        this.intake   = hardwareMap.get(CRServo.class, RobotConfig.decode_intake);
        this.belt     = hardwareMap.get(DcMotor.class, RobotConfig.belt);
        this.launcher = hardwareMap.get(DcMotor.class, RobotConfig.launcher);
        this.armExtensionL = hardwareMap.get(DcMotor.class, RobotConfig.armExtensionL);
        this.armExtensionR = hardwareMap.get(DcMotor.class, RobotConfig.armExtensionR);

        // Arm extension motors face opposite directions on the rail, so reverse one.
        armExtensionL.setDirection(DcMotorSimple.Direction.REVERSE);
        armExtensionR.setDirection(DcMotorSimple.Direction.REVERSE);
        armExtensionL.setTargetPosition(0);
        armExtensionR.setTargetPosition(0);


        // Optional safety: ensure motors stop when power=0. Uncomment if desired.
        // this.intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // this.belt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // this.launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // =========================
    // Intake controls
    // =========================

    /** Run intake forward (into the robot). */
    public void runIntakeForward() { intake.setPower(INTAKE_POWER); }

    /** Run intake in reverse (out of the robot). */
    public void runIntakeReverse() { intake.setPower(-INTAKE_POWER); }

    /** Stop the intake. */
    public void stopIntake() { intake.setPower(0); }

    /** Set intake on/off with direction control. */
    public void setIntake(boolean run, boolean reverse) {
        if (!run) { stopIntake(); return; }

        double p = reverse ? -INTAKE_POWER : INTAKE_POWER;

        // Optional small deadband protector in case drift happens elsewhere
        if (Math.abs(p) < 0.02) p = 0.0;

        intake.setPower(p);
    }

    /** Convenience overload: forward by default. */
    public void setIntake(boolean run) { setIntake(run, false); }

    // --- Backward‑compat aliases (kept to avoid breaking existing callers) ---
    /**
     * LEGACY: Previously named `intake()` and always set power to +1.
     * Prefer {@link #runIntakeForward()}.
     */
    public void intake() { runIntakeForward(); }

    /**
     * LEGACY & MISNAMED: This used to control the intake despite the name.
     * Preserved for compatibility; prefers reverse (old behavior).
     * Prefer {@link #setIntake(boolean, boolean)} or {@link #runIntakeReverse()}.
     */
    @Deprecated
    public void setinLauncher(boolean run) { setIntake(run, true); }

    // =========================
    // Belt controls
    // =========================

    /** Run belt forward (toward launcher). */
    public void runBeltForward() { belt.setPower(BELT_POWER); }

    /** Run belt reverse (away from launcher). */
    public void runBeltReverse() { belt.setPower(-BELT_POWER); }

    /** Stop the belt. */
    public void stopBelt() { belt.setPower(0); }

    /** Set belt on/off with direction control. */
    public void setBelt(boolean run, boolean reverse) {
        if (!run) { stopBelt(); return; }
        belt.setPower(reverse ? -BELT_POWER : BELT_POWER);
    }

    /**
     * LEGACY: Preserve old signature that used negative power when true.
     * Prefer {@link #setBelt(boolean, boolean)} or the explicit run/stop helpers.
     */
    public void setBelt(boolean run) { setBelt(run, true); }

    /** LEGACY: Previously named `belt()` and always set power to +1. */
    public void belt() { runBeltForward(); }

    // =========================
    // Launcher controls
    // =========================

    /** Spin up the launcher. */
    public void runLauncher() { launcher.setPower(LAUNCHER_POWER); }

    /** Stop the launcher. */
    public void stopLauncher() { launcher.setPower(0); }

    public void jsutrun(){
        armExtensionR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armExtensionL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armExtensionR.setPower(1);
        armExtensionL.setPower(1);
    }

    public void jusstop(){
        armExtensionR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armExtensionL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armExtensionR.setPower(0);
        armExtensionL.setPower(0);
    }

    // =========================
    // Utilities
    // =========================


    //armEx
    public void setArmExtensionPosition(int position){
        armExtensionL.setTargetPosition(position);
        //armExtensionL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtensionR.setTargetPosition(position);
        //armExtensionR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }



    public void update() {
        //armExtension
        if (armExtensionL.getTargetPosition() == 0 && armExtensionR.getTargetPosition() == 0) {
            armExtensionR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armExtensionL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (armExtensionL.isBusy() && armExtensionR.isBusy()){
                armExtensionL.setPower(1);
                armExtensionR.setPower(1);
            }
            else{
                armExtensionL.setPower(0.0);
                armExtensionR.setPower(0.0);
            }
        } else if (armExtensionL.isBusy() && armExtensionR.isBusy()) {
            armExtensionL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armExtensionR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (armExtensionR.getTargetPosition() == 1000 && armExtensionL.getTargetPosition() == 1000){
                armExtensionR.setPower(1);
                armExtensionL.setPower(1);
            }
            else {
                // I dont understand why d
                armExtensionL.setPower(1);
                armExtensionR.setPower(1);
            }
        } else{
            armExtensionL.setPower(0.0);
            armExtensionR.setPower(0.0);
        }

    }
    public void resetArmExtension(){
        armExtensionR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtensionL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        armExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armExtensionR.setTargetPosition(0);
        armExtensionL.setTargetPosition(0);
        armExtensionL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtensionR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtensionL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtensionR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtensionL.setPower(0);
        armExtensionR.setPower(0);
    }


        /** Stop all mechanisms. Useful for init/stop opmode. */
    public void stopAll() {
        stopIntake();
        stopBelt();
        stopLauncher();
    }

    /**
     * LEGACY NO‑OP: Old code referenced an `update()` that tried to read a belt flag that never changed.
     * Kept as an empty method to avoid compile errors; safe to delete once callers are updated.
     */
}

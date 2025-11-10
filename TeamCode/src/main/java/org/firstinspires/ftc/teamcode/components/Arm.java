package org.firstinspires.ftc.teamcode.components;

import android.util.Log;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

public class Arm {
    public final DcMotorEx arm;
    private final CRServo wrist;
    private final CRServo intake;
    public final ServoImplEx claw;
    private final CRServo leftDServo;
    private final CRServo rightDServo;
    private final DcMotor armExtensionR;
    private final DcMotor armExtensionL;
//    public final TouchSensor slideZeroReset;

    public static final int VERY_LOW = 1600;
    public static final int LOW = 3000;
    public static final int HIGH = 4051;
    public static final int GROUND = 0;
    public static final double dspeed = 0.5;

    public boolean hang = false;

    public Arm(HardwareMap hardwareMap){
//        this.slideZeroReset = hardwareMap.get(TouchSensor.class,"touch");
        this.arm = hardwareMap.get(DcMotorEx.class, RobotConfig.arm);
        this.claw = hardwareMap.get(ServoImplEx.class, RobotConfig.claw);
        this.wrist = hardwareMap.get(CRServo.class, RobotConfig.wrist);
        this.armExtensionR = hardwareMap.get(DcMotor.class, RobotConfig.armExtensionR);
        this.armExtensionL = hardwareMap.get(DcMotor.class, RobotConfig.armExtensionL);
        this.intake = hardwareMap.get(CRServo.class, RobotConfig.intake);
        this.rightDServo = hardwareMap.get(CRServo.class, RobotConfig.RDServo);
        this.leftDServo = hardwareMap.get(CRServo.class, RobotConfig.LDServo);
        this.claw.setPwmRange(new PwmControl.PwmRange(600,2400));

        // Arm extension gearboxes mirror each other, so reverse one.
        armExtensionL.setDirection(DcMotorSimple.Direction.FORWARD);
        armExtensionR.setDirection(DcMotorSimple.Direction.REVERSE);
//        arm.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    public void dUp(){
        rightDServo.setPower(dspeed);
        leftDServo.setPower(-dspeed);
    }

    public void dDown(){
        rightDServo.setPower(-dspeed);
        leftDServo.setPower(dspeed);
    }

    public void dLeft(){
        rightDServo.setPower(dspeed);
        leftDServo.setPower(dspeed);
    }

    public void dRight(){
        rightDServo.setPower(-dspeed);
        leftDServo.setPower(-dspeed);
    }
    // wrist
    public void swingWristRight(){
        wrist.setPower(0.5);
    }

    public void stopDs(){
        rightDServo.setPower(0);
        leftDServo.setPower(0);
    }

    public void swingWristLeft(){
        wrist.setPower(-0.5);
    }

    public double getWristPower(){
        return wrist.getPower();
    }

    public void stopWrist(){
        wrist.setPower(0);
    }

    //  wrist as Servo (not CRServo)
//    public void setWristPosition(int pos){
//        wrist.setPosition(pos);
//    }

    //intake
    public void intake(){
        intake.setPower(1.0);
    }

    public void outtake(){
        intake.setPower(-1.0);
    }

    public void stopIntake(){
        intake.setPower(0);
    }

    //arm
    public void resetArm(){
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setPower(0);
    }

    public void resetArmExtension(){
        armExtensionL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtensionR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        armExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armExtensionL.setTargetPosition(0);
        armExtensionR.setTargetPosition(0);
        armExtensionL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtensionR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtensionL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtensionR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtensionL.setPower(0);
        armExtensionR.setPower(0);
    }

    public void setArmPosition(int armPosition){
        arm.setTargetPosition(armPosition);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //armEx
    public void setArmExtensionPosition(int position){
        setArmExtensionTarget(position);
        setArmExtensionMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void  update() {
        //armExtension
        if (armExtensionL.getTargetPosition() == 0 && armExtensionR.getTargetPosition() == 0){
            setArmExtensionMode(DcMotor.RunMode.RUN_TO_POSITION);
            if(areArmExtensionsBusy()) setArmExtensionPower(1.0);
            else setArmExtensionPower(0.0);
        } else if(areArmExtensionsBusy()) {
            setArmExtensionMode(DcMotor.RunMode.RUN_TO_POSITION);
            setArmExtensionPower(1.0);
        } else setArmExtensionPower(0.0);

        //arm (lift)
        if (arm.isBusy()) arm.setPower(1.0);
        else arm.setPower(0.0);
    }

    public Action armExToPosition(int pos) {
        return new ArmExToPosition(pos);
    }

    public Action armToPosition(int pos) {
        return new ArmToPosition(pos);
    }

    public Action outtakeAction() {
        return new PowerIntake(-1.0);
    }
    public Action intakeAction() {
        return new PowerIntake(1.0);
    }
    public Action stopIntakeAction() {
        return new PowerIntake(0.0);
    }

    public int getArmPosition() {return arm.getCurrentPosition();}
    public int getArmTargetPosition() {return arm.getTargetPosition();}
    public int getArmPower() {return (int) arm.getPower();}

    //    public double getWristPosition() {return wrist.getPosition();} //wrist as Servo (not CRServo)
    public double getArmExPower() {return (armExtensionL.getPower() + armExtensionR.getPower()) / 2.0;}
    public int getArmExPosition() {return (armExtensionL.getCurrentPosition() + armExtensionR.getCurrentPosition()) / 2;}
    public int getArmExTargetPosition() {return (armExtensionL.getTargetPosition() + armExtensionR.getTargetPosition()) / 2;}

    private void setArmExtensionPower(double power) {
        armExtensionL.setPower(power);
        armExtensionR.setPower(power);
    }

    private void setArmExtensionMode(DcMotor.RunMode mode) {
        armExtensionL.setMode(mode);
        armExtensionR.setMode(mode);
    }

    private void setArmExtensionTarget(int position) {
        armExtensionL.setTargetPosition(position);
        armExtensionR.setTargetPosition(position);
    }

    private boolean areArmExtensionsBusy() {
        return armExtensionL.isBusy() || armExtensionR.isBusy();
    }

    public class PowerIntake implements Action {
        private final double power;

        public PowerIntake(double power){
            this.power = power;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            //  intake.setPower(power);
            return false;
        }
    }

    public class ArmExToPosition implements Action {
        private boolean initialized = false;
        private final int targetPosition;

        public ArmExToPosition(int pos){
            this.targetPosition = pos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                setArmExtensionPosition(targetPosition);
                Log.d("SET ARMEX POS", String.valueOf(getArmExTargetPosition()));
                setArmExtensionPower(1.0);
                initialized = true;
            }

            // checks lift's current position
            packet.put("current ArmEx position", getArmExPosition());
            packet.put("target ArmEx position", getArmExTargetPosition());
            if (areArmExtensionsBusy()) {
                // true causes the action to rerun
                Log.d("target ArmEx position", String.valueOf(getArmExTargetPosition()));
                Log.d("current ArmEx position", String.valueOf(getArmExPosition()));
                return true;
            } else {
                // false stops action rerun
                setArmExtensionPower(0.0);
                return false;
            }
        }
    }

    public class ArmToPosition implements Action {
        private boolean initialized = false;
        private final int targetPosition;

        public ArmToPosition(int pos){
            this.targetPosition = pos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                setArmPosition(targetPosition);
                Log.d("SET LIFT POS", String.valueOf(getArmTargetPosition()));
                arm.setPower(1.0);
                initialized = true;
            }

            // checks lift's current position
            packet.put("current Lift position", getArmPosition());
            packet.put("target Lift position", getArmTargetPosition());
            if (arm.isBusy()) {
                // true causes the action to rerun
                Log.d("target Lift position", String.valueOf(getArmTargetPosition()));
                Log.d("current Lift position", String.valueOf(getArmPosition()));
                return true;
            } else {
                // false stops action rerun
                arm.setPower(0);
                return false;
            }
        }
    }
}

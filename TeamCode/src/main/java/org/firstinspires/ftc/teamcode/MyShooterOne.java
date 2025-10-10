package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.Arrays;
import java.util.List;

public class MyShooterOne {
    private DcMotor beltMotor, flyingWheelMotor;
    private Servo pushingUpServo;
    List<DcMotor> shooterOneMotors;//and servo

    public enum MyShooterOneState {none, belt, push, shoot}

    ;
    public MyShooterOneState currentState = MyShooterOneState.none;

    public MyShooterOne(HardwareMap hardwareMap) {
        this.beltMotor = hardwareMap.get(DcMotor.class, RobotConfig.beltMotor);
        this.flyingWheelMotor = hardwareMap.get(DcMotor.class, RobotConfig.flyingWheelMotor);
        this.pushingUpServo = hardwareMap.get(Servo.class, RobotConfig.pushingUpServo);

        shooterOneMotors = Arrays.asList(beltMotor, flyingWheelMotor);
        for (DcMotor shooter : shooterOneMotors) {
            shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        beltMotor.setDirection(DcMotorSimple.Direction.REVERSE);//???
        flyingWheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);//???
    }

    public void setState(MyShooterOneState state) {
        currentState = state;
    }

    public void setShooterOneShoot(double power) {
        setState((MyShooterOneState.none));

        flyingWheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);//???
        beltMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        flyingWheelMotor.setPower(power);
        beltMotor.setPower(power);

        flyingWheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        beltMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void servoRest() {
        pushingUpServo.setPosition(0.0); //???
    }

    public void pushingUp() {
        pushingUpServo.setPosition(1.0); //???
    }

    public void update() {
        for (DcMotor shooter : shooterOneMotors) {//and servo!

        }
    }
}

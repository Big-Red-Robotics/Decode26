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
    List<DcMotor> shooterOneMotors;

    public enum MyShooterOneState {none, movingBelt, pushBallUp, shoot}

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
        flyingWheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);//???
    }
}

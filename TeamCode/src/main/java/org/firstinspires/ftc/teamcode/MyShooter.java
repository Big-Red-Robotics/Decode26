package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.MyShooter.DetectedColor.none;
import static org.firstinspires.ftc.teamcode.MyShooter.DetectedColor.purple;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class MyShooter {
    private DcMotor leftShooter, rightShooter;
    List<DcMotor> shooter;
    private Servo leftServoAngle, rightServoAngle;
    private Servo backServoSpin, servoRightSpin, servoLeftSpin;
    private Servo pushingBeltServo;
    private NormalizedColorSensor colorSensor;

    public enum MyShooterState {pushUp, spinSpindexter, nothing, shoot}

    ;

    public enum DetectedColor {purple, green, none}

    ;

    public EnumSet<MyShooterState> activeStates = EnumSet.of(MyShooterState.pushUp, MyShooterState.spinSpindexter,
            MyShooterState.nothing, MyShooterState.shoot);

    public MyShooter(HardwareMap hardwareMap) {
        this.leftShooter = hardwareMap.get(DcMotor.class, RobotConfig.shooterMotorL);//shooter motors
        this.rightShooter = hardwareMap.get(DcMotor.class, RobotConfig.shooterMotorR);

        this.leftServoAngle = hardwareMap.get(Servo.class, RobotConfig.angleHoodServoL); //hood angle
        this.rightServoAngle = hardwareMap.get(Servo.class, RobotConfig.angleHoodServoR);

        this.backServoSpin = hardwareMap.get(Servo.class, RobotConfig.spindexterServoBack);
        this.servoRightSpin = hardwareMap.get(Servo.class, RobotConfig.spindexterServoRight);
        this.servoLeftSpin = hardwareMap.get(Servo.class, RobotConfig.spindexterServoLeft);

        this.pushingBeltServo = hardwareMap.get(Servo.class, RobotConfig.pushingBeltServo);

        this.colorSensor = hardwareMap.get(NormalizedColorSensor.class, RobotConfig.colorSensor);


        shooter = Arrays.asList(leftShooter, rightShooter);
        for (DcMotor shooters : shooter) {
            shooters.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shooters.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooters.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        leftShooter.setDirection(DcMotorSimple.Direction.FORWARD);//check what direction
        rightShooter.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setActiveStates(EnumSet<MyShooterState> states) {
        this.activeStates = EnumSet.copyOf(states);
    }


    public void rotateSpindexter(MyShooterState state) {
        activeStates.add(MyShooterState.spinSpindexter);

        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        float red = colors.red;
        float green = colors.green;
        float blue = colors.blue;

        DetectedColor detectColor;
        if (green > red * 1.2 && green > blue * 1.2) {
            detectColor = DetectedColor.green;
        } else if (red > green * 0.8 && blue > green * 0.8 && (red + blue) / 2 > green * 1.2) {
            detectColor = DetectedColor.purple;

        } else {
            detectColor = DetectedColor.none;
        }
        switch (detectColor) {
            case green:
                servoLeftSpin.setPosition(0.2); //something

            case purple: // do something here

            case none:
        }
    }


}





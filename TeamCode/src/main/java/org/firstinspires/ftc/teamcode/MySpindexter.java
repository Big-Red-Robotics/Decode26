package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.List;

public class MySpindexter {
    private Servo servoLeft, servoRight;
    List<Servo> turretServos;

    public enum SpindexterState {intake, rotate, pause}

    ;
    public SpindexterState currentState = SpindexterState.pause;
    public boolean rotate = false;

    public MySpindexter(HardwareMap hardwareMap) {
        servoLeft = hardwareMap.get(Servo.class, RobotConfig.spindexterServoLeft);
        servoRight = hardwareMap.get(Servo.class, RobotConfig.spindexterServoRight);


    }
}

package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

@Config
public class Chassis { //extends mecanumdrive

    private IMU imu;


    private DcMotorEx motorFL, motorBL, motorFR, motorBR;

    // Control gains
    private final double kPForward = 0.05;   // forward/backward proportional gain
    private final double kPSide = 0.02;      // strafe proportional gain
    private final double stopDistanceInches = 6.0; // stop this far away from tag //var?

    public Chassis(HardwareMap hardwareMap) {
        //super
        this.motorFL = hardwareMap.get(DcMotorEx.class, RobotConfig.motorFL);
        this.motorBL = hardwareMap.get(DcMotorEx.class, RobotConfig.motorBL);
        this.motorFR = hardwareMap.get(DcMotorEx.class, RobotConfig.motorFR);
        this.motorBR = hardwareMap.get(DcMotorEx.class, RobotConfig.motorBR);
        this.imu = hardwareMap.get(IMU.class, RobotConfig.imu);
    }
}


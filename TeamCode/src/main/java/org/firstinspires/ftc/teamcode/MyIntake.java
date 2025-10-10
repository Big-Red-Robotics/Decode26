package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.RobotConfigNameable;

import org.firstinspires.ftc.robotserver.internal.webserver.RobotControllerWebHandlers;
import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.Arrays;
import java.util.List;

public class MyIntake {
    private DcMotor intakeMotor;
    private DigitalChannel beamBreakerSensor;

    public enum IntakeState {intake, pause}

    ;
    public IntakeState currentState = IntakeState.pause;
    public boolean intake = false;
    List<DcMotor> intakeMechanism;

    public MyIntake(HardwareMap hardwareMap) {
        this.intakeMotor = hardwareMap.get(DcMotor.class, RobotConfig.intake);
        this.beamBreakerSensor = hardwareMap.get(DigitalChannel.class, RobotConfig.beamBreaker);
        beamBreakerSensor.setMode(DigitalChannel.Mode.INPUT);

        intakeMechanism = Arrays.asList(intakeMotor);
        for (DcMotor intake : intakeMechanism) {
            intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        intakeMotor.setDirection(DcMotor.Direction.REVERSE); //idk if its actually inverse or FORWARD
    }

    public void setState(IntakeState state) {
        currentState = state;
    }

    public void setIntakePowers(double power) {
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE); //???
        intakeMotor.setPower(power);//???
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //add another public void for the beam breaker
    public void update() {
        for (DcMotor intake : intakeMechanism) {
            if (beamBreakerSensor.getState() == true) {
                setState(IntakeState.intake);
            } else if (beamBreakerSensor.getState() == false) {
                setState(IntakeState.pause);
            }
        }
    }
}

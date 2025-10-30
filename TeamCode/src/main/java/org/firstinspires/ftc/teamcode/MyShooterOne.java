package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class MyShooterOne implements Action {

    // Motors and servo
    private DcMotor beltMotor, flyingWheelMotor;
    //private Servo pushingUpServo;

    //Intake motors and sensors
    private DcMotor intakeMotor;
    private DigitalChannel beamBreakerSensor;

    // Groupings for convenience
    private List<DcMotor> shooterOneMotors;
    private List<DcMotor> intakeMechanism;

    // States for intake
    // public enum IntakeState {intake, pause}
    //public MyIntake.IntakeState currentIntakeState = MyIntake.IntakeState.pause;

    // Shooter states
    public enum MyShooterOneState {intake, none, belt, shoot, inversebelt, inverseintake}

    public EnumSet<MyShooterOneState> activeOneStates = EnumSet.of(MyShooterOneState.none);


    public MyShooterOne(HardwareMap hardwareMap) {
        this.beltMotor = hardwareMap.get(DcMotor.class, RobotConfig.beltMotor);
        this.flyingWheelMotor = hardwareMap.get(DcMotor.class, RobotConfig.flyingWheelMotor);


        //intake hardware
        this.intakeMotor = hardwareMap.get(DcMotor.class, RobotConfig.intake);
        //this.beamBreakerSensor = hardwareMap.get(DigitalChannel.class, RobotConfig.beamBreaker);
        beamBreakerSensor.setMode(DigitalChannel.Mode.INPUT);

        shooterOneMotors = Arrays.asList(beltMotor, flyingWheelMotor);
        for (DcMotor shooter : shooterOneMotors) {
            shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        intakeMechanism = Arrays.asList(intakeMotor);
        for (DcMotor intake : intakeMechanism) {
            intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        //adjust
        beltMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        flyingWheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    public void setActiveOneStates(EnumSet<MyShooterOneState> states) {
        this.activeOneStates = EnumSet.copyOf(states);
    }

    public Action addState(MyShooterOneState state) {
        this.activeOneStates.add(state);
        return null;
    }

    public void removeState(MyShooterOneState state) {
        this.activeOneStates.remove(state);
    }

    public void setIntakePower(double power) {
        intakeMotor.setPower(power);
    }

    public void setShooterPower(double power) {
        flyingWheelMotor.setPower(power);
        beltMotor.setPower(power);
    }


    @Override
    public boolean run(@NonNull TelemetryPacket packet) {
        // Handle intake state
        if (activeOneStates.contains(MyShooterOneState.intake)) {
            intakeMotor.setPower(1.0); // full power intake - adjust as needed
        } else {
            intakeMotor.setPower(0.0);
        }

        if (activeOneStates.contains(MyShooterOneState.belt)) {
            beltMotor.setPower(1.0);
        } else {
            beltMotor.setPower(0.0);
        }

        if (activeOneStates.contains(MyShooterOneState.shoot)) {
            flyingWheelMotor.setPower(1.0);
        } else {
            flyingWheelMotor.setPower(0.0);
        }
        if (activeOneStates.contains(MyShooterOneState.inversebelt)) {
            beltMotor.setPower(-1.0);
        } else {
            beltMotor.setPower(0.0);
        }
        if (activeOneStates.contains(MyShooterOneState.inverseintake)) {
            intakeMotor.setPower(-1.0);
        } else {
            intakeMotor.setPower(0.0);
        }



        packet.put("Active States", activeOneStates.toString());
        //packet.put("Beam Breaker", beamBreakerSensor.getState());

        return false;
    }

}

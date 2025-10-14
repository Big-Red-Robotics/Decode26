package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class MyShooterOne {
    private DcMotor beltMotor, flyingWheelMotor;
    private Servo pushingUpServo;
    List<DcMotor> shooterOneMotors;//and servo


    //intake
    private DcMotor intakeMotor;
    private DigitalChannel beamBreakerSensor;

    public enum IntakeState {intake, pause}

    ;
    public MyIntake.IntakeState currentState = MyIntake.IntakeState.pause;
    public boolean intake = false;
    List<DcMotor> intakeMechanism;


    public enum MyShooterOneState {intake, none, belt, push, shoot}

    ;

    public EnumSet<MyShooterOneState> activeOneStates = EnumSet.of(MyShooterOneState.none,
            MyShooterOneState.belt, MyShooterOneState.push, MyShooterOneState.shoot);



    public MyShooterOne(HardwareMap hardwareMap) {
        this.beltMotor = hardwareMap.get(DcMotor.class, RobotConfig.beltMotor);
        this.flyingWheelMotor = hardwareMap.get(DcMotor.class, RobotConfig.flyingWheelMotor);
        this.pushingUpServo = hardwareMap.get(Servo.class, RobotConfig.pushingUpServo);


//intake
        this.intakeMotor = hardwareMap.get(DcMotor.class, RobotConfig.intake);
        this.beamBreakerSensor = hardwareMap.get(DigitalChannel.class, RobotConfig.beamBreaker);
        beamBreakerSensor.setMode(DigitalChannel.Mode.INPUT);

        shooterOneMotors = Arrays.asList(beltMotor, flyingWheelMotor);
        for (DcMotor shooter : shooterOneMotors) {
            shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
//intake
        intakeMechanism = Arrays.asList(intakeMotor);
        for (DcMotor intake : intakeMechanism) {
            intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        beltMotor.setDirection(DcMotorSimple.Direction.REVERSE);//???
        flyingWheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);//???

        //intake
        intakeMotor.setDirection(DcMotor.Direction.REVERSE); //idk if its actually inverse or FORWARD
    }

    public void setActiveOneStatesState(EnumSet<MyShooterOneState> states) {
        this.activeOneStates = EnumSet.copyOf(states);
    }

    public void setIntakePowers(double power) {
        activeOneStates.add(MyShooterOneState.none);

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE); //???
        intakeMotor.setPower(power);//???
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setShooterOneShoot(double power) {
        activeOneStates.add(MyShooterOneState.none);

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

    public void setIntakeMechanism() {

    }
}
//states intake, none, belt, push, shoot do a public void for each through active state to be able to do them parralelly

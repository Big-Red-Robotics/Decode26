package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.RobotConfig;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class MyLift {
    private DcMotor LLift;
    private DcMotor RLift;
    public enum LiftState {down, up};
    public EnumSet<MyLift.LiftState> activeOneStates = EnumSet.of(MyLift.LiftState.down);

    List<DcMotor> liftMechanism;

    public MyLift(HardwareMap hardwareMap) {
        this.LLift = hardwareMap.get(DcMotor.class, RobotConfig.LLift);
        this.RLift = hardwareMap.get(DcMotor.class, RobotConfig.RLift);
        //this.beamBreakerSensor = hardwareMap.get(DigitalChannel.class, RobotConfig.beamBreaker);
        //beamBreakerSensor.setMode(DigitalChannel.Mode.INPUT);

        liftMechanism = Arrays.asList(LLift, RLift);
        for (DcMotor up : liftMechanism) {
            up.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            up.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            up.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        LLift.setDirection(DcMotor.Direction.REVERSE); //idk if its actually inverse or FORWARD
        RLift.setDirection(DcMotor.Direction.FORWARD); //idk if its actually inverse or FORWARD
    }
}

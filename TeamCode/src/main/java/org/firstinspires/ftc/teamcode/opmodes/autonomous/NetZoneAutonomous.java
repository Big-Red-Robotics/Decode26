package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.components.drive.MecanumDrive;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

@Autonomous (name="Net Zone Autonomous")
public class NetZoneAutonomous extends LinearOpMode {

    private DcMotor beltMotor = null;
    private DcMotor shooterMotor = null;
    private CRServo intakeServo = null;

    @Override
    public void runOpMode() throws InterruptedException{

        beltMotor = hardwareMap.get(DcMotor.class, "belt");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        intakeServo = hardwareMap.get(CRServo.class, "intake");

        beltMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        beltMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        Pose2d beginPose = new Pose2d(new Vector2d(-70, 24), Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Action shootOn = (telemetryPacket) -> {
            shooterMotor.setPower(0.7);
            return false;
        };

        Action shootOff = (telemetryPacket) -> {
            shooterMotor.setPower(0.0);
            return false;
        };

        waitForStart();

        intakeServo.setPower(1.0);
        beltMotor.setPower(1.0);

        Action path = drive.actionBuilder(beginPose)
                .lineToXLinearHeading(-30, Math.toRadians(135))
                .stopAndAdd(shootOn)
                .waitSeconds(1.5)
                .stopAndAdd(shootOff)
                .splineToSplineHeading(new Pose2d(-11, 30, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.05)
                .lineToY(37)
                .waitSeconds(0.2)
                .lineToY(42)
                .waitSeconds(0.2)
                .lineToY(47)
                .splineToSplineHeading(new Pose2d(-30, 24, Math.toRadians(135)), Math.toRadians(180))
                .stopAndAdd(shootOn)
                .waitSeconds(1)
                .stopAndAdd(shootOff)
                .splineToSplineHeading(new Pose2d(12, 30, Math.toRadians(90)), Math.toRadians(0))
                .waitSeconds(0.2)
                .lineToY(37)
                .waitSeconds(0.3)
                .lineToY(42)
                .waitSeconds(0.3)
                .lineToY(47)
                .splineToSplineHeading(new Pose2d(-30, 24, Math.toRadians(135)), Math.toRadians(180))
                .stopAndAdd(shootOn)
                .waitSeconds(1)
                .stopAndAdd(shootOff)
                .splineToSplineHeading(new Pose2d(37, 33, Math.toRadians(0)), Math.toRadians(90))
                .build();

        Actions.runBlocking(new SequentialAction(path));
    }
}
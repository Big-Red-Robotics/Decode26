package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.drive.MecanumDrive;

@Disabled
@Autonomous(name="Base Autonomous")
public class _BaseAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() {
        //initialize components
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        telemetry.addLine("waiting to start!");
        telemetry.update();

        while (!isStarted() && !isStopRequested()) {
            //after initialization, before starting

            sleep(20);
        }

        waitForStart();

        //autonomous code
    }
}

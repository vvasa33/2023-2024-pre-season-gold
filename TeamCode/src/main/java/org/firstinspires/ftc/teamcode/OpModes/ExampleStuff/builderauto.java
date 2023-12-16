package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous (name="badauto")
public class builderauto extends LinearOpMode {
    SampleMecanumDrive drive;
    ElapsedTime timer;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        timer = new ElapsedTime();

        waitForStart();

        while (timer.seconds() < 3) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            0,
                            -0.5,
                            0
                    )
            );
            drive.update();
        }
    }
}

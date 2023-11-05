package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous (name="Example Auto w/Roadrunner")
public class AutonomousOpMode extends LinearOpMode {
    SampleMecanumDrive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(0,0,Math.toRadians(90)));
        Trajectory cool = drive.trajectoryBuilder(drive.getPoseEstimate()).
                lineToLinearHeading(new Pose2d(0,30, Math.toRadians(270))).
                build();
        Trajectory newtraj = drive.trajectoryBuilder(cool.end())
                        .lineToLinearHeading(new Pose2d(0,0, Math.toRadians(90)))
                                .build();

        waitForStart();

        drive.followTrajectory(cool);
        drive.followTrajectory(newtraj);

    }
}

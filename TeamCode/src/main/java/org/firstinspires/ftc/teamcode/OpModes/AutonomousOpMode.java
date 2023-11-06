package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous (name="Example Auto w/Roadrunner")
public class AutonomousOpMode extends LinearOpMode {
    SampleMecanumDrive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(6.2, 62.7, Math.toRadians(270)));
        TrajectorySequence newTraj = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineToLinearHeading(new Pose2d(45, 35.3, 0))
                .lineToLinearHeading(new Pose2d(13.9,58.7, Math.toRadians(180)))
                .splineTo(new Vector2d(-35.8, 58.9), Math.toRadians(180))
                .splineTo(new Vector2d(-64.3, 35.8), Math.toRadians(180))
                .build();

        waitForStart();

        

    }
}

package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.Vector;


@Autonomous (name="LeftOptimalAutonomous", group="OptimalAutos")
public class LeftOptimalAutonomous extends LinearOpMode {
    SampleMecanumDrive drive;

    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d((14.65/2) + 2, 62.7, Math.toRadians(90)));

        //TODO vision code these are stand-ins
//        Trajectory visionScorer = drive.trajectoryBuilder(drive.getPoseEstimate())
//                .lineTo(new Vector2d(22.7, 46.4))
//                .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
//                .build();
//        Trajectory lineUpToPass = drive.trajectoryBuilder(visionScorer.end())
//                .lineTo(new Vector2d(46,41.1))
//                .build();
        TrajectorySequence visionScorer = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(22.7, 46.4))
                .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
                .lineTo(new Vector2d(46, 41.1))
                .build();


        TrajectorySequence optimalAutoSequence = drive.trajectorySequenceBuilder(visionScorer.end())
                //THIS REPEATS (START)
                .splineTo(new Vector2d(10,10), Math.toRadians(180))
                .splineTo(new Vector2d(-36.3, 10), Math.toRadians(180))
                .splineTo(new Vector2d(-58,11.8), Math.toRadians(180))
                .waitSeconds(0.5)
                .lineTo(new Vector2d(-57, 11.8))
                .splineTo(new Vector2d(-36.3, 10), Math.toRadians(0))
                .splineTo(new Vector2d(10,10), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
                .waitSeconds(0.5)
                .lineTo(new Vector2d(46, 41.1))
                //REPEAT END
                .splineTo(new Vector2d(10,10), Math.toRadians(180))
                .splineTo(new Vector2d(-36.3, 10), Math.toRadians(180))
                .splineTo(new Vector2d(-58,11.8), Math.toRadians(180))
                .waitSeconds(0.5)
                .lineTo(new Vector2d(-57, 11.8))
                .splineTo(new Vector2d(-36.3, 10), Math.toRadians(0))
                .splineTo(new Vector2d(10,10), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
                .waitSeconds(0.5)
                .lineTo(new Vector2d(46, 41.1))
                //THIRD
                .splineTo(new Vector2d(10,10), Math.toRadians(180))
                .splineTo(new Vector2d(-36.3, 10), Math.toRadians(180))
                .splineTo(new Vector2d(-58,11.8), Math.toRadians(180))
                .waitSeconds(0.5)
                .lineTo(new Vector2d(-57, 11.8))
                .splineTo(new Vector2d(-36.3, 10), Math.toRadians(0))
                .splineTo(new Vector2d(10,10), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
                .waitSeconds(0.5)
                .lineToLinearHeading(new Pose2d(50, 15.5, Math.toRadians(210)))
                .build();

                telemetry.addLine("> Initialization sequence done. Press start to play...");
                telemetry.update();

                waitForStart();
                drive.followTrajectorySequence(visionScorer);
                drive.followTrajectorySequence(optimalAutoSequence);
    }
}

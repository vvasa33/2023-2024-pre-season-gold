package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.opencv.core.Mat;

import java.lang.invoke.VolatileCallSite;
import java.util.TreeMap;
import java.util.Vector;

//import org.firstinspires.ftc.teamcode.OpModes.ExampleStuff.LocalizationStorageCloser;

@Autonomous (name="ChoosableAutoExample", group="Examples")
public class ChoosableAutoExample extends LinearOpMode {
    SampleMecanumDrive drive;

    enum DriveState {

    }

    ElapsedTime timer;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(14.65/2, 62.7, Math.toRadians(90)));
        timer = new ElapsedTime();

        if (LocalizationStorageCloser.firstPassToPixel == null) {
            telemetry.addLine("Trajectories are not enabled. Please configure them and come back...");
            telemetry.update();
            return;
        }

        //TODO vision code + all of the trajectories for that
        //this is a fake stand in
        Trajectory visionScorer = drive.trajectoryBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(22.7, 46.4))
                .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
                .build();
        Trajectory lineUpToPass = drive.trajectoryBuilder(visionScorer.end())
                .lineTo(new Vector2d(46,41.1))
                .build();

        Trajectory firstPassToPixel = null;
        switch (LocalizationStorageCloser.firstPassToPixel) {
            case DOOR: default:
                firstPassToPixel = drive.trajectoryBuilder(lineUpToPass.end())
                        .splineTo(new Vector2d(10,10), Math.toRadians(180))
                        .splineTo(new Vector2d(-36.3, 10), Math.toRadians(180))
                        .build();
                break;
            case TRUSS:
                firstPassToPixel = drive.trajectoryBuilder(lineUpToPass.end())
                        .splineTo(new Vector2d(11.0, 59.6), Math.toRadians(180))
                        .splineTo(new Vector2d(-36.3, 59.6), Math.toRadians(180))
                        .build();
                break;
        }
        Trajectory firstPixelStack;
        Trajectory lineUpToGoBackFirstPass;

        switch (LocalizationStorageCloser.firstPixelStack) {
            case LEFT: default:
                firstPixelStack = drive.trajectoryBuilder(firstPassToPixel.end())
                        .splineTo(new Vector2d(-58,11.8), Math.toRadians(180))
                        .build();
                break;
            case MIDDLE:
                firstPixelStack = drive.trajectoryBuilder(firstPassToPixel.end())
                        .splineTo(new Vector2d(-58,23.8), Math.toRadians(180))
                        .build();
                break;
            case RIGHT:
                firstPixelStack = drive.trajectoryBuilder(firstPassToPixel.end())
                        .splineTo(new Vector2d(-58,35.6), Math.toRadians(180))
                        .build();
                break;
        }
        lineUpToGoBackFirstPass = drive.trajectoryBuilder(firstPixelStack.end())
                .lineTo(new Vector2d(firstPixelStack.end().getX() - 1, firstPixelStack.end().getY()))
                .build();

        Trajectory firstPassToScore;
        switch (LocalizationStorageCloser.firstPassToScore) {
            case DOOR: default:
                firstPassToScore = drive.trajectoryBuilder(lineUpToGoBackFirstPass.end())
                        .splineTo(new Vector2d(-36.3, 10), Math.toRadians(0))
                        .splineTo(new Vector2d(10,10), Math.toRadians(0))
                        .build();
                break;
            case TRUSS:
                firstPassToScore = drive.trajectoryBuilder(lineUpToGoBackFirstPass.end())
                        .splineTo(new Vector2d(-36.3, 59.6), Math.toRadians(0))
                        .splineTo(new Vector2d(11, 59.6), Math.toRadians(0))
                        .build();
                break;
        }

        Trajectory firstCycleScore = null;
        Trajectory firstCycleScoreAlign;
        switch (LocalizationStorageCloser.firstCycleScore) {
            case LEFT:
                firstCycleScore = drive.trajectoryBuilder(firstPassToScore.end())
                        .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
                        .build();
                break;
            case MIDDLE:
                firstCycleScore = drive.trajectoryBuilder(firstPassToScore.end())
                        .splineToLinearHeading(new Pose2d(47.1, 34.7, Math.toRadians(180)), Math.toRadians(0))
                        .build();
                break;
            case RIGHT: default:
                firstCycleScore = drive.trajectoryBuilder(firstPassToScore.end())
                        .splineToLinearHeading(new Pose2d(47.1, 28.4, Math.toRadians(180)), Math.toRadians(0))
                        .build();
                break;
            case AUTOSELECT:
                //TODO do this once the vision do it too
        }

        firstCycleScoreAlign = drive.trajectoryBuilder(firstPassToScore.end())
                .lineTo(new Vector2d(firstPassToScore.end().getX()-1, firstPassToScore.end().getY()))
                .build();

        //SECOND PASS

        Trajectory secondPassToPixel = null;
        switch (LocalizationStorageCloser.secondPassToPixel) {
            case DOOR: default:
                secondPassToPixel = drive.trajectoryBuilder(firstCycleScoreAlign.end())
                        .splineTo(new Vector2d(10,10), Math.toRadians(180))
                        .splineTo(new Vector2d(-36.3, 10), Math.toRadians(180))
                        .build();
                break;
            case TRUSS:
                secondPassToPixel = drive.trajectoryBuilder(firstCycleScoreAlign.end())
                        .splineTo(new Vector2d(11.0, 59.6), Math.toRadians(180))
                        .splineTo(new Vector2d(-36.3, 59.6), Math.toRadians(180))
                        .build();
                break;
        }
        Trajectory secondPixelStack;
        Trajectory secondLineUpToGoBackSecondPass;

        switch (LocalizationStorageCloser.secondPixelStack) {
            case LEFT: default:
                secondPixelStack = drive.trajectoryBuilder(secondPassToPixel.end())
                        .splineTo(new Vector2d(-58,11.8), Math.toRadians(180))
                        .build();
                break;
            case MIDDLE:
                secondPixelStack = drive.trajectoryBuilder(secondPassToPixel.end())
                        .splineTo(new Vector2d(-58,23.8), Math.toRadians(180))
                        .build();
                break;
            case RIGHT:
                secondPixelStack = drive.trajectoryBuilder(secondPassToPixel.end())
                        .splineTo(new Vector2d(-58,35.6), Math.toRadians(180))
                        .build();
                break;
        }
        secondLineUpToGoBackSecondPass = drive.trajectoryBuilder(firstPixelStack.end())
                .lineTo(new Vector2d(secondPixelStack.end().getX() - 1, secondPixelStack.end().getY()))
                .build();

        Trajectory secondPassToScore;
        switch (LocalizationStorageCloser.secondPassToScore) {
            case DOOR: default:
                secondPassToScore = drive.trajectoryBuilder(secondLineUpToGoBackSecondPass.end())
                        .splineTo(new Vector2d(-36.3, 10), Math.toRadians(0))
                        .splineTo(new Vector2d(10,10), Math.toRadians(0))
                        .build();
                break;
            case TRUSS:
                secondPassToScore = drive.trajectoryBuilder(secondLineUpToGoBackSecondPass.end())
                        .splineTo(new Vector2d(-36.3, 59.6), Math.toRadians(0))
                        .splineTo(new Vector2d(11, 59.6), Math.toRadians(0))
                        .build();
                break;
        }

        Trajectory secondCycleScore = null;

        switch (LocalizationStorageCloser.secondCycleScore) {
            case LEFT:
                secondCycleScore = drive.trajectoryBuilder(secondPassToScore.end())
                        .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
                        .build();
                break;
            case MIDDLE:
                secondCycleScore = drive.trajectoryBuilder(secondPassToScore.end())
                        .splineToLinearHeading(new Pose2d(47.1, 34.7, Math.toRadians(180)), Math.toRadians(0))
                        .build();
                break;
            case RIGHT: default:
                secondCycleScore = drive.trajectoryBuilder(secondPassToScore.end())
                        .splineToLinearHeading(new Pose2d(47.1, 28.4, Math.toRadians(180)), Math.toRadians(0))
                        .build();
                break;
            case AUTOSELECT:
                //TODO do this once the vision do it too
        }

        Trajectory parking;

        switch (LocalizationStorageCloser.parking) {
            case LEFT: default:
                assert secondCycleScore != null;
                parking = drive.trajectoryBuilder(secondCycleScore.end())
                        .lineToLinearHeading(new Pose2d(50, 58.4, Math.toRadians(225)))
                        .build();
                break;
            case RIGHT:
                assert secondCycleScore != null;
                parking = drive.trajectoryBuilder(secondCycleScore.end())
                        .lineToLinearHeading(new Pose2d(50, 15.5, Math.toRadians(210)))
                        .build();
                break;
        }

        telemetry.addLine("> Initialized. Press Start to play...");
        telemetry.update();

        waitForStart();

        drive.followTrajectory(visionScorer);
        drive.followTrajectory(lineUpToPass);
        drive.followTrajectory(firstPassToPixel);
        drive.followTrajectory(firstPixelStack);
        drive.followTrajectory(lineUpToGoBackFirstPass);
        drive.followTrajectory(firstPassToScore);
        drive.followTrajectory(firstCycleScore);
        drive.followTrajectory(firstCycleScoreAlign);
        drive.followTrajectory(secondPassToPixel);
        drive.followTrajectory(secondPixelStack);
        drive.followTrajectory(secondLineUpToGoBackSecondPass);
        drive.followTrajectory(secondPassToScore);
        drive.followTrajectory(secondCycleScore);
        drive.followTrajectory(parking);

    }
}

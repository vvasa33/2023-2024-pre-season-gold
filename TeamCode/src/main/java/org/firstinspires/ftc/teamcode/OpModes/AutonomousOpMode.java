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
        
        int position = 0; //get from the vision portal

        //if statement stuff for the position

        TrajectorySequence vision = drive.trajectorySequenceBuilder(new Pose2d((14.65 / 2) + 1, -62.7, Math.toRadians(270)))
                .lineToLinearHeading(new Pose2d(6.5,-41.4,  5 * Math.PI / 3))
                .addDisplacementMarker(() -> {
                    //intake.setPower(0.01); //pushes the pixel out
                })
                .splineToLinearHeading(new Pose2d(47.1, -28.4, Math.toRadians(180)), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    //run to position for the lift and make the arm come out, then score the yellow pixel
                })
                .lineTo(new Vector2d(46, -28.4)) //left to align
                .build();

        TrajectorySequence extras = drive.trajectorySequenceBuilder(vision.end())
                .splineTo(new Vector2d(10,-10), Math.toRadians(180)) //align with the backstage door
                .splineTo(new Vector2d(-36.3, -10), Math.toRadians(180)) //go through door

                .splineTo(new Vector2d(-58, -11.8), Math.toRadians(180)) //go to right pixel stack

                .addTemporalMarker(5, () -> {
                    //intake.setPower(1);
                })

                .lineTo(new Vector2d(-56, -11.8))

                .addTemporalMarker(7, () -> {
                    //intake.setPower(-1);
                })

                .addTemporalMarker(9, () -> {
                    //intake.setPower(0);
                })

                .splineTo(new Vector2d(-36.3,-10), Math.toRadians(0)) //line up with the backstage door
                .splineTo(new Vector2d(10,-10), Math.toRadians(0)) //go through

                .splineToLinearHeading(new Pose2d(47.1, -34.7, Math.toRadians(180)), Math.toRadians(0)) //board spot middle

                .lineTo(new Vector2d(46, -34.7)) //middle to align

                .splineTo(new Vector2d(10,-10), Math.toRadians(180)) //align with the backstage door
                .splineTo(new Vector2d(-36.3, -10), Math.toRadians(180)) //go through door

                .splineTo(new Vector2d(-58, -11.8), Math.toRadians(180)) //go to right pixel stack

                .waitSeconds(0.5)

                .lineTo(new Vector2d(-56, -11.8))

                .splineTo(new Vector2d(-36.3,-10), Math.toRadians(0)) //line up with the backstage door
                .splineTo(new Vector2d(10,-10), Math.toRadians(0)) //go through

                .splineToLinearHeading(new Pose2d(47.1, -34.7, Math.toRadians(180)), Math.toRadians(0))//score

                .lineTo(new Vector2d(46, -34.7)) //middle to align

                .splineTo(new Vector2d(10,-10), Math.toRadians(180)) //align with the backstage door
                .splineTo(new Vector2d(-36.3, -10), Math.toRadians(180)) //go through door

                .splineTo(new Vector2d(-58, -23.8), Math.toRadians(180)) //go to middle pixel stack

                .waitSeconds(0.5)

                .lineTo(new Vector2d(-56, -23.8))

                .splineTo(new Vector2d(-36.3,-10), Math.toRadians(0)) //line up with the backstage door
                .splineTo(new Vector2d(10,-10), Math.toRadians(0)) //go through

                .splineToLinearHeading(new Pose2d(47.1, -34.7, Math.toRadians(180)), Math.toRadians(0))//score

                //.lineToLinearHeading(new Pose2d(47.1, -59.4, Math.toRadians(135)))
                .build();


        waitForStart();

        drive.followTrajectorySequence(vision);
        drive.followTrajectorySequence(extras);
    }
}

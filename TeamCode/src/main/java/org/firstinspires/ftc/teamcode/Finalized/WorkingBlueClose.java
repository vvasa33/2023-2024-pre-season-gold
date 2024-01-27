package org.firstinspires.ftc.teamcode.Finalized;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CameraStuff.PropDetectorBlue;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.OpModes.ExampleStuff.autotesting;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;


@Autonomous (name="WorkingBlueClose", group="FINAL")
public class WorkingBlueClose extends LinearOpMode {
    private VisionPortal portal;
    private PropDetectorBlue pipeline;

    SampleMecanumDrive drive;
    DcMotorEx intake;
    DcMotorEx lift;

    ElapsedTime depositTimer, liftTimer, gripTimer;


    enum LiftStates {
        WAITING,
        EXTEND,
        DEPOSIT,
        RETRACT,
        DOWN
    }

    public LiftStates currentLiftState = LiftStates.WAITING;
    public LiftStates previousLiftState = LiftStates.WAITING;

    public enum ClawStates {
        OPEN,
        CLOSED
    }

    public ClawStates frontClawState = ClawStates.OPEN;
    public ClawStates backClawState = ClawStates.OPEN;

    Servo arm;
    Servo joint;
    Servo frontClaw, backClaw;

    @Override
    public void runOpMode() throws InterruptedException {
        pipeline = new PropDetectorBlue();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "0")) //the zero represents the first webcam detected by the control hub os
                .setCameraResolution(new Size(640,480))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(pipeline)
                .build();



        //drive.setPoseEstimate();
        Trajectory spikeMark, moveBack, splineTo, boardMoveBack;

        drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d(15, 62.7, Math.toRadians(270)));




        intake = hardwareMap.get(DcMotorEx.class, "motor");
        lift = hardwareMap.get(DcMotorEx.class, "lift");

        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        depositTimer = new ElapsedTime();
        liftTimer = new ElapsedTime();
        gripTimer = new ElapsedTime();

        depositTimer.reset();
        liftTimer.reset();
        gripTimer.reset();

        arm = hardwareMap.get(Servo.class, "arm"); //we control both with one thing now yay

        joint = hardwareMap.get(Servo.class, "joint");
        frontClaw = hardwareMap.get(Servo.class, "claw1");
        backClaw = hardwareMap.get(Servo.class, "claw2");

        arm.setPosition(0.02);
        joint.setPosition(0.48);




        while (opModeInInit()) {
            telemetry.addLine(String.valueOf(pipeline.getArea()));
            telemetry.update();
            if (gripTimer.seconds() > 1) {
                frontClaw.setPosition(0.55);
                backClaw.setPosition(0.52);
            }
        }

        waitForStart();

        switch (pipeline.getArea()) {
            case LEFT: default:
                spikeMark = drive.trajectoryBuilder(new Pose2d(15, 62.7, Math.toRadians(270)))
                        .lineTo(new Vector2d(26.7,48))
                        .build();
                moveBack = drive.trajectoryBuilder(spikeMark.end())
                        .lineTo(new Vector2d(23.7, 52))
                        .build();
                splineTo = drive.trajectoryBuilder(moveBack.end())
                        .splineToLinearHeading(new Pose2d(50.5, 43.5, Math.toRadians(180)), Math.toRadians(0))
                        .addDisplacementMarker(() -> {
                            backClaw.setPosition(0.52);
                            depositTimer.reset();
                            currentLiftState = LiftStates.DEPOSIT;
                        })
                        .build();
                boardMoveBack = drive.trajectoryBuilder(splineTo.end())
                        .lineTo(new Vector2d(48, 43.5))
                        .build();
                break;
            case CENTER:
                spikeMark = drive.trajectoryBuilder(new Pose2d(15, 62.7, Math.toRadians(270)))
                        .lineTo(new Vector2d(16,37))
                        .build();
                moveBack = drive.trajectoryBuilder(spikeMark.end())
                        .lineTo(new Vector2d(16, 47))
                        .build();
                splineTo = drive.trajectoryBuilder(moveBack.end())
                        .splineToLinearHeading(new Pose2d(50.7, 34.7, Math.toRadians(180)), Math.toRadians(0))
                        .addDisplacementMarker(() -> {
                            backClaw.setPosition(0.52);
                            depositTimer.reset();
                            currentLiftState = LiftStates.DEPOSIT;
                        })
                        .build();
                boardMoveBack = drive.trajectoryBuilder(splineTo.end())
                        .lineTo(new Vector2d(48, 34.7))
                        .build();
                break;
            case RIGHT:
                spikeMark = drive.trajectoryBuilder(new Pose2d(15, 62.7, Math.toRadians(270)))
                        .lineToLinearHeading(new Pose2d(13.7,35,  Math.toRadians(180)))
                        .build();
                moveBack = drive.trajectoryBuilder(spikeMark.end())
                        .lineTo(new Vector2d(20, 35))
                        .build();
                splineTo = drive.trajectoryBuilder(moveBack.end())
                        .lineToLinearHeading(new Pose2d(51, 28.9, Math.toRadians(180))) //board spot right
                        .addDisplacementMarker(() -> {
                            backClaw.setPosition(0.52);
                            depositTimer.reset();
                            currentLiftState = LiftStates.DEPOSIT;
                        })
                        .build();
                boardMoveBack = drive.trajectoryBuilder(splineTo.end())
                        .lineTo(new Vector2d(48, 27))
                        .build();
        }
        Trajectory park = drive.trajectoryBuilder(boardMoveBack.end())
                .lineToLinearHeading(new Pose2d(44, 58, Math.toRadians(225)))
                .build();


        TrajectorySequence seq = drive.trajectorySequenceBuilder(new Pose2d(15, 62.7, Math.toRadians(270)))

                .addTrajectory(spikeMark)
                //run the intake
                .addTemporalMarker(1.2, () -> {
                    intake.setPower(0.35);
                    currentLiftState = LiftStates.EXTEND;
                    backClaw.setPosition(0.52);
                })

                .addTrajectory(moveBack)

                .addTemporalMarker(3, () -> {
                    intake.setPower(0);
                    backClaw.setPosition(0.52);
                    currentLiftState = LiftStates.DOWN;
                })

                .addTrajectory(splineTo)


                .addTrajectory(boardMoveBack)
                //.lineTo(new Vector2d(46, 41.1))
                .addTrajectory(park)
                .build();

        drive.followTrajectorySequenceAsync(seq);


        while (opModeIsActive() && !isStopRequested()) {
            drive.update();

            switch (currentLiftState) {
                case WAITING:
                    break;
                case EXTEND:
                    lift.setTargetPosition(2000);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(1);

                    if (lift.getCurrentPosition() > 1200) {
                        arm.setPosition(0.5);
                        joint.setPosition(0.67);
                    }

                    if (previousLiftState == LiftStates.DOWN && liftTimer.seconds() > 0.5) {
                        currentLiftState = LiftStates.RETRACT;
                    }
                    break;
                case DEPOSIT:
                    backClaw.setPosition(0.37);
                    if (depositTimer.seconds() > 0.2) {
                        liftTimer.reset();
                        currentLiftState = LiftStates.EXTEND;
                        previousLiftState = LiftStates.DOWN;
                    }
                    break;
                case DOWN:
                    lift.setTargetPosition(1000);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(1);
                    break;
                case RETRACT:
                    if (liftTimer.seconds() > 1) {
                        lift.setTargetPosition(0);
                        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift.setPower(1);
                    }

                    if (liftTimer.seconds() > 0.3) {
                        arm.setPosition(0.02);
                        joint.setPosition(0.48);
                    }

                    if (lift.getCurrentPosition() < 5) {
                        currentLiftState = LiftStates.WAITING;
                    }
                    break;
            }

            telemetry.addData("current lift state" , currentLiftState);
            telemetry.addData("lift encoder", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}

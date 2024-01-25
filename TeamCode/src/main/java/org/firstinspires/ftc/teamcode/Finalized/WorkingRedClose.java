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
import org.firstinspires.ftc.teamcode.CameraStuff.PropDetectorRed;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous (name="WorkingRedClose", group="FINAL")
public class WorkingRedClose extends LinearOpMode {

    private VisionPortal portal;
    private PropDetectorRed pipeline;

    SampleMecanumDrive drive;
    DcMotorEx intake;
    DcMotorEx lift;

    ElapsedTime depositTimer, liftTimer;


    enum LiftStates {
        WAITING,
        EXTEND,
        DEPOSIT,
        RETRACT
    }

    public LiftStates currentLiftState = LiftStates.WAITING;

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
        pipeline = new PropDetectorRed();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "0")) //the zero represents the first webcam detected by the control hub os
                .setCameraResolution(new Size(640,480))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(pipeline)
                .build();

        while (opModeInInit()) {
            telemetry.addLine(String.valueOf(pipeline.getArea()));
            telemetry.update();
        }

        drive.setPoseEstimate(new Pose2d((14.65 / 2) + 1, -62.7, Math.toRadians(90)));
        Trajectory spikeMark;

        switch (pipeline.getArea()) {
            case LEFT: default:
                spikeMark = drive.trajectoryBuilder(drive.getPoseEstimate())
                        .lineToLinearHeading(new Pose2d(10,-35,  Math.toRadians(180)))
                        .splineToLinearHeading(new Pose2d(47.1, -28.4, Math.toRadians(180)), Math.toRadians(0))
                        .lineTo(new Vector2d(46, -28.4))
                        .build();
            case CENTER:
                spikeMark = drive.trajectoryBuilder(drive.getPoseEstimate())
                        .lineTo(new Vector2d(16,-33.7))
                        .lineTo(new Vector2d(16, -40))
                        .splineToLinearHeading(new Pose2d(47.1, -34.7, Math.toRadians(180)), Math.toRadians(0))
                        .lineTo(new Vector2d(46, -34.7))
                        .build();
            case RIGHT:
                spikeMark = drive.trajectoryBuilder(drive.getPoseEstimate())
                        .lineTo(new Vector2d(22.7,-44.4))
                        .splineToLinearHeading(new Pose2d(47.1, -41.1, Math.toRadians(180)), Math.toRadians(0))
                        .lineTo(new Vector2d(46, -41.1))
                        .build();
        }

        drive = new SampleMecanumDrive(hardwareMap);
        intake = hardwareMap.get(DcMotorEx.class, "motor");
        lift = hardwareMap.get(DcMotorEx.class, "lift");

        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        depositTimer = new ElapsedTime();
        liftTimer = new ElapsedTime();

        depositTimer.reset();
        liftTimer.reset();

        arm = hardwareMap.get(Servo.class, "arm"); //we control both with one thing now yay

        joint = hardwareMap.get(Servo.class, "joint");
        frontClaw = hardwareMap.get(Servo.class, "claw1");
        backClaw = hardwareMap.get(Servo.class, "claw2");

        arm.setPosition(0);
        joint.setPosition(0.45);
        frontClaw.setPosition(0.55);
        backClaw.setPosition(0.52);

        TrajectorySequence seq = drive.trajectorySequenceBuilder(new Pose2d((14.65 / 2) + 1, -62.7, Math.toRadians(90)))
                //.lineTo(new Vector2d(22.7,50)) //vision spike left
                //.lineTo(new Vector2d(16,33.7)) //vision spike middle

                .addTrajectory(spikeMark)
                //run the intake
                .addTemporalMarker(1.2, () -> {
                    intake.setPower(0.2);
                    currentLiftState = LiftStates.EXTEND;
                    backClaw.setPosition(0.52);
                })

                //.lineTo(new Vector2d(22.7, 45))
                //.waitSeconds(0.2)
                .addTemporalMarker(3, () -> {
                    intake.setPower(0);
                    backClaw.setPosition(0.52);
                })
                //.splineToLinearHeading(new Pose2d(49.8, 43.5, Math.toRadians(180)), Math.toRadians(0)) //left
                //.splineToLinearHeading(new Pose2d(47.1, 34.7, Math.toRadians(180)), Math.toRadians(0)) //board spot middle
                .addDisplacementMarker(() -> {
                    backClaw.setPosition(0.52);
                    depositTimer.reset();
                    currentLiftState = LiftStates.DEPOSIT;
                })
                //.lineTo(new Vector2d(46, 41.1))
                .lineToLinearHeading(new Pose2d(44, 59.4, Math.toRadians(225)))
                .build();

        drive.followTrajectorySequenceAsync(seq);

        waitForStart();

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
                    break;
                case DEPOSIT:
                    backClaw.setPosition(0.37);
                    if (depositTimer.seconds() > 0.2) {
                        currentLiftState = LiftStates.RETRACT;
                        liftTimer.reset();
                    }
                    break;
                case RETRACT:
                    if (liftTimer.seconds() > 0.5) {
                        lift.setTargetPosition(0);
                        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift.setPower(1);
                    }

                    arm.setPosition(0);
                    joint.setPosition(0.45);

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

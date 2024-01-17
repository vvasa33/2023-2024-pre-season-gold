package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

//@Photon
@Autonomous (name="AUTOTESTING")
public class autotesting extends LinearOpMode {

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


        drive.setPoseEstimate(new Pose2d(14.65/2, 62.7, Math.toRadians(270)));

        TrajectorySequence seq = drive.trajectorySequenceBuilder(new Pose2d(14.65/2, 62.7, Math.toRadians(270)))
                .lineTo(new Vector2d(22.7,48))
                .lineTo(new Vector2d(22.7, 54))
                .addTemporalMarker(1.2, () -> {
                    intake.setPower(0.2);
                    currentLiftState = LiftStates.EXTEND;
                    backClaw.setPosition(0.52);
                })
                //.waitSeconds(0.2)
                .addTemporalMarker(3, () -> {
                    intake.setPower(0);
                    backClaw.setPosition(0.52);
                })
                .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    backClaw.setPosition(0.52);
                    depositTimer.reset();
                    currentLiftState = LiftStates.DEPOSIT;
                })
                .lineTo(new Vector2d(46, 41.1))
                .splineTo(new Vector2d(29,11), Math.toRadians(180)) //align with the backstage door
                .splineTo(new Vector2d(-36.3, 11), Math.toRadians(180)) //go through door
                .splineTo(new Vector2d(-58, 11.8), Math.toRadians(180))
                .build();

        drive.followTrajectorySequenceAsync(seq);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            drive.update();

            switch (currentLiftState) {
                case WAITING:
                    break;
                case EXTEND:
                    lift.setTargetPosition(1200);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(1);

                    if (lift.getCurrentPosition() > 800) {
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

                    if (lift.getCurrentPosition() == 0) {
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

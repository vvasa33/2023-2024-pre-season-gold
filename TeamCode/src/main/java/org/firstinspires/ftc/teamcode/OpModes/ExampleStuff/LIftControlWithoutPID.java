package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name="liftcontrolnopid")
public class LIftControlWithoutPID extends LinearOpMode {
    SampleMecanumDrive drive;
    DcMotorEx lift;

    Servo arm1, arm2;
    Servo joint;
    Servo claw1, claw2;

    public static int target;
    @Override
    public void runOpMode() throws InterruptedException {
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //HardwareConstants.currentLiftPosition = HardwareConstants.LiftPositions.GROUND;
        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.WAITING;

        arm1 = hardwareMap.get(Servo.class, "arm");
        arm2 = hardwareMap.get(Servo.class, "arm2");
        joint = hardwareMap.get(Servo.class, "joint");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");

        //everything is in position and the servos are closed right now
        arm1.setPosition(0);
        arm2.setPosition(0);
        joint.setPosition(0.69);
        claw1.setPosition(0.55);
        claw2.setPosition(0.85);

        HardwareConstants.currentLeftClawState = HardwareConstants.ClawStates.CLOSED;
        HardwareConstants.currentRightClawState = HardwareConstants.ClawStates.CLOSED;


//        arm1.setPosition(0);
//        arm2.setPosition(0);
//        joint.setPosition(0.69);
//        claw1.setPosition(0.55);
//        claw2.setPosition(0.85);

        drive = new SampleMecanumDrive(hardwareMap);

//        PhotonCore.experimental.setMaximumParallelCommands(6);
//        PhotonCore.start(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad1.a && HardwareConstants.currentLiftState != HardwareConstants.LiftStates.WAITING) {
                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.RETRACT;
            } else if (gamepad1.b) {
                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.EXTEND;
                target = HardwareConstants.LiftPositions.SETLINE_1.getValue();
            } else if (gamepad1.x) {
                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.EXTEND;
                target = HardwareConstants.LiftPositions.SETLINE_2.getValue();
            }
//            } else if (gamepad1.y) {
//                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.EXTEND;
//                target = HardwareConstants.LiftPositions.SETLINE_3.getValue();
//            }

            if (gamepad1.left_bumper && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.CLOSED) {
                claw1.setPosition(0.3);
            } else if (gamepad1.left_bumper && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.OPEN) {
                claw1.setPosition(1);
            }

            if (gamepad1.right_bumper && HardwareConstants.currentRightClawState == HardwareConstants.ClawStates.CLOSED) {
                claw2.setPosition(0.55);
            } else if (gamepad1.right_bumper && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.OPEN) {
                claw2.setPosition(0.85);
            }

            switch (HardwareConstants.currentLiftState) {
                case WAITING:
                    break;
                case EXTEND:
                    lift.setTargetPosition(target);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(0.7);
                    if (!lift.isBusy()) {
                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.DEPOSIT;
                    }

                    if (lift.getCurrentPosition() > HardwareConstants.threshold) {
                        arm1.setPosition(0.7);
                        arm2.setPosition(0.7);

                        joint.setPosition(1);
                    }

                    break;
                case DEPOSIT:
                    if (gamepad1.left_bumper) {
                        claw1.setPosition(0.3);
                        HardwareConstants.currentLeftClawState = HardwareConstants.ClawStates.OPEN;
                    } else if (gamepad1.right_bumper) {
                        claw2.setPosition(0.55);
                        HardwareConstants.currentRightClawState = HardwareConstants.ClawStates.OPEN;
                    }

                    if (HardwareConstants.currentRightClawState == HardwareConstants.ClawStates.OPEN && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.OPEN) {
                        sleep(200); //wait a little bit so that the

                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.RETRACT;
                    }
                    break;

                case RETRACT:
                    claw1.setPosition(0.3);
                    claw2.setPosition(0.55);
                    joint.setPosition(0.69);
                    arm1.setPosition(0);
                    arm2.setPosition(0);
                    lift.setTargetPosition(0);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(0.7);

//                    if (lift.getCurrentPosition() < HardwareConstants.threshold) {
//                        arm1.setPosition(0);
//                        arm2.setPosition(0);
//
//                        joint.setPosition(0.69);
//                    }

                    if (!lift.isBusy()) {
                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.WAITING;
                    }
                    break;
            }

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y / ((gamepad1.left_bumper) ? 3 : 1),
                            -gamepad1.left_stick_x / ((gamepad1.left_bumper) ? 3 : 1),
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();


        }
    }
}
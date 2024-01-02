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
import org.opencv.engine.OpenCVEngineInterface;

@TeleOp(name="liftcontrolnopid")
public class LIftControlWithoutPID extends LinearOpMode {
    SampleMecanumDrive drive;
    DcMotorEx lift, motor; //intake motor is called motor

    Servo arm1, arm2;
    Servo joint;
    Servo claw1, claw2;

    Servo airplane;

    public static int target;
    @Override
    public void runOpMode() throws InterruptedException {
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //HardwareConstants.currentLiftPosition = HardwareConstants.LiftPositions.GROUND;
        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.WAITING;

        motor = hardwareMap.get(DcMotorEx.class, "motor");

        arm1 = hardwareMap.get(Servo.class, "arm");
        arm2 = hardwareMap.get(Servo.class, "arm2");
        joint = hardwareMap.get(Servo.class, "joint");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        airplane = hardwareMap.get(Servo.class, "airplane");

        //everything is in position and the servos are closed right now
        arm1.setPosition(0.6);
        arm2.setPosition(0.6);
        joint.setPosition(1);
        claw1.setPosition(1);
        claw2.setPosition(0);
        airplane.setPosition(1);

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
            } else if (gamepad1.y) {
                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.EXTEND;
                target = 1100;
            }
//            } else if (gamepad1.y) {
//                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.EXTEND;
//                target = HardwareConstants.LiftPositions.SETLINE_3.getValue();
//            }

//            'if (gamepad1.left_bumper && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.CLOSED) {
//                claw1.setPosition(1);
//                HardwareConstants.currentLeftClawState = HardwareConstants.ClawStates.OPEN;
//            } else if (gamepad1.left_bumper && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.OPEN) {
//                claw1.setPosition(0);
//                HardwareConstants.currentLeftClawState = HardwareConstants.ClawStates.CLOSED;
//            }
//
//            if (gamepad1.right_bumper && HardwareConstants.currentRightClawState == HardwareConstants.ClawStates.CLOSED) {
//                claw2.setPosition(0);
//                HardwareConstants.currentRightClawState = HardwareConstants.ClawStates.OPEN;
//            } else if (gamepad1.right_bumper && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.OPEN) {
//                claw2.setPosition(0.85);
//                HardwareConstants.currentRightClawState = HardwareConstants.ClawStates.CLOSED;
//            }'

            switch (HardwareConstants.currentLiftState) {
                case WAITING:
                    break;
                case EXTEND:
                    if (lift.getCurrentPosition() > HardwareConstants.threshold) {
//                        arm1.setPosition(0.5);//change this
//                        arm2.setPosition(0.5);
//                        joint.setPosition(1);
                    }

                    lift.setTargetPosition(target * 2);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(1);
                    if (!lift.isBusy()) {
                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.DEPOSIT;
                    }

                    break;
                case DEPOSIT:

                    break;

                case RETRACT:
//                    arm1.setPosition(0.6);
//                    arm2.setPosition(0.6);
//                    joint.setPosition(1);
//                    claw1.setPosition(1);
//                    claw2.setPosition(0);
                    lift.setTargetPosition(0);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(1);

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

            if (gamepad1.left_trigger > 0.1) {
                motor.setPower(-gamepad1.left_trigger); //spit out
            } else if (gamepad1.right_trigger > 0.1) {
                motor.setPower(gamepad1.right_trigger); //spit in
            } else {
                motor.setPower(0);
            }

            if (gamepad2.a) {
                airplane.setPosition(1); //reset
            } else if (gamepad2.b) {
                airplane.setPosition(0); //throw
            }

            telemetry.addData("lift", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}

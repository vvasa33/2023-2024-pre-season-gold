package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.apache.commons.math3.stat.inference.MannWhitneyUTest;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Photon
@TeleOp (name="sensorsnopid")
public class LiftControlNoPIDRewrite extends LinearOpMode {
    SampleMecanumDrive drive;
    DcMotorEx lift, motor; //intake motor is called motor

    Servo arm;
    Servo joint;
    Servo frontClaw, backClaw;
    ElapsedTime backTimer, frontTimer;

    Servo airplane;

    ColorSensor backSensor, frontSensor;

    public static int target;

    private enum LiftStates {
        WAITING,
        EXTENDING,
        DEPOSIT,
        RETRACT,
        MANUAL;
    }

    public LiftStates liftState = LiftStates.WAITING;

    public enum ClawStates {
        OPEN,
        CLOSED
    }

    public ClawStates frontClawState = ClawStates.OPEN;
    public ClawStates backClawState = ClawStates.OPEN;

    public enum LiftPositions {
        GROUND (0),
        SETLINE_1 (900),
        SETLINE_2 (2000),
        HANG (1100);

        private final int val;

        LiftPositions(int val) {
            this.val = val;
        }

        public int getValue() {
            return val;
        }
    }

    public int previousBack = 0;
    public int previousFront = 0;

    public boolean sensorOverride = false;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backSensor = hardwareMap.get(ColorSensor.class, "back");
        frontSensor = hardwareMap.get(ColorSensor.class, "front");

        motor = hardwareMap.get(DcMotorEx.class, "motor");

        arm = hardwareMap.get(Servo.class, "arm"); //we control both with one thing now yay

        joint = hardwareMap.get(Servo.class, "joint");
        frontClaw = hardwareMap.get(Servo.class, "claw1");
        backClaw = hardwareMap.get(Servo.class, "claw2");
        airplane = hardwareMap.get(Servo.class, "airplane");

        backTimer = new ElapsedTime();
        frontTimer = new ElapsedTime();
        telemetry.addLine("Optimized with Photon, press play to start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad2.a && liftState != LiftStates.WAITING) {
                liftState = LiftStates.RETRACT;
            } else if (gamepad2.b) {
                liftState = LiftStates.EXTENDING;
                target = LiftPositions.SETLINE_1.getValue();
            } else if (gamepad2.x) {
                liftState = LiftStates.EXTENDING;
                target = LiftPositions.SETLINE_2.getValue();
            } else if (gamepad2.y) {
                liftState = LiftStates.EXTENDING;
                target = LiftPositions.HANG.getValue();
            } else if (gamepad2.dpad_up) {
                liftState = LiftStates.MANUAL;
            }

            if (previousFront != frontSensor.argb() && !sensorOverride && liftState == LiftStates.WAITING) {
                frontClaw.setPosition(1);
                frontClawState = ClawStates.CLOSED;
            }
            if (previousBack != backSensor.argb() && !sensorOverride && liftState == LiftStates.WAITING) {
                backClaw.setPosition(1);
                backClawState = ClawStates.CLOSED;
            }

            if (gamepad2.left_trigger > 0.5 && frontClawState == ClawStates.OPEN) {
                frontClaw.setPosition(1);
                frontClawState = ClawStates.CLOSED;
                sensorOverride = true;
            } else if (gamepad2.left_trigger > 0.5 && frontClawState == ClawStates.CLOSED) {
                frontClaw.setPosition(0);
                frontClawState = ClawStates.OPEN;
                sensorOverride = true;
            }

            if (gamepad2.right_trigger > 0.5 && backClawState == ClawStates.OPEN) {
                backClaw.setPosition(1);
                backClawState = ClawStates.CLOSED;
                sensorOverride = true;
            } else if (gamepad2.right_trigger > 0.5 && backClawState == ClawStates.CLOSED) {
                backClaw.setPosition(0);
                backClawState = ClawStates.OPEN;
                sensorOverride = true;
            }

            switch (liftState) {
                case WAITING:
                    break;
                case EXTENDING:
                    lift.setTargetPosition(target);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(1);

                    if (lift.getCurrentPosition() > HardwareConstants.threshold) {
                        arm.setPosition(1);
                        joint.setPosition(1);
                    }

                    if (!lift.isBusy()) {
                        liftState = LiftStates.DEPOSIT;
                    }
                    break;
                case DEPOSIT:
                    if (frontClawState == ClawStates.OPEN && backClawState == ClawStates.OPEN) {
                        liftState = LiftStates.RETRACT;
                    }
                    break;
                case RETRACT:
                    lift.setTargetPosition(0);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(1);

                    if (lift.getCurrentPosition() < HardwareConstants.threshold) {
                        arm.setPosition(0);
                        joint.setPosition(0);
                    }
                    sensorOverride = false;
                    break;
                case MANUAL:
                    lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    if (gamepad2.dpad_up) {
                        lift.setPower(0.2);
                    } else if (gamepad2.dpad_down) {
                        lift.setPower(-0.2);
                    }
                    if (lift.getCurrentPosition() > HardwareConstants.threshold) {
                        arm.setPosition(1);
                    } else if (lift.getCurrentPosition() < HardwareConstants.threshold) {
                        arm.setPosition(0);
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

            if (gamepad1.left_trigger > 0.1) {
                motor.setPower(-gamepad1.left_trigger); //spit out
            } else if (gamepad1.right_trigger > 0.1) {
                motor.setPower(gamepad1.right_trigger); //spit in
            } else {
                motor.setPower(0);
            }

            if (gamepad2.dpad_left) {
                airplane.setPosition(1); //reset
            } else if (gamepad2.dpad_right) {
                airplane.setPosition(0); //throw
            }

            telemetry.addData("lift", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}

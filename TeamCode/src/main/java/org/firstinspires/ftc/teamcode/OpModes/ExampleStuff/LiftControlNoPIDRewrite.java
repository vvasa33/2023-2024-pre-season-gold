package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Photon
@TeleOp (name="sensorsnopid")
public class LiftControlNoPIDRewrite extends LinearOpMode {
    SampleMecanumDrive drive;
    DcMotorEx lift, intake; //intake motor is called motor

    Servo arm;
    Servo joint;
    Servo frontClaw, backClaw;
    ElapsedTime backTimer, frontTimer, armTimer, liftTimer;

    Servo airplane;
    GamepadEx gamepad;

    ColorSensor backSensor, frontSensor;

    public static int target;

    public static double jointTarget = 0, armTarget = 0;

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

    public int currentFront = 0;
    public int currentBack = 0;

    //public boolean sensorOverride = false;


    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        gamepad = new GamepadEx(gamepad2);

        backSensor = hardwareMap.get(ColorSensor.class, "back");
        frontSensor = hardwareMap.get(ColorSensor.class, "front");
        backSensor.enableLed(true);
        frontSensor.enableLed(true);

        intake = hardwareMap.get(DcMotorEx.class, "motor");


        arm = hardwareMap.get(Servo.class, "arm"); //we control both with one thing now yay
        armTimer = new ElapsedTime();
        liftTimer = new ElapsedTime();

        joint = hardwareMap.get(Servo.class, "joint");
        frontClaw = hardwareMap.get(Servo.class, "claw1");
        backClaw = hardwareMap.get(Servo.class, "claw2");
        airplane = hardwareMap.get(Servo.class, "airplane");

        arm.setPosition(0.97);
        joint.setPosition(0.24);
        frontClaw.setPosition(0.5);
        backClaw.setPosition(0.48);


        backTimer = new ElapsedTime();
        frontTimer = new ElapsedTime();
        telemetry.addLine("Optimized with Photon, press play to start...");
        telemetry.update();

        waitForStart();
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (opModeIsActive() && !isStopRequested()) {
            gamepad.readButtons();
            //presets (lift power is set in the switch (thats the fsm))
            if (gamepad2.a && liftState != LiftStates.WAITING) {
                liftState = LiftStates.RETRACT;
                armTimer.reset();
            } else if (gamepad2.b) {
                liftState = LiftStates.EXTENDING;
                target = LiftPositions.SETLINE_1.getValue();
                jointTarget = 0.35;
                armTarget = 0.5;
            } else if (gamepad2.x) {
                liftState = LiftStates.EXTENDING;
                target = LiftPositions.SETLINE_2.getValue();
                jointTarget = 0.35;
                armTarget = 0.5;
            } else if (gamepad2.y) {
                liftState = LiftStates.EXTENDING;
                target = LiftPositions.HANG.getValue();
                jointTarget = 0.7;
                armTarget = 0.25;
            } else if (gamepad2.dpad_up) {
                liftState = LiftStates.MANUAL;
                lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            //we save this to another variable because these are i2c calls which are slow
            currentFront = frontSensor.argb();
            currentBack = backSensor.argb();

//            if (previousFront != currentFront && !sensorOverride && liftState == LiftStates.WAITING) {
//                frontClaw.setPosition(1);
//                frontClawState = ClawStates.CLOSED;
//            }
//            if (previousBack != currentBack && !sensorOverride && liftState == LiftStates.WAITING) {
//                backClaw.setPosition(1);
//                backClawState = ClawStates.CLOSED;
//            }

//            previousBack = currentBack;
//            previousFront = currentFront;

            //claw stuff (overrides)
            if (gamepad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && frontClawState == ClawStates.OPEN) {
                frontClaw.setPosition(0.4); //close the claw
                frontClawState = ClawStates.CLOSED;
                //sensorOverride = true;
            } else if (gamepad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && frontClawState == ClawStates.CLOSED) {
                frontClaw.setPosition(0.54); //open the claw
                frontClawState = ClawStates.OPEN;
                //sensorOverride = true;
            }

            if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && backClawState == ClawStates.OPEN) {
                backClaw.setPosition(0.55);
                backClawState = ClawStates.CLOSED;
                //sensorOverride = true;
            } else if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && backClawState == ClawStates.CLOSED) {
                backClaw.setPosition(0.4);
                backClawState = ClawStates.OPEN;
                //sensorOverride = true;
            }

            if (gamepad2.dpad_left) {
                frontClaw.setPosition(0.54);
                frontClawState = ClawStates.OPEN;
                backClawState = ClawStates.OPEN;
                backClaw.setPosition(0.4);
            }

            //finite state machine controlling the lift mechanism
            switch (liftState) {
                case WAITING:
                    break;
                case EXTENDING:
                    lift.setTargetPosition(target * 2);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(1);

                    if (lift.getCurrentPosition() > HardwareConstants.threshold) {
                        arm.setPosition(armTarget);
                        joint.setPosition(jointTarget);
                    }

                    if (!lift.isBusy()) {
                        liftState = LiftStates.DEPOSIT;
                    }
                    break;
                case DEPOSIT:
                    if (frontClawState == ClawStates.OPEN && backClawState == ClawStates.OPEN) {
                        liftState = LiftStates.RETRACT;
                        armTimer.reset();

                    }
                    break;
                case RETRACT:
                    if (armTimer.seconds() > 1) {
                        lift.setTargetPosition(LiftPositions.GROUND.getValue());
                        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        telemetry.addLine("yo hi visu I'm in your code :D");
                        telemetry.update();
                        lift.setPower(1);
                    }


                    arm.setPosition(0.97);
                    joint.setPosition(0.24);
                    //sensorOverride = false;
                    break;
                case MANUAL:
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

            //drive code is outsourced to rr because why not
            drive.setWeightedDrivePower(
                    new Pose2d(
                            gamepad1.left_stick_y / ((gamepad1.left_bumper) ? 3 : 1),
                            gamepad1.left_stick_x / ((gamepad1.left_bumper) ? 3 : 1),
                            gamepad1.right_stick_x
                    )
            );

            drive.update();
            //intake stuff, its so simple that we just use if statements
            if (gamepad2.left_trigger > 0.1) {
                intake.setPower(-gamepad2.left_trigger); //spit out
            } else if (gamepad2.right_trigger > 0.1) {
                intake.setPower(gamepad2.right_trigger); //spit in
            } else {
                intake.setPower(0);
            }

            //airplane thrower (insert 9/11 joke)
//            if (gamepad2.dpad_left) {
//                airplane.setPosition(1); //reset
//            } else if (gamepad2.dpad_right) {
//                airplane.setPosition(0); //throw
//            }

            telemetry.addData("lift", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}

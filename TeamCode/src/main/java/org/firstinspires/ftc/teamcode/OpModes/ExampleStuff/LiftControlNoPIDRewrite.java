package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
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
    //MecanumDrive drive;
    DcMotorEx fl, fr, bl, br;
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

    public enum DriveStates {
        FRONT,
        BACK
    }

    public DriveStates currentDriveState = DriveStates.FRONT;

    public int previousBack = 0;
    public int previousFront = 0;

    public int currentFront = 0;
    public int currentBack = 0;

    //public boolean sensorOverride = false;


    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        br = hardwareMap.get(DcMotorEx.class, "br");
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        arm.setPosition(0);
        joint.setPosition(0.45);
        frontClaw.setPosition(0.55);
        backClaw.setPosition(0.37);
        airplane.setPosition(0);


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
                jointTarget = 0.67;
                armTarget = 0.5;
            } else if (gamepad2.x) {
                liftState = LiftStates.EXTENDING;
                target = LiftPositions.SETLINE_2.getValue();
                jointTarget = 0.67;
                armTarget = 0.5;
            } else if (gamepad2.y) {
                liftState = LiftStates.EXTENDING;
                target = LiftPositions.HANG.getValue();
                jointTarget = 0.7;
                armTarget = 1;
            } else if (gamepad2.dpad_up) {
                liftState = LiftStates.MANUAL;
                lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            //we save this to another variable because these are i2c calls which are slow
            //currentFront = frontSensor.argb();
            //currentBack = backSensor.argb();

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
                frontClaw.setPosition(0.35); //close the claw
                frontClawState = ClawStates.CLOSED;
                //sensorOverride = true;
            } else if (gamepad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && frontClawState == ClawStates.CLOSED) {
                frontClaw.setPosition(0.55); //open the claw
                frontClawState = ClawStates.OPEN;
                //sensorOverride = true;
            }

            if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && backClawState == ClawStates.OPEN) {
                backClaw.setPosition(0.52);
                backClawState = ClawStates.CLOSED;
                //sensorOverride = true;
            } else if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && backClawState == ClawStates.CLOSED) {
                backClaw.setPosition(0.37);
                backClawState = ClawStates.OPEN;
                //sensorOverride = true;
            }

            if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT) && frontClawState == ClawStates.CLOSED && backClawState == ClawStates.CLOSED) {
                frontClaw.setPosition(0.55);
                frontClawState = ClawStates.OPEN;
                backClawState = ClawStates.OPEN;
                backClaw.setPosition(0.37);
            } else if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)  && frontClawState == ClawStates.OPEN && backClawState == ClawStates.OPEN) {
                frontClaw.setPosition(0.35);
                frontClawState = ClawStates.CLOSED;
                backClawState = ClawStates.CLOSED;
                backClaw.setPosition(0.52);
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
                    break;
                case RETRACT:
                    if (armTimer.seconds() > 1) {
                        lift.setTargetPosition(LiftPositions.GROUND.getValue());
                        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        //who did this??? (probably zach)
                        //telemetry.addLine("yo hi visu I'm in your code :D");
                        //telemetry.update();
                        lift.setPower(1);
                    }


                    arm.setPosition(0);
                    joint.setPosition(0.45);
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

            //multiple drive states to emulate which side of the robot is perceived as the front of the robot




            //intake stuff, its so simple that we just use if statements
            if (gamepad1.left_trigger > 0.1) {
                intake.setPower(-gamepad1.left_trigger); //spit out
            } else if (gamepad1.right_trigger > 0.1) {
                intake.setPower(gamepad1.right_trigger); //spit in
            } else {
                intake.setPower(0);
            }

            //airplane thrower (insert 9/11 joke)
            if (gamepad2.dpad_right) {
                airplane.setPosition(1); //reset
            }

            telemetry.addData("lift", lift.getCurrentPosition());
            telemetry.addData("lift state", liftState);
            telemetry.addData("drive state", currentDriveState);
            telemetry.update();

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x * ((currentDriveState == DriveStates.BACK) ? -1 : 1);

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double flPower = (y + x + rx) / denominator / ((gamepad1.left_bumper) ? 3.0 : 1);
            double frPower = (y - x - rx) / denominator / ((gamepad1.left_bumper) ? 3.0 : 1);
            double blPower = (y - x + rx) / denominator / ((gamepad1.left_bumper) ? 3.0 : 1);
            double brPower = (y + x - rx) / denominator / ((gamepad1.left_bumper) ? 3.0 : 1);

            switch (currentDriveState) {
                case FRONT: default:
                    fl.setPower(-flPower);
                    fr.setPower(-frPower);
                    bl.setPower(-blPower);
                    br.setPower(-brPower);
                    break;
                case BACK:
                    fl.setPower(flPower);
                    fr.setPower(frPower);
                    bl.setPower(blPower);
                    br.setPower(brPower);
                    break;
            }

            if (gamepad1.b && currentDriveState == DriveStates.FRONT) {
                currentDriveState = DriveStates.BACK;
            } else if (gamepad1.a && currentDriveState == DriveStates.BACK) {
                currentDriveState = DriveStates.FRONT;
            }
        }
    }
}

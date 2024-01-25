package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;

@Config
@Photon
@TeleOp (name="this is the teleop", group = "FINAL")
public class FinalOpMode extends LinearOpMode {
    public boolean toggle = false;
    public DcMotorEx fl, fr, bl, br;
    DcMotorEx lift, intake; //intake motor is called motor in the hardwaremap dont question it

    Servo arm;
    Servo joint;
    Servo frontClaw, backClaw;
    Servo airplane;

    GamepadEx gamepad;

    ColorSensor backSensor; //frontSensor;

    public ElapsedTime backTimer, frontTimer, liftTimer, armTimer;

    public static double jointTarget = 0, armTarget = 0;

    public static int liftTarget;

    public static long currentFront, currentBack;

    public static long previousFront = 0, previousBack = 0;

    public static boolean sensorOverride = false;

    private enum LiftStates {
        WAITING,
        EXTENDING,
        DEPOSIT,
        RETRACT,
        MANUAL;
    }

    public LiftStates liftState = LiftStates.WAITING;

    public enum LiftPositions {
        GROUND (0),
        SETLINE_1 (900),
        SETLINE_2 (2000),
        HANG (1125);

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

    public enum FrontClawStates {
        OPEN (0.55),
        CLOSE (0.35);

        private final double val;

        FrontClawStates(double val) {
            this.val = val;
        }

        public double getValue() {
            return val;
        }
    }

    public enum BackClawStates {
        OPEN (0.37),
        CLOSE (0.52);

        private final double val;

        BackClawStates(double val) {
            this.val = val;
        }

        public double getValue() {
            return val;
        }
    }

    public FrontClawStates frontClawState = FrontClawStates.OPEN;
    public BackClawStates backClawState = BackClawStates.OPEN;

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
        //frontSensor = hardwareMap.get(ColorSensor.class, "front");
        backSensor.enableLed(true);
        //frontSensor.enableLed(true);

        intake = hardwareMap.get(DcMotorEx.class, "motor");


        arm = hardwareMap.get(Servo.class, "arm"); //we control both with one thing now yay
        armTimer = new ElapsedTime();
        liftTimer = new ElapsedTime();

        joint = hardwareMap.get(Servo.class, "joint");
        frontClaw = hardwareMap.get(Servo.class, "claw1");
        backClaw = hardwareMap.get(Servo.class, "claw2");
        airplane = hardwareMap.get(Servo.class, "airplane");

        arm.setPosition(0);
        joint.setPosition(0.48);
        frontClaw.setPosition(0.55);
        backClaw.setPosition(0.37);
        airplane.setPosition(0);


        backTimer = new ElapsedTime();
        frontTimer = new ElapsedTime();
        telemetry.addLine("Optimized with Photon, press play to start...");
        telemetry.update();

        for (LynxModule l: hardwareMap.getAll(LynxModule.class)) {
            l.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        waitForStart();

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (opModeIsActive() && !isStopRequested()) {
            gamepad.readButtons();

            controlLift();
            controlClaws();
            controlDrivetrain();
            controlIntake();
            controlDrone();

            telemetry.update();
        }


    }

    public void controlLift() {
        if (gamepad2.a && liftState != LiftStates.WAITING) {
            liftState = LiftStates.RETRACT;
            armTimer.reset();
        } else if (gamepad2.b) {
            liftState = LiftStates.EXTENDING;
            liftTarget = LiftPositions.SETLINE_1.getValue();
            jointTarget = 0.67;
            armTarget = 0.5;
        } else if (gamepad2.x) {
            liftState = LiftStates.EXTENDING;
            liftTarget = LiftPositions.SETLINE_2.getValue();
            jointTarget = 0.67;
            armTarget = 0.5;
        } else if (gamepad2.y) {
            liftState = LiftStates.EXTENDING;
            liftTarget = LiftPositions.HANG.getValue();
            jointTarget = 0.89;
            armTarget = 0.7;
        } else if (gamepad2.dpad_up || gamepad2.dpad_down) {
            liftState = LiftStates.MANUAL;
            lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        //Finite state machine for the lift, which changes the code that runs for each of the
        //states that our lift can use in
        switch (liftState) {
            case WAITING:
                break;
            case EXTENDING:
                lift.setTargetPosition(liftTarget * 3);
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
            //we dont actually need a deposit case i just like having control
            case DEPOSIT:
                break;
            case RETRACT:
                sensorOverride = false;
                if (armTimer.seconds() > 0.5) {
                    lift.setTargetPosition(LiftPositions.GROUND.getValue());
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //who did this??? (probably zach)
                    //telemetry.addLine("yo hi visu I'm in your code :D");
                    //telemetry.update();
                    lift.setPower(1);
                }

                if (lift.getCurrentPosition() < 5) {
                    liftState = LiftStates.WAITING;
                }


                arm.setPosition(0);
                joint.setPosition(0.48);
                //sensorOverride = false;
                break;
            case MANUAL:
                lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                //i dont think this actually works
                //it does actually
                if (gamepad2.dpad_up) {
                    lift.setPower(0.5);
                } else if (gamepad2.dpad_down) {
                    lift.setPower(-0.5);
                } else {
                    lift.setPower(0);
                }
                break;
        }
        telemetry.addData("Current Lift State", liftState);
        telemetry.addData("Current Lift Encoder Position", lift.getCurrentPosition());

    }

    //please dont do this to yourself why did i even say we should have sensors
    //also this logic took me like 30 minutes to figure out
    public void controlClaws() {
        //currentFront = frontSensor.argb();
//        currentBack = backSensor.argb();
//
//        //setting timers & closing if the timers hit 3 seconds
////        if (Math.abs(currentFront - previousFront) > 100000000 && liftState == LiftStates.WAITING && !sensorOverride) {
////             frontTimer.reset();
////        }
//        if (Math.abs(currentBack - previousBack) > 100000000 && liftState == LiftStates.WAITING && !sensorOverride) {
//            backTimer.reset();
//        }

//        if (frontTimer.seconds() > 3 && currentFront != 0) {
//            frontClawState = FrontClawStates.CLOSE;
//            frontClaw.setPosition(frontClawState.getValue());
//        }
//            if (backTimer.seconds() > 3 && currentBack != 0) {
//            backClawState = BackClawStates.CLOSE;
//            backClaw.setPosition(backClawState.getValue());
//        }

        //overrides for the claw, they get reset everytime the claw comes down
        if (gamepad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && frontClawState == FrontClawStates.OPEN) {
            frontClawState = FrontClawStates.CLOSE;
            frontClaw.setPosition(frontClawState.getValue());
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && frontClawState == FrontClawStates.CLOSE) {
            frontClawState = FrontClawStates.OPEN;
            frontClaw.setPosition(frontClawState.getValue());
        }

        if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && backClawState == BackClawStates.OPEN) {
            backClawState = BackClawStates.CLOSE;
            backClaw.setPosition(backClawState.getValue());
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER) && backClawState == BackClawStates.CLOSE) {
            backClawState = BackClawStates.OPEN;
            backClaw.setPosition(backClawState.getValue());
        }

        //toggle true = close, false = open
        if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT) && !toggle) {
            frontClawState = FrontClawStates.CLOSE;
            frontClaw.setPosition(frontClawState.getValue());
            backClawState = BackClawStates.CLOSE;
            backClaw.setPosition(backClawState.getValue());
            toggle = !toggle;
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.DPAD_LEFT) && toggle) {
            frontClawState = FrontClawStates.OPEN;
            frontClaw.setPosition(frontClawState.getValue());
            backClawState = BackClawStates.OPEN;
            backClaw.setPosition(backClawState.getValue());
            toggle = !toggle;
        }

        //previousBack = currentBack;
        //previousFront = currentFront;

        telemetry.addData("Front Sensor Reading", currentFront);
        telemetry.addData("Back Sensor Reading", currentBack);
        telemetry.addData("Front Claw State", frontClawState);
        telemetry.addData("Back Claw State", backClawState);
    }

    public void controlDrivetrain() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_x * ((currentDriveState == DriveStates.BACK) ? -1 : 1);

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double flPower = (y + x + rx) / denominator / ((gamepad1.left_bumper) ? 3.0 : 1);
        double frPower = (y - x - rx) / denominator / ((gamepad1.left_bumper) ? 3.0 : 1);
        double blPower = (y - x + rx) / denominator / ((gamepad1.left_bumper) ? 3.0 : 1);
        double brPower = (y + x - rx) / denominator / ((gamepad1.left_bumper) ? 3.0 : 1);

        //the back emulates driving as if the back was actually the front
        //new robocavs programmers this is a game changer you should definitely use this
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



        //changes state for that fsm
        if (gamepad1.b && currentDriveState == DriveStates.FRONT) {
            currentDriveState = DriveStates.BACK;
        } else if (gamepad1.a && currentDriveState == DriveStates.BACK) {
            currentDriveState = DriveStates.FRONT;
        }

        telemetry.addData("Current Drive State", currentDriveState);
    }

    public void controlIntake() {
        if (gamepad1.left_trigger > 0.1) {
            intake.setPower(-gamepad1.left_trigger / 0.85); //spit out
        } else if (gamepad1.right_trigger > 0.1) {
            intake.setPower(gamepad1.right_trigger / 0.85); //intake
        } else {
            intake.setPower(0);
        }
    }

    public void controlDrone() {
        if (gamepad2.dpad_right) {
            airplane.setPosition(0.3); //reset
        }
    }
}

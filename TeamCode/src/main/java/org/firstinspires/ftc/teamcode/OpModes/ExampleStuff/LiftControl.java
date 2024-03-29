package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.controller.PIDController;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@Config
@TeleOp (name= "lift control")
public class LiftControl extends LinearOpMode {
    public DcMotorEx lift;
    public DcMotorEx motor;


    public PIDController liftController;
    public final double f = 0;

    public int target;
    public final double ticks_per_degree = 384.5;
    SampleMecanumDrive drive;



    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorEx.class, "motor");
        //tune this
        liftController = new PIDController(0,0,0);
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //HardwareConstants.currentLiftPosition = HardwareConstants.LiftPositions.GROUND;
        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.WAITING;

        drive = new SampleMecanumDrive(hardwareMap);

//        PhotonCore.experimental.setMaximumParallelCommands(6);
//        PhotonCore.start(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

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
                target = HardwareConstants.LiftPositions.SETLINE_3.getValue();
            }

            switch (HardwareConstants.currentLiftState) {
                case WAITING:
                    break;
                case EXTEND:
                    if (lift.getCurrentPosition() > HardwareConstants.threshold) {
                        //make code to move the arm
                    }
                    lift.setPower((liftController.calculate(lift.getCurrentPosition(), target)) + Math.cos(Math.toRadians(target / ticks_per_degree)) * f);
                    if (!lift.isBusy()) {
                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.DEPOSIT;
                    }
                    break;
                case DEPOSIT:
                    if (gamepad1.left_bumper) {
                        //code to open the upper claw
                        HardwareConstants.currentLeftClawState = HardwareConstants.ClawStates.OPEN;
                    } else if (gamepad1.right_bumper) {
                        HardwareConstants.currentRightClawState = HardwareConstants.ClawStates.OPEN;
                    } else if (gamepad1.dpad_left) {
                        HardwareConstants.currentLeftClawState = HardwareConstants.ClawStates.OPEN;
                        HardwareConstants.currentRightClawState = HardwareConstants.ClawStates.OPEN;
                    }

                    if (HardwareConstants.currentRightClawState == HardwareConstants.ClawStates.OPEN && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.OPEN) {
                        sleep(200); //wait a little bit so that the
                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.RETRACT;
                    }

                    break;
                case RETRACT:
                    if (lift.getCurrentPosition() < HardwareConstants.threshold) {
                        //code to bring the stuff back
                    }
                    lift.setPower((liftController.calculate(lift.getCurrentPosition(), HardwareConstants.LiftPositions.GROUND.getValue())) + Math.cos(Math.toRadians(target / ticks_per_degree)) * f);
                    if (!lift.isBusy() && lift.getCurrentPosition() == 0) {
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
                motor.setPower(-gamepad1.left_trigger);
            } else if (gamepad1.right_trigger > 0.1) {
                motor.setPower(gamepad1.right_trigger);
            } else {
                motor.setPower(0);
            }

            telemetry.addData("Lift Position", HardwareConstants.currentLiftPosition);
            telemetry.addData("Lift State", HardwareConstants.currentLiftState);
            telemetry.update();

        }
    }
}

package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants.*;
import org.firstinspires.ftc.teamcode.Utilities.PIDFController;

public class Lift {
    private final DcMotorEx lift;

    LinearOpMode opMode;

    PIDFController liftController;


    public Lift(LinearOpMode opMode) {
        this.opMode = opMode;
        lift = opMode.hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setLiftParameters(DcMotor.RunMode.RUN_WITHOUT_ENCODER, 0);
        //TODO Tune these values for the lift PID
        liftController = new PIDFController(lift, new PIDFCoefficients(0,0,0,0));
    }

    public void controlLift() {
        switch (HardwareConstants.currentLiftState) {
            case GROUND:
                if (opMode.gamepad2.b) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_1.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_1;
                } else if (opMode.gamepad2.x) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_2.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_2;
                } else if (opMode.gamepad2.y) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_3.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_3;
                } else {
                    setLiftParameters();
                }

                if (opMode.gamepad2.dpad_up || opMode.gamepad2.dpad_down) {
                    setClawState(ClawStates.CLOSED);
                    HardwareConstants.currentLiftState = LiftStates.MANUAL;
                }
            case SETLINE_1:
                if (opMode.gamepad2.a) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.GROUND.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.GROUND;
                } else if (opMode.gamepad2.x) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_2.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_2;
                } else if (opMode.gamepad2.y) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_3.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_3;
                } else {
                    setLiftParameters();
                }
            case SETLINE_2:
                if (opMode.gamepad2.a) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.GROUND.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.GROUND;
                } else if (opMode.gamepad2.b) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_1.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_1;
                } else if (opMode.gamepad2.y) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_3.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_3;
                } else {
                    setLiftParameters();
                }
            case SETLINE_3:
                if (opMode.gamepad2.a) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.GROUND.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.GROUND;
                } else if (opMode.gamepad2.b) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_1.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_1;
                } else if (opMode.gamepad2.x) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_2.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_2;
                } else {
                    setLiftParameters();
                }
            case MANUAL:
                if (opMode.gamepad2.dpad_up) {
                    setLiftParameters(DcMotor.RunMode.RUN_WITHOUT_ENCODER, HardwareConstants.slowLiftSpeed);
                } else if (opMode.gamepad2.dpad_down) {
                    setLiftParameters(DcMotor.RunMode.RUN_WITHOUT_ENCODER, -HardwareConstants.slowLiftSpeed);
                }

                if (opMode.gamepad2.a) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.GROUND.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.GROUND;
                } else if (opMode.gamepad2.b) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_1.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_1;
                } else if (opMode.gamepad2.x) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_2.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_2;
                } else if (opMode.gamepad2.y) {
                    setClawState(ClawStates.CLOSED);
                    setLiftParameters(LiftStates.SETLINE_3.getValue(), DcMotor.RunMode.RUN_TO_POSITION);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_3;
                } else {
                    setLiftParameters();
                }
        }
    }

    public void setLiftParameters(int position, DcMotor.RunMode runmode) {
        lift.setTargetPosition(position);
        lift.setMode(runmode);
        lift.setPower(liftController.calculate(position));
    }

    public void setLiftParameters(DcMotor.RunMode runmode, double power) {
        lift.setMode(runmode);
        lift.setPower(power);
    }

    public void setLiftParameters() {
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setPower(HardwareConstants.noLiftSpeed);
    }

    public void setClawState(ClawStates state) {
        HardwareConstants.currentLeftClawState = state;
        HardwareConstants.currentRightClawState = state;
    }
}

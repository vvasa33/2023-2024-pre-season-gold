package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants.*;
import org.firstinspires.ftc.teamcode.Utilities.PIDFController;

public class Lift {
    private final DcMotorEx lift;

    LinearOpMode opMode;

    PIDFController liftController;

    public ElapsedTime clawTimer;

    public Lift(LinearOpMode opMode) {
        this.opMode = opMode;
        lift = opMode.hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setLiftParameters(DcMotor.RunMode.RUN_TO_POSITION, 0);
        //TODO Tune these values for the lift PID
        liftController = new PIDFController(lift, new PIDFCoefficients(0,0,0,0));
        clawTimer = new ElapsedTime();
    }

    public void controlLift() {
        opMode.telemetry.addData("Motor Ticks: ", lift.getCurrentPosition());
        opMode.telemetry.update();

        switch (HardwareConstants.currentLiftState) {
            case WAITING:
                if (opMode.gamepad2.b) {
                    HardwareConstants.currentLiftPosition = LiftPositions.SETLINE_1;
                    lift.setTargetPosition(LiftPositions.SETLINE_1.getValue());
                    HardwareConstants.currentLiftState = LiftStates.EXTEND;
                    lift.setPower(HardwareConstants.liftSpeed);
                } else if (opMode.gamepad2.x) {
                    HardwareConstants.currentLiftPosition = LiftPositions.SETLINE_2;
                    lift.setTargetPosition(LiftPositions.SETLINE_2.getValue());
                    HardwareConstants.currentLiftState = LiftStates.EXTEND;
                    lift.setPower(HardwareConstants.liftSpeed);
                } else if (opMode.gamepad2.y) {
                    HardwareConstants.currentLiftPosition = LiftPositions.SETLINE_3;
                    lift.setTargetPosition(LiftPositions.SETLINE_3.getValue());
                    HardwareConstants.currentLiftState = LiftStates.EXTEND;
                    lift.setPower(HardwareConstants.liftSpeed);
                }
                break;
            case EXTEND:
                if (Math.abs(lift.getCurrentPosition() - HardwareConstants.currentLiftPosition.getValue()) < 10) {
                    //Set the claw to face the board
                    HardwareConstants.currentLiftState = LiftStates.DEPOSIT;
                }
            case DEPOSIT:
                //code to deposit (need to write the code for the claw first though)
                Claw.setClawState(ClawStates.OPEN);
                //TODO fix the arm + claw code so that it's actually depositing instead of just opening the claw lol
                if (opMode.gamepad2.dpad_left) {
                    clawTimer.reset();
                    Claw.setClawState(ClawStates.OPEN); //this opens the claw
                    if (clawTimer.seconds() >= 1) {
                        Claw.setClawState(ClawStates.CLOSED); //bring the claw back to its normal position
                    }


                    lift.setTargetPosition(LiftPositions.GROUND.getValue());
                    lift.setPower(HardwareConstants.liftSpeed);
                    HardwareConstants.currentLiftState = LiftStates.RETRACT;
                    HardwareConstants.currentLiftPosition = LiftPositions.GROUND;
                }

            case RETRACT:
                if (Math.abs(lift.getCurrentPosition() - HardwareConstants.currentLiftPosition.getValue()) < 10) {
                    HardwareConstants.currentLiftState = LiftStates.WAITING;
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

    public void setLiftState(LiftPositions state) {
        HardwareConstants.currentLiftPosition = state;
    }
}

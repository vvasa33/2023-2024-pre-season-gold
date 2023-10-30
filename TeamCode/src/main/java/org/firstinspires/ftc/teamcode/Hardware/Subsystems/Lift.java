package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants.*;
import org.firstinspires.ftc.teamcode.Utilities.PIDFController;

public class Lift {
    private final DcMotorEx lift;

    LinearOpMode opMode;

    PIDFController controller;



    public Lift(LinearOpMode opMode) {
        this.opMode = opMode;
        lift = opMode.hardwareMap.get(DcMotorEx.class, "lift");
    }

    public void controlLift() {
        switch (HardwareConstants.currentLiftState) {
            case GROUND:
                if (opMode.gamepad2.b) {
                    setLiftParameters(LiftStates.SETLINE_1.getValue(), DcMotor.RunMode.RUN_TO_POSITION, HardwareConstants.liftSpeed);
                    HardwareConstants.currentLiftState = LiftStates.SETLINE_1;
                }
        }
    }

    public void setLiftParameters(int position, DcMotor.RunMode runmode, double power) {
        lift.setTargetPosition(position);
        lift.setMode(runmode);
        lift.setPower(power);
    }
}

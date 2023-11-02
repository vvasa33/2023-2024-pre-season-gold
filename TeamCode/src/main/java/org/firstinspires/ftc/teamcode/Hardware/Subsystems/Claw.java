package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants.*;

public class Claw {
    private final Servo claw1, claw2;
    private final LinearOpMode opMode;

    public Claw(LinearOpMode opMode) {
        claw1 = opMode.hardwareMap.get(Servo.class, "claw1");
        claw2 = opMode.hardwareMap.get(Servo.class, "claw2");
        this.opMode = opMode;
    }

    //TODO change this so that trigger checks are outside of the state loop and changes in state will cause it to change

    public void controlClaw() {
        switch (HardwareConstants.currentLeftClawState) {

        }

        if (opMode.gamepad2.dpad_left) {
           setClawState(ClawStates.OPEN);
        }

        if (opMode.gamepad2.dpad_right) {
            setClawState(ClawStates.CLOSED);
        }

        if (opMode.gamepad2.left_trigger > 0.5) {
            HardwareConstants.currentLeftClawState = ((HardwareConstants.currentLeftClawState == ClawStates.CLOSED) ? ClawStates.OPEN : ClawStates.CLOSED);
        }

        if (opMode.gamepad2.left_trigger > 0.5) {
            HardwareConstants.currentRightClawState = ((HardwareConstants.currentRightClawState == ClawStates.CLOSED) ? ClawStates.OPEN : ClawStates.CLOSED);
        }
    }

    public static void setClawState(ClawStates state) {
        HardwareConstants.currentLeftClawState = state;
        HardwareConstants.currentRightClawState = state;
    }

}

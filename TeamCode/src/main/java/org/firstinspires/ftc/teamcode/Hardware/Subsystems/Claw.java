package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants.*;

public class Claw {
    private final ServoImplEx claw1, claw2;
    private final LinearOpMode opMode;

    public Claw(LinearOpMode opMode) {
        claw1 = opMode.hardwareMap.get(ServoImplEx.class, "claw1");
        claw2 = opMode.hardwareMap.get(ServoImplEx.class, "claw2");
        this.opMode = opMode;
    }

    public void controlClaw() {
        switch (HardwareConstants.currentLeftClawState) {
            case OPEN:
                if (opMode.gamepad2.left_trigger > 0.5) {
                    claw1.setPosition(ClawStates.CLOSED.getValue());
                    HardwareConstants.currentLeftClawState = ClawStates.CLOSED;
                }
            case CLOSED:
                if (opMode.gamepad2.left_trigger > 0.5) {
                claw1.setPosition(ClawStates.OPEN.getValue());
                HardwareConstants.currentLeftClawState = ClawStates.OPEN;
                }
        }

        switch (HardwareConstants.currentLeftClawState) {
            case OPEN:
                if (opMode.gamepad2.right_trigger > 0.5) {
                    claw2.setPosition(ClawStates.CLOSED.getValue());
                    HardwareConstants.currentRightClawState = ClawStates.CLOSED;
                }
            case CLOSED:
                if (opMode.gamepad2.right_trigger > 0.5) {
                    claw2.setPosition(ClawStates.OPEN.getValue());
                    HardwareConstants.currentRightClawState = ClawStates.OPEN;
                }
        }

        if (opMode.gamepad2.dpad_left) {
           HardwareConstants.currentLeftClawState = ClawStates.OPEN;
           HardwareConstants.currentRightClawState = ClawStates.OPEN;
        }

        if (opMode.gamepad2.dpad_right) {
            HardwareConstants.currentLeftClawState = ClawStates.CLOSED;
            HardwareConstants.currentRightClawState = ClawStates.CLOSED;
        }
    }
}

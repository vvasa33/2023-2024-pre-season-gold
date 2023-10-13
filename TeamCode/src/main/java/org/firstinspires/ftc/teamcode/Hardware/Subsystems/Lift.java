package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Utilities.PIDFController;

public class Lift {
    private final DcMotorEx lift;

    LinearOpMode opMode;

    PIDFController controller;

    public Lift(LinearOpMode opMode) {
        this.opMode = opMode;
        lift = opMode.hardwareMap.get(DcMotorEx.class, "lift");
    }

    public void moveLift() {

    }
}

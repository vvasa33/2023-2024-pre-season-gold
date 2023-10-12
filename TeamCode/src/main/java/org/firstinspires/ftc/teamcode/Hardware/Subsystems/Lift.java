package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    private DcMotorEx lift;

    LinearOpMode opMode;

    public Lift(LinearOpMode opMode) {
        this.opMode = opMode;
        lift = opMode.hardwareMap.get(DcMotorEx.class, "lift");
    }

    public void moveLift() {

    }
}

package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class BasicHardware {

    public static final String name = "Base";

    protected LinearOpMode opMode;
    public ElapsedTime timer;

    public abstract void init();
}

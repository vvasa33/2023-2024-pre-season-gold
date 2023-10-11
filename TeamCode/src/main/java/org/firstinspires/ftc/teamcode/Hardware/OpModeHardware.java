package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Subsystems.Drivetrain;

public class OpModeHardware extends BasicHardware{

    public Drivetrain drive;
    //TODO add accessories and preset enums


    public OpModeHardware(LinearOpMode opMode) {
        this.opMode = opMode;
        this.timer = new ElapsedTime();
    }

    @Override
    public void init() {
        drive = new Drivetrain(opMode);
        //TODO lift and claw inits

        opMode.telemetry.addData("status: ", "initalized");
        opMode.telemetry.update();
    }
}

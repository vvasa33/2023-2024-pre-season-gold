package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Hardware.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class OpModeHardware extends BasicHardware{

    public Drivetrain drive;
    public Lift lift;
    public Claw claw;



    public OpModeHardware(LinearOpMode opMode) {
        this.opMode = opMode;
        this.timer = new ElapsedTime();
    }

    @Override
    public void init() {
        drive = new Drivetrain(opMode.hardwareMap, opMode);
        //TODO lift and claw inits
        lift = new Lift(opMode);
        claw = new Claw(opMode);

        opMode.telemetry.addData("status: ", "initialized");
        opMode.telemetry.update();
    }
}

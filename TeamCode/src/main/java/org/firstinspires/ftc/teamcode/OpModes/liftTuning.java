package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.OpModeHardware;

public class liftTuning extends LinearOpMode {

    OpModeHardware robot = new OpModeHardware(this);
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();

        telemetry.addData(">", "Press play to watch this crash");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //robot.lift.controlLift();
        }
    }
}
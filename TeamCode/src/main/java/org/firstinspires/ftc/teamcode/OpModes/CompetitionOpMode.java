package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.OpModeHardware;

@TeleOp (name=CompetitionOpMode.name, group= HardwareConstants.opModeGroup)
public class CompetitionOpMode extends LinearOpMode {
    public static final String name = "Competition Op Modes";

    private final OpModeHardware robot = new OpModeHardware(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();

        telemetry.addData(">", "Running opmode...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            robot.drive.moveRobotFieldCentric();
        }
    }
}

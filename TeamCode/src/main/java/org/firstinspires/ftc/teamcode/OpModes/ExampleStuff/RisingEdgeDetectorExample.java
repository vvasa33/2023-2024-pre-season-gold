package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
@TeleOp (name="RisingEdgeDetectorExample")
public class RisingEdgeDetectorExample extends LinearOpMode {

    @Override
    public void runOpMode() throws  InterruptedException {
        GamepadEx gamepad = new GamepadEx(gamepad1);
        ButtonReader reader = new ButtonReader(gamepad, GamepadKeys.Button.A);

        waitForStart();

        while (opModeIsActive()) {
            gamepad.readButtons();
            if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                telemetry.addLine("Hi");
                telemetry.update();
            }
        }
    }
}

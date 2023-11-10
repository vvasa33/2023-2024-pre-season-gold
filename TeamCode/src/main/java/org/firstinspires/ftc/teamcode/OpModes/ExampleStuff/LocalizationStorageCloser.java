package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;

@TeleOp (name="LocalizationStorage")
public class LocalizationStorageCloser extends LinearOpMode {
    public static Pose2d poseIntoTeleOp = new Pose2d();

    public static HardwareConstants.PassChoices firstPassToPixel = null; //
    public static HardwareConstants.StackChoices firstPixelStack = null; //
    public static HardwareConstants.PassChoices firstPassToScore = null; //
    public static HardwareConstants.ScoreChoices firstCycleScore = null;
    public static HardwareConstants.PassChoices secondPassToPixel = null;
    public static HardwareConstants.StackChoices secondPixelStack = null;
    public static HardwareConstants.PassChoices secondPassToScore = null;
    public static HardwareConstants.ScoreChoices secondCycleScore = null;
    public static HardwareConstants.ParkChoices parking = null;

    GamepadEx gamepad;

    boolean isCleared = true;
    @Override
    public void runOpMode() throws InterruptedException {
        gamepad = new GamepadEx(gamepad1);


        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            telemetry.clearAll();
            telemetry.addLine("You currently " + ((parking == null) ? "DO NOT" : "DO") + " have Trajectories planned.");
            telemetry.addLine();
            telemetry.addLine("Press A to clear trajectories!");

            telemetry.update();

            if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                telemetry.clearAll();
                telemetry.addLine("Are you sure you would like to clear trajectories? Press A to confirm. Press B to go back");
                telemetry.update();
                boolean confirm = false;

                while (!confirm) {
                    if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                        clearTrajectories();
                        telemetry.clearAll();
                        telemetry.addLine("Cleared Trajectories");
                        telemetry.update();
                        confirm = true;
                    } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                        break;
                    }
                }
            }
        }
    }

    public void clearTrajectories() {
        poseIntoTeleOp = new Pose2d();

        firstPassToPixel = null;
        firstPixelStack = null;
        firstPassToScore = null;
        firstCycleScore = null;
        secondPassToPixel = null;
        secondPixelStack = null;
        secondPassToScore = null;
        secondCycleScore = null;
        parking = null;
    }
}

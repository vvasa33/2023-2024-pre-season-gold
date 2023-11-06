package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name="LocalizationStorage")
public class LocalizationStorageCloser extends LinearOpMode {
    public static Pose2d poseIntoTeleOp = new Pose2d();

    public static Trajectory firstPassToPixel = null;
    public static Trajectory firstStackPickup = null;
    public static Trajectory firstPassToScore = null;
    public static Trajectory secondPassToPixel = null;
    public static Trajectory secondStackPickup = null;
    public static Trajectory secondPassToScore = null;
    public static Trajectory parkingSpot = null;

    GamepadEx gamepad;

    boolean isCleared = true;
    @Override
    public void runOpMode() throws InterruptedException {
        gamepad = new GamepadEx(gamepad1);


        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            telemetry.clearAll();
            telemetry.addLine("You currently " + ((isCleared) ? "DO NOT" : "DO") + " have Trajectories planned.");
            telemetry.addLine();
            telemetry.addLine("Press A to clear trajectories!");

            telemetry.update();

            if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                telemetry.clearAll();
                telemetry.addLine("Are you sure you would like to clear trajectories? Press A to confirm. Press B to go back");
                boolean confirm = false;

                while (!confirm) {
                    if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                        clearTrajectories();
                        telemetry.addLine("Cleared Trajectories");
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
        firstStackPickup = null;
        firstPassToScore = null;
        secondPassToPixel = null;
        secondStackPickup = null;
        secondPassToScore = null;
        parkingSpot = null;
    }
}

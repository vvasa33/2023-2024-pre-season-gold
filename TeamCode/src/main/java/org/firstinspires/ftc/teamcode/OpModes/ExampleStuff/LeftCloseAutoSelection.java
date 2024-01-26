package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp (name= "LeftCloseAutoSelection", group="Auto Selection")
public class LeftCloseAutoSelection extends LinearOpMode {
    SampleMecanumDrive drive;
    GamepadEx gamepad;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        gamepad = new GamepadEx(gamepad1);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            telemetry.addLine("Choose your Auto!");
            telemetry.addLine();
            telemetry.addData("> First choice", "Which truss should we go underneath during our FIRST PASS?");
            telemetry.addLine();
            telemetry.addData("X", "BACKSTAGE DOOR");
            telemetry.addData("B", "RIGHT TRUSS");
            telemetry.update();

            HardwareConstants.PassChoices firstResponse = null;

            while (firstResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    firstResponse = HardwareConstants.PassChoices.DOOR;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    firstResponse = HardwareConstants.PassChoices.TRUSS;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", firstResponse.getValue());
            telemetry.addData("> Second choice", "Which FIRST PIXEL STACK should we pick up from?");
            telemetry.addLine();
            telemetry.addData("X", "LEFT stack");
            telemetry.addData("A", "MIDDLE stack");
            telemetry.addData("B", "RIGHT stack");
            telemetry.update();
            HardwareConstants.StackChoices secondResponse = null;
            while (secondResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    secondResponse = HardwareConstants.StackChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    secondResponse = HardwareConstants.StackChoices.RIGHT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    secondResponse = HardwareConstants.StackChoices.LEFT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", "Go under the " + firstResponse.getValue());
            telemetry.addData("Your second choice", "Go to the " + secondResponse.getValue());
            telemetry.addLine();
            telemetry.addData("> Third choice", "Which truss should we go underneath during our FIRST PASS BACK?");
            telemetry.addLine();
            //telemetry.addData("X: ", "BACKSTAGE DOOR");
            telemetry.addData("X: ", "Backstage DOOR");
            telemetry.addData("B: ", "TRUSS");
            telemetry.update();

            HardwareConstants.PassChoices thirdResponse = null;
            Trajectory firstPassToScore = null;
            while (thirdResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    thirdResponse = HardwareConstants.PassChoices.DOOR;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    thirdResponse = HardwareConstants.PassChoices.TRUSS;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", "Go under the " + firstResponse.getValue());
            telemetry.addData("Your second choice", "Go to the " + secondResponse.getValue());
            telemetry.addData("Your third choice", "Go under the " + thirdResponse.getValue());
            telemetry.addLine();
            telemetry.addData("> Fourth choice", "Which part should we score on?");
            telemetry.addLine();
            telemetry.addData("X", "LEFT side");
            telemetry.addData("A", "MIDDLE side");
            telemetry.addData("B", "RIGHT side");
            telemetry.addData("Y", "Autoselect");
            telemetry.update();

            HardwareConstants.ScoreChoices fourthResponse = null;
            while (fourthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    fourthResponse = HardwareConstants.ScoreChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    fourthResponse = HardwareConstants.ScoreChoices.RIGHT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    fourthResponse = HardwareConstants.ScoreChoices.LEFT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.Y)) {
                    fourthResponse = HardwareConstants.ScoreChoices.AUTOSELECT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", "Go under the " + firstResponse.getValue());
            telemetry.addData("Your second choice", "Go to the " + secondResponse.getValue());
            telemetry.addData("Your third choice", "Go under the " + thirdResponse.getValue());
            telemetry.addData("Your fourth choice", "Score at the " + fourthResponse.getValue());
            telemetry.addLine();
            telemetry.addData("> Fifth choice", "Which truss should we go under for our SECOND PASS PICKUP");
            telemetry.addLine();
            telemetry.addData("X", "DOOR");
            telemetry.addData("B", "TRUSS");
            telemetry.update();

            HardwareConstants.PassChoices fifthResponse = null;
            while (fifthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    fifthResponse = HardwareConstants.PassChoices.TRUSS;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    fifthResponse = HardwareConstants.PassChoices.DOOR;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", "Go under the " + firstResponse.getValue());
            telemetry.addData("Your second choice", "Go to the " + secondResponse.getValue());
            telemetry.addData("Your third choice", "Go under the " + thirdResponse.getValue());
            telemetry.addData("Your fourth choice", "Score at the " + fourthResponse.getValue());
            telemetry.addData("Your fifth choice", "Go through the " + fifthResponse.getValue());
            telemetry.addLine();
            telemetry.addLine("> Sixth choice: Which pixel stack do you want to pick up for the SECOND PASS");
            telemetry.addData("X", "LEFT stack");
            telemetry.addData("A", "MIDDLE stack");
            telemetry.addData("B", "RIGHT stack");
            telemetry.update();
            HardwareConstants.StackChoices sixthResponse = null;
            while (sixthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    sixthResponse = HardwareConstants.StackChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    sixthResponse = HardwareConstants.StackChoices.RIGHT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    sixthResponse = HardwareConstants.StackChoices.LEFT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", "Go under the " + firstResponse.getValue());
            telemetry.addData("Your second choice", "Go to the " + secondResponse.getValue());
            telemetry.addData("Your third choice", "Go under the " + thirdResponse.getValue());
            telemetry.addData("Your fourth choice", "Score at the " + fourthResponse.getValue());
            telemetry.addData("Your fifth choice", "Go through the " + fifthResponse.getValue());
            telemetry.addData("Your sixth choice", "Go to the " + sixthResponse.getValue());
            telemetry.addLine();
            telemetry.addLine("> Seventh choice: What do you want to go underneath for the SECOND PASS BACK?");
            telemetry.addLine();
            telemetry.addData("X", "DOOR");
            telemetry.addData("B", "TRUSS");
            telemetry.update();

            HardwareConstants.PassChoices seventhResponse = null;
            while (seventhResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    seventhResponse = HardwareConstants.PassChoices.DOOR;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    seventhResponse = HardwareConstants.PassChoices.TRUSS;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", "Go under the " + firstResponse.getValue());
            telemetry.addData("Your second choice", "Go to the " + secondResponse.getValue());
            telemetry.addData("Your third choice", "Go under the " + thirdResponse.getValue());
            telemetry.addData("Your fourth choice", "Score at the " + fourthResponse.getValue());
            telemetry.addData("Your fifth choice", "Go through the " + fifthResponse.getValue());
            telemetry.addData("Your sixth choice", "Go to the " + sixthResponse.getValue());
            telemetry.addData("Your seventh choice", "Go under the " + seventhResponse.getValue());
            telemetry.addLine();
            telemetry.addLine("> Eighth choice: Where do you want to score on our SECOND PASS");
            telemetry.addLine();
            telemetry.addData("X", "LEFT side");
            telemetry.addData("A", "MIDDLE side");
            telemetry.addData("B", "RIGHT side");
            telemetry.addData("Y", "Autoselect");
            telemetry.update();
            HardwareConstants.ScoreChoices eighthResponse = null;
            while (eighthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    eighthResponse = HardwareConstants.ScoreChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    eighthResponse = HardwareConstants.ScoreChoices.RIGHT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    eighthResponse = HardwareConstants.ScoreChoices.LEFT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.Y)) {
                    eighthResponse = HardwareConstants.ScoreChoices.AUTOSELECT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", "Go under the " + firstResponse.getValue());
            telemetry.addData("Your second choice", "Go to the " + secondResponse.getValue());
            telemetry.addData("Your third choice", "Go under the " + thirdResponse.getValue());
            telemetry.addData("Your fourth choice", "Score at the " + fourthResponse.getValue());
            telemetry.addData("Your fifth choice", "Go through the " + fifthResponse.getValue());
            telemetry.addData("Your sixth choice", "Go to the " + sixthResponse.getValue());
            telemetry.addData("Your seventh choice", "Go under the " + seventhResponse.getValue());
            telemetry.addData("Your eighth choice", "Score at the " + eighthResponse.getValue());
            telemetry.addLine();
            telemetry.addLine("> Last choice: Where do you want to park");
            telemetry.addLine();
            telemetry.addData("X", "Left");
            telemetry.addData("B", "Right");
            telemetry.update();

            HardwareConstants.ParkChoices lastResponse = null;
            while (lastResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    lastResponse = HardwareConstants.ParkChoices.LEFT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    lastResponse = HardwareConstants.ParkChoices.RIGHT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("Your first choice", "Go under the " + firstResponse.getValue());
            telemetry.addData("Your second choice", "Go to the " + secondResponse.getValue());
            telemetry.addData("Your third choice", "Go under the " + thirdResponse.getValue());
            telemetry.addData("Your fourth choice", "Score at the " + fourthResponse.getValue());
            telemetry.addData("Your fifth choice", "Go through the " + fifthResponse.getValue());
            telemetry.addData("Your sixth choice", "Go to the " + sixthResponse.getValue());
            telemetry.addData("Your seventh choice", "Go under the " + seventhResponse.getValue());
            telemetry.addData("Your eighth choice", "Score at the " + eighthResponse.getValue());
            telemetry.addData("Your last choice", "Park at the " + lastResponse.getValue());

            telemetry.addLine();
            telemetry.addData("Is this all good? ", "Press A to confirm");
            telemetry.update();

            boolean confirm = false;

            while (!confirm) {
                gamepad.readButtons();
                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    confirm = true;
                }
            }


            LocalizationStorageCloser.firstPassToPixel = firstResponse;
            LocalizationStorageCloser.firstPixelStack = secondResponse;   
            LocalizationStorageCloser.firstPassToScore = thirdResponse;
            LocalizationStorageCloser.firstCycleScore = fourthResponse;
            LocalizationStorageCloser.secondPassToPixel = fifthResponse;
            LocalizationStorageCloser.secondPixelStack = sixthResponse;
            LocalizationStorageCloser.secondPassToScore = seventhResponse;
            LocalizationStorageCloser.secondCycleScore = eighthResponse;
            LocalizationStorageCloser.parking = lastResponse;

            telemetry.clearAll();
            telemetry.addLine("> The CLOSE Auto has been chosen. Ending the opmode...");
            wait(5);
            return;
        }
    }
}

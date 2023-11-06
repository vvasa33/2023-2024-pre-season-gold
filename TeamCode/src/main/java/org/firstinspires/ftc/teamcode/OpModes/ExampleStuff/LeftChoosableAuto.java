package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp (name="WorkingChoosableAuto")
public class LeftChoosableAuto extends LinearOpMode {
    SampleMecanumDrive drive;
    GamepadEx gamepad;



    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        gamepad = new GamepadEx(gamepad1);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            telemetry.addData("> Choose your own adventure! written by visu vasa", "press the button you want to choose where robot go");
            telemetry.addLine();
            telemetry.addData("> First choice", "Which truss should we go underneath during our FIRST PASS?");
            telemetry.addLine();
            //telemetry.addData("X", "BACKSTAGE DOOR");
            telemetry.addData("A", "MIDDLE TRUSS");
            telemetry.addData("B", "RIGHT TRUSS");
            telemetry.update();
            HardwareConstants.PassChoices firstResponse = null;
            Trajectory firstPassToPixel = null;
            while (firstResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    firstResponse = HardwareConstants.PassChoices.MIDDLE;
                    //TODO give each of the if statements their respective values
                    firstPassToPixel = drive.trajectoryBuilder(new Pose2d(0,0,0))
                            .lineToLinearHeading(new Pose2d(0,0,0))
                            .build();
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    firstResponse = HardwareConstants.PassChoices.RIGHT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("> Choose your own adventure! written by visu vasa: ", "press the button you want to choose where robot go");
            telemetry.addLine();
            telemetry.addData("Your first choice: ", firstResponse.getValue());
            telemetry.addData("> Second choice: ", "Which FIRST PIXEL STACK should we pick up from?");
            telemetry.addLine();
            telemetry.addData("X: ", "LEFT stack");
            telemetry.addData("A: ", "MIDDLE stack");
            telemetry.addData("B: ", "RIGHT stack");
            telemetry.update();
            HardwareConstants.StackChoices secondResponse = null;
            Trajectory firstStackPickup = null;
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
            telemetry.addData("> Choose your own adventure! written by visu vasa: ", "press the button you want to choose where robot go");
            telemetry.addLine();
            telemetry.addData("Your first choice: ", "Go under the " + firstResponse.getValue() + " FIRST.");
            telemetry.addData("Your second choice: ", "Go to the " + secondResponse.getValue() + " NEXT");
            telemetry.addLine();
            telemetry.addData("> Third choice: ", "Which truss should we go underneath during our FIRST PASS BACK?");
            telemetry.addLine();
            //telemetry.addData("X: ", "BACKSTAGE DOOR");
            telemetry.addData("A: ", "MIDDLE TRUSS");
            telemetry.addData("B: ", "RIGHT TRUSS");
            telemetry.update();

            HardwareConstants.PassChoices thirdResponse = null;
            Trajectory firstPassToScore = null;
            while (thirdResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    thirdResponse = HardwareConstants.PassChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    thirdResponse = HardwareConstants.PassChoices.RIGHT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("> Choose your own adventure! written by visu vasa: ", "press the button you want to choose where robot go");
            telemetry.addLine();
            telemetry.addData("Your first choice: ", "Go under the " + firstResponse.getValue() + " FIRST.");
            telemetry.addData("Your second choice: ", "Go to the " + secondResponse.getValue() + " NEXT");
            telemetry.addData("Your third choice: ", "Go under the " + thirdResponse.getValue() + " NEXT");
            telemetry.addLine();
            telemetry.addData("> Fourth choice: ", "Which truss should we go underneath during our SECOND PASS?");
            telemetry.addLine();
            //telemetry.addData("X: ", "BACKSTAGE DOOR");
            telemetry.addData("A: ", "MIDDLE TRUSS");
            telemetry.addData("B: ", "RIGHT TRUSS");
            telemetry.update();

            HardwareConstants.PassChoices fourthResponse = null;
            Trajectory secondPassToPixel = null;
            while (fourthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    fourthResponse = HardwareConstants.PassChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    fourthResponse = HardwareConstants.PassChoices.RIGHT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("> Choose your own adventure! written by visu vasa: ", "press the button you want to choose where robot go");
            telemetry.addLine();
            telemetry.addData("Your first choice: ", "Go under the " + firstResponse.getValue() + " FIRST.");
            telemetry.addData("Your second choice: ", "Go to the " + secondResponse.getValue() + " NEXT");
            telemetry.addData("Your third choice: ", "Go under the " + thirdResponse.getValue() + " NEXT");
            telemetry.addData("Your fourth choice: ", "Go under the " + fourthResponse.getValue() + " NEXT");
            telemetry.addLine();
            telemetry.addData("> Fourth choice: ", "Which SECOND PIXEL STACK should we pick up from?");
            telemetry.addLine();
            telemetry.addData("X: ", "LEFT STACK");
            telemetry.addData("A: ", "MIDDLE STACK");
            telemetry.addData("B: ", "RIGHT STACK");
            telemetry.update();

            HardwareConstants.StackChoices fifthResponse = null;
            Trajectory secondStackPickup = null;
            while (fifthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    fifthResponse = HardwareConstants.StackChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    fifthResponse = HardwareConstants.StackChoices.RIGHT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    fifthResponse = HardwareConstants.StackChoices.LEFT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("> Choose your own adventure! written by visu vasa: ", "press the button you want to choose where robot go");
            telemetry.addLine();
            telemetry.addData("Your first choice: ", "Go under the " + firstResponse.getValue() + " FIRST.");
            telemetry.addData("Your second choice: ", "Go to the " + secondResponse.getValue() + " NEXT");
            telemetry.addData("Your third choice: ", "Go under the " + thirdResponse.getValue() + " NEXT");
            telemetry.addData("Your fourth choice: ", "Go under the " + fourthResponse.getValue() + " NEXT");
            telemetry.addData("Your fifth choice: ", "Go to the " + fifthResponse.getValue() + " NEXT");
            telemetry.addLine();
            telemetry.addData("> Sixth choice: ", "Which truss should we go underneath for the SECOND PASS BACK?");
            telemetry.addLine();
            //telemetry.addData("X: ", "BACKSTAGE DOOR");
            telemetry.addData("A: ", "MIDDLE TRUSS");
            telemetry.addData("B: ", "RIGHT TRUSS");
            telemetry.update();

            HardwareConstants.PassChoices sixthResponse = null;
            Trajectory secondPassToScore = null;
            while (sixthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    sixthResponse = HardwareConstants.PassChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    sixthResponse = HardwareConstants.PassChoices.RIGHT;
                }
            }

            telemetry.clearAll();
            telemetry.addData("> Choose your own adventure! written by visu vasa: ", "press the button you want to choose where robot go");
            telemetry.addLine();
            telemetry.addData("Your first choice: ", "Go under the " + firstResponse.getValue() + " FIRST.");
            telemetry.addData("Your second choice: ", "Go to the " + secondResponse.getValue() + " NEXT");
            telemetry.addData("Your third choice: ", "Go under the " + thirdResponse.getValue() + " NEXT");
            telemetry.addData("Your fourth choice: ", "Go under the " + fourthResponse.getValue() + " NEXT");
            telemetry.addData("Your fifth choice: ", "Go to the " + fifthResponse.getValue() + " NEXT");
            telemetry.addData("Your sixth choice: ", "Go under the " + sixthResponse.getValue() + " NEXT");
            telemetry.addLine();
            telemetry.addData("> Last choice: ", "Where should we park");
            telemetry.addLine();
            telemetry.addData("X: ", "Left of the Backdrop");
            telemetry.addData("B: ", "Right of the Backdrop");
            telemetry.update();

            HardwareConstants.ParkChoices lastResponse = null;
            Trajectory parkingSpot = null;
            while (lastResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    lastResponse = HardwareConstants.ParkChoices.LEFT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    lastResponse = HardwareConstants.ParkChoices.RIGHT;
                }
            }

            telemetry.clearAll();
            telemetry.addData(">> Final choices: ", "");
            telemetry.addLine();
            telemetry.addData("Your first choice: ", "Go under the " + firstResponse.getValue() + " FIRST.");
            telemetry.addData("Your second choice: ", "Go to the " + secondResponse.getValue() + " NEXT");
            telemetry.addData("Your third choice: ", "Go under the " + thirdResponse.getValue() + " NEXT");
            telemetry.addData("Your fourth choice: ", "Go under the " + fourthResponse.getValue() + " NEXT");
            telemetry.addData("Your fifth choice: ", "Go to the " + fifthResponse.getValue() + " NEXT");
            telemetry.addData("Your sixth choice: ", "Go under the " + sixthResponse.getValue() + " NEXT");
            telemetry.addData("Your seventh choice: ", lastResponse.getValue());

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

            //This is where everything is saved
            LocalizationStorageCloser.firstPassToPixel = firstPassToPixel;




            telemetry.clearAll();
            telemetry.addLine("> Saved to file. Ready to go...");
            telemetry.update();

            sleep(5000);

            return;
        }
    }
}

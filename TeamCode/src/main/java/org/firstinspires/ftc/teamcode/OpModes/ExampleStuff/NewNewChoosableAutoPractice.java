package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp (name="WorkingChoosableAuto")
public class NewNewChoosableAutoPractice extends LinearOpMode {
    SampleMecanumDrive drive;
    GamepadEx gamepad;

    enum PassChoices {
        MIDDLE ("MIDDLE Truss"),
        RIGHT ("RIGHT Truss");

        public final String val;

        PassChoices (String val) {this.val = val;}

        public String getValue() {return val;}
    }

    enum StackChoices {
        LEFT ("LEFT Stack"),
        MIDDLE ("MIDDLE Stack"),
        RIGHT ("RIGHT Stack");

        public final String val;

        StackChoices (String val) {this.val = val;}

        public String getValue() {return val;}
    }

    enum ParkChoices {
        LEFT ("Left Park"),
        RIGHT ("Right Park");

        public final String val;

        ParkChoices (String val) {this.val = val;}

        public String getValue() {return val;}
    }

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
            PassChoices firstResponse = null;
            Trajectory firstPassToPixel;
            while (firstResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    firstResponse = PassChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    firstResponse = PassChoices.RIGHT;
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
            StackChoices secondResponse = null;
            Trajectory firstStackPickup;
            while (secondResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    secondResponse = StackChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    secondResponse = StackChoices.RIGHT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    secondResponse = StackChoices.LEFT;
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

            PassChoices thirdResponse = null;
            Trajectory firstPassToScore;
            while (thirdResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    thirdResponse = PassChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    thirdResponse = PassChoices.RIGHT;
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

            PassChoices fourthResponse = null;
            Trajectory secondPassToPixel;
            while (fourthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    fourthResponse = PassChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    fourthResponse = PassChoices.RIGHT;
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

            StackChoices fifthResponse = null;
            Trajectory secondStackPickup;
            while (fifthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    fifthResponse = StackChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    fifthResponse = StackChoices.RIGHT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    fifthResponse = StackChoices.LEFT;
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

            PassChoices sixthResponse = null;
            Trajectory secondPassToScore;
            while (sixthResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    sixthResponse = PassChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    sixthResponse = PassChoices.RIGHT;
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

            ParkChoices lastResponse = null;
            Trajectory parkingSpot;
            while (lastResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    lastResponse = ParkChoices.LEFT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    lastResponse = ParkChoices.RIGHT;
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

            telemetry.clearAll();
            telemetry.addLine("> Saved to file. Sending you to the auto selection...");
            telemetry.update();

            sleep(5);

            return;
        }
    }
}

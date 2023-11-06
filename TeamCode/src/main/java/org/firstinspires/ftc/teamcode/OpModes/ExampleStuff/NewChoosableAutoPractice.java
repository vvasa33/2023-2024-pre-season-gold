package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@TeleOp(name="newchoosableauto")
public class NewChoosableAutoPractice extends LinearOpMode {
    SampleMecanumDrive drive;
    GamepadEx gamepad;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        gamepad = new GamepadEx(gamepad1);


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("> Choose your own adventure! written by visu vasa: ", "press the button you want to choose where robot go");
            telemetry.addLine();
            telemetry.addData("> First choice: ", "Which truss should we go underneath during our FIRST PASS?");
            telemetry.addLine();
            telemetry.addData("X: ", "BACKSTAGE DOOR");
            telemetry.addData("A: ", "MIDDLE TRUSS");
            telemetry.addData("B: ", "RIGHT TRUSS");
            telemetry.update();
            ChoosableAutoPractice.PassChoices firstResponse = null;
            Trajectory firstPassToPixel;

            while (firstResponse == null) {
                gamepad.readButtons();

                if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    //generate our first trajectory towards the backstage door
                    //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                    firstResponse = ChoosableAutoPractice.PassChoices.DOOR;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    //generate our first traj towards the middle truss
                    firstResponse = ChoosableAutoPractice.PassChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    //generate our first traj towards the right most truss
                    firstResponse = ChoosableAutoPractice.PassChoices.RIGHT;
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
            ChoosableAutoPractice.StackChoices secondResponse = null;
            Trajectory firstStackPickup;

            while (secondResponse == null) {
                gamepad.readButtons();
                if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
                    //generate our first trajectory towards the backstage door
                    //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                    secondResponse = ChoosableAutoPractice.StackChoices.LEFT;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.A)) {
                    //generate our first traj towards the middle truss
                    secondResponse = ChoosableAutoPractice.StackChoices.MIDDLE;
                } else if (gamepad.wasJustPressed(GamepadKeys.Button.B)) {
                    //generate our first traj towards the right most truss
                    secondResponse = ChoosableAutoPractice.StackChoices.RIGHT;
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
            telemetry.addData("X: ", "BACKSTAGE DOOR");
            telemetry.addData("A: ", "MIDDLE TRUSS");
            telemetry.addData("B: ", "RIGHT TRUSS");
            telemetry.update();

            ChoosableAutoPractice.PassChoices thirdResponse = null;
            Trajectory firstPassToScore;
            while (thirdResponse == null) {
                if (gamepad1.x) {
                    //generate our first trajectory towards the backstage door
                    //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                    thirdResponse = ChoosableAutoPractice.PassChoices.DOOR;
                } else if (gamepad1.a) {
                    //generate our first traj towards the middle truss
                    thirdResponse = ChoosableAutoPractice.PassChoices.MIDDLE;
                } else if (gamepad1.b) {
                    //generate our first traj towards the right most truss
                    thirdResponse = ChoosableAutoPractice.PassChoices.RIGHT;
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
            telemetry.addData("X: ", "BACKSTAGE DOOR");
            telemetry.addData("A: ", "MIDDLE TRUSS");
            telemetry.addData("B: ", "RIGHT TRUSS");
            telemetry.update();

            ChoosableAutoPractice.PassChoices fourthResponse = null;
            Trajectory secondPassToPixel;
            while (fourthResponse == null) {
                if (gamepad1.x) {
                    //generate our first trajectory towards the backstage door
                    //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                    fourthResponse = ChoosableAutoPractice.PassChoices.DOOR;
                } else if (gamepad1.a) {
                    //generate our first traj towards the middle truss
                    fourthResponse = ChoosableAutoPractice.PassChoices.MIDDLE;
                } else if (gamepad1.b) {
                    //generate our first traj towards the right most truss
                    fourthResponse = ChoosableAutoPractice.PassChoices.RIGHT;
                }
            }
            sleep(1);
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

            ChoosableAutoPractice.StackChoices fifthResponse = null;
            Trajectory secondStackPickup;
            while (fifthResponse == null) {
                if (gamepad1.x) {
                    //generate our first trajectory towards the backstage door
                    //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                    fifthResponse = ChoosableAutoPractice.StackChoices.LEFT;
                } else if (gamepad1.a) {
                    //generate our first traj towards the middle truss
                    fifthResponse = ChoosableAutoPractice.StackChoices.MIDDLE;
                } else if (gamepad1.b) {
                    //generate our first traj towards the right most truss
                    fifthResponse = ChoosableAutoPractice.StackChoices.RIGHT;
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
            telemetry.addData("X: ", "BACKSTAGE DOOR");
            telemetry.addData("A: ", "MIDDLE TRUSS");
            telemetry.addData("B: ", "RIGHT TRUSS");
            telemetry.update();

            ChoosableAutoPractice.PassChoices sixthResponse = null;
            Trajectory secondPassToScore;
            while (sixthResponse == null) {
                if (gamepad1.x) {
                    //generate our first trajectory towards the backstage door
                    //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                    sixthResponse = ChoosableAutoPractice.PassChoices.DOOR;
                } else if (gamepad1.a) {
                    //generate our first traj towards the middle truss
                    sixthResponse = ChoosableAutoPractice.PassChoices.MIDDLE;
                } else if (gamepad1.b) {
                    //generate our first traj towards the right most truss
                    sixthResponse = ChoosableAutoPractice.PassChoices.RIGHT;
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

            ChoosableAutoPractice.ParkChoices lastResponse = null;
            Trajectory parkingSpot;
            while (lastResponse == null) {
                if (gamepad1.x) {
                    lastResponse = ChoosableAutoPractice.ParkChoices.LEFT;
                    //generate
                } else if (gamepad1.b) {
                    lastResponse = ChoosableAutoPractice.ParkChoices.RIGHT;
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
            telemetry.addData("Your seventh choice: ", "lastResponse.getValue()");

            telemetry.addLine();
            telemetry.addData("Is this all good? ", "Press A to confirm");
            telemetry.update();

            boolean confirm = false;

            while (!confirm) {
                if (gamepad1.a) {
                    confirm = true;
                }
            }


            telemetry.clearAll();
            telemetry.addLine("> Initialization sequence done. Press start to play...");
            telemetry.update();

            return;
        }




    }
}

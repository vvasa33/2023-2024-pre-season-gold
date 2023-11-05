package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Hardware.OpModeHardware;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import kotlin.OverloadResolutionByLambdaReturnType;

@Config
@Autonomous (name="ChoosableAutoPractice")
public class ChoosableAutoPractice extends LinearOpMode {

    OpModeHardware robot;

    enum PassChoices {
        DOOR ("Backstage DOOR"),
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
        //here is where we begin the init stage, where we can select our stuff
        //the issue: the telemetry.update() causes the entire program to stop and it doesnt get into the 

         robot = new OpModeHardware(this);

         telemetry.addData("> Choose your own adventure! written by visu vasa: ", "press the button you want to choose where robot go");
         telemetry.addLine();
         telemetry.addData("> First choice: ", "Which truss should we go underneath during our FIRST PASS?");
         telemetry.addLine();
         telemetry.addData("X: ", "BACKSTAGE DOOR");
         telemetry.addData("A: ", "MIDDLE TRUSS");
         telemetry.addData("B: ", "RIGHT TRUSS");
         telemetry.update();
         PassChoices firstResponse = null;
         Trajectory firstPassToPixel;
         while (firstResponse == null) {
             if (gamepad1.x) {
                 //generate our first trajectory towards the backstage door
                 //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                 firstResponse = PassChoices.DOOR;
             } else if (gamepad1.a) {
                 //generate our first traj towards the middle truss
                 firstResponse = PassChoices.MIDDLE;
             } else if (gamepad1.b) {
                 //generate our first traj towards the right most truss
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
            if (gamepad1.x) {
                //generate our first trajectory towards the backstage door
                //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                secondResponse = StackChoices.LEFT;
            } else if (gamepad1.a) {
                //generate our first traj towards the middle truss
                secondResponse = StackChoices.MIDDLE;
            } else if (gamepad1.b) {
                //generate our first traj towards the right most truss
                secondResponse = StackChoices.RIGHT;
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

        PassChoices thirdResponse = null;
        Trajectory firstPassToScore;
        while (thirdResponse == null) {
            if (gamepad1.x) {
                //generate our first trajectory towards the backstage door
                //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                thirdResponse = PassChoices.DOOR;
            } else if (gamepad1.a) {
                //generate our first traj towards the middle truss
                thirdResponse = PassChoices.MIDDLE;
            } else if (gamepad1.b) {
                //generate our first traj towards the right most truss
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
        telemetry.addData("X: ", "BACKSTAGE DOOR");
        telemetry.addData("A: ", "MIDDLE TRUSS");
        telemetry.addData("B: ", "RIGHT TRUSS");
        telemetry.update();

        PassChoices fourthResponse = null;
        Trajectory secondPassToPixel;
        while (fourthResponse == null) {
            if (gamepad1.x) {
                //generate our first trajectory towards the backstage door
                //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                fourthResponse = PassChoices.DOOR;
            } else if (gamepad1.a) {
                //generate our first traj towards the middle truss
                fourthResponse = PassChoices.MIDDLE;
            } else if (gamepad1.b) {
                //generate our first traj towards the right most truss
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
            if (gamepad1.x) {
                //generate our first trajectory towards the backstage door
                //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                fifthResponse = StackChoices.LEFT;
            } else if (gamepad1.a) {
                //generate our first traj towards the middle truss
                fifthResponse = StackChoices.MIDDLE;
            } else if (gamepad1.b) {
                //generate our first traj towards the right most truss
                fifthResponse = StackChoices.RIGHT;
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

        PassChoices sixthResponse = null;
        Trajectory secondPassToScore;
        while (sixthResponse == null) {
            if (gamepad1.x) {
                //generate our first trajectory towards the backstage door
                //firstPassToPixel = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate()) (same thing for everything else)
                sixthResponse = PassChoices.DOOR;
            } else if (gamepad1.a) {
                //generate our first traj towards the middle truss
                sixthResponse = PassChoices.MIDDLE;
            } else if (gamepad1.b) {
                //generate our first traj towards the right most truss
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
            if (gamepad1.x) {
                lastResponse = ParkChoices.LEFT;
                //generate
            } else if (gamepad1.b) {
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

        boolean confirmation = false;
        while (!confirmation) {
            if (gamepad1.a) {
                confirmation = true;
            }
        }

        telemetry.clearAll();
        telemetry.addLine("> Initialization sequence done. Press start to play...");

        waitForStart();

        while (opModeIsActive()) {
            //run code in here, all trajectories are chosen up there so we're good
        }
    }
}

package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
public class DrivingPractice extends LinearOpMode {
    SampleMecanumDrive drive;
    double change = 0;

    public enum DriveModes {
        ROBOT,
        FIELD
    }
    public DriveModes mode = DriveModes.ROBOT;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            switch (mode) {
                case ROBOT: default:
                    if (gamepad1.a) {
                        mode = DriveModes.FIELD;
                    }
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    -gamepad1.left_stick_y / ((gamepad1.left_bumper) ? 3 : 1),
                                    -gamepad1.left_stick_x / ((gamepad1.left_bumper) ? 3 : 1),
                                    -gamepad1.right_stick_x
                            )
                    );
                    break;
                case FIELD:
                    if (gamepad1.dpad_left) {
                        mode = DriveModes.ROBOT;
                    }
                    change = Math.random();

                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    (-gamepad1.left_stick_y / ((gamepad1.left_bumper) ? 3 : 1)) - change,
                                    (-gamepad1.left_stick_x / ((gamepad1.left_bumper) ? 3 : 1)) + (change / 2.0),
                                    -gamepad1.right_stick_x
                            )
                    );
                    break;
            }

            telemetry.addData("mode", mode);
            telemetry.update();
            drive.update();
        }
    }
}

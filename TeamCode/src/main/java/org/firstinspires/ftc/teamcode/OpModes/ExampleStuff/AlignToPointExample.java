package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp (name="Align To Point")
public class AlignToPointExample extends LinearOpMode {
    SampleMecanumDrive drive;

    enum Mode {
        NORMAL,
        ALIGN
    }

    public Mode mode = Mode.NORMAL;
    public Vector2d targetPosition = new Vector2d(0,0);
    private PIDFController headingController = new PIDFController(SampleMecanumDrive.HEADING_PID);
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.setPoseEstimate(new Pose2d((14.65/2) + 1, 62.7, Math.toRadians(90)));
        headingController.setInputBounds(-Math.PI, Math.PI);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            Pose2d poseEstimate = drive.getPoseEstimate();


            telemetry.addData("mode", mode);

            switch (mode) {
                case NORMAL: default:
                    if (gamepad1.a) {
                        mode = Mode.ALIGN;
                    }
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    -gamepad1.left_stick_y / ((gamepad1.left_bumper) ? 3 : 1),
                                    -gamepad1.left_stick_x / ((gamepad1.left_bumper) ? 3 : 1),
                                    -gamepad1.right_stick_x
                            )
                    );
                    break;
                case ALIGN:
                    if (gamepad1.b) {
                        mode = Mode.NORMAL;
                    }

                    Vector2d fieldFrameInput = new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    );
                    Vector2d robotFrameInput = fieldFrameInput.rotated(-poseEstimate.getHeading());

                    // Difference between the target vector and the bot's position
                    Vector2d difference = targetPosition.minus(poseEstimate.vec());
                    // Obtain the target angle for feedback and derivative for feedforward
                    double theta = difference.angle();

                    // Not technically omega because its power. This is the derivative of atan2
                    double thetaFF = -fieldFrameInput.rotated(-Math.PI / 2).dot(difference) / (difference.norm() * difference.norm());

                    // Set the target heading for the heading controller to our desired angle
                    headingController.setTargetPosition(theta);

                    // Set desired angular velocity to the heading controller output + angular
                    // velocity feedforward
                    double headingInput = (headingController.update(poseEstimate.getHeading())
                            * DriveConstants.kV + thetaFF)
                            * DriveConstants.TRACK_WIDTH;

                    // Combine the field centric x/y velocity with our derived angular velocity

                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    robotFrameInput,
                                    headingInput
                            )
                    );
                    break;
            }

            headingController.update(poseEstimate.getHeading());
            drive.update();

            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();
        }

    }
}

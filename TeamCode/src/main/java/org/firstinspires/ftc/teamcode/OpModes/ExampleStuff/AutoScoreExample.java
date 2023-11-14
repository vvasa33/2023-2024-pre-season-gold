package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@TeleOp (name="AutoScoreExample")
public class AutoScoreExample extends LinearOpMode {
    SampleMecanumDrive drive;

    public enum DriverControl {
        MANUAL,
        AUTO
    }
    public DriverControl state = DriverControl.MANUAL;


    @Override
    public void runOpMode() throws InterruptedException {
        PhotonCore.experimental.setMaximumParallelCommands(7);
        PhotonCore.start(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.setPoseEstimate(new Pose2d(14.65/2 + 1, 62.7, Math.toRadians(90)));

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            Pose2d pose = drive.getPoseEstimate();

            switch (state) {
                case MANUAL:
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    -gamepad1.left_stick_y,
                                    -gamepad1.left_stick_x,
                                    -gamepad1.right_stick_x
                            )
                    );

                    if (gamepad1.a && pose.getX() > 0 && pose.getY() > -10) {
                        state = DriverControl.AUTO;
                        Trajectory lineUp = drive.trajectoryBuilder(pose)
                                .lineToLinearHeading(new Pose2d(47.1, 34.7, Math.toRadians(180)))
                                .build();
                        drive.followTrajectoryAsync(lineUp);
                    }

                    break;
                case AUTO:
                    if (!gamepad1.atRest()) {
                        drive.breakFollowing();
                        state = DriverControl.MANUAL;
                    }
                    if (!drive.isBusy()) {
                        state = DriverControl.MANUAL;
                    }
                    break;
            }
            drive.update();
            telemetry.addData("mode", state);
            telemetry.addData("x", pose.getX());
            telemetry.addData("y", pose.getY());
            telemetry.addData("heading", pose.getHeading());
            telemetry.update();
        }
    }
}

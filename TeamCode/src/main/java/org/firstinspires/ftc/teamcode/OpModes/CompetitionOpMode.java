package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.OpModeHardware;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp (name=CompetitionOpMode.name, group= HardwareConstants.opModeGroup)
public class CompetitionOpMode extends LinearOpMode {
    public static final String name = "Competition Op Modes";

    private SampleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(0,0,Math.toRadians(0)));
        //robot.init();

        telemetry.addData(">", "Running opmode...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //robot.drive.moveRobotFieldCentric();
            Pose2d estimate = drive.getPoseEstimate();

            Vector2d input = new Vector2d(
                    -gamepad1.left_stick_y * ((gamepad1.right_bumper) ? 0.3 : 1),
                    -gamepad1.left_stick_x * ((gamepad1.right_bumper) ? 0.3 : 1)
            ).rotated(-estimate.getHeading());

            drive.setWeightedDrivePower(
                    new Pose2d(
                            input.getX(),
                            input.getY(),
                            -gamepad1.right_stick_x
                    )
            );

            // Update everything. Odometry. Etc.
            drive.update();
        }
    }
}

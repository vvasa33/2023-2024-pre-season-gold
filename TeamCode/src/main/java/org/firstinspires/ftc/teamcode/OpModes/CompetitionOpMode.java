package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.Hardware.OpModeHardware;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp (name=CompetitionOpMode.name, group= HardwareConstants.opModeGroup)
public class CompetitionOpMode extends LinearOpMode {
    public static final String name = "Competition Op Modes";
    DcMotorEx motor;

    SampleMecanumDrive drive;



    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorEx.class, "motor");
        //drive.setPoseEstimate(new Pose2d(0,0,0));
        drive = new SampleMecanumDrive(hardwareMap);

        telemetry.addData(">", "Running opmode...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() & !isStopRequested()) {
            moveRobotFieldCentric(); //this is not field centric
            //drive.update();
            if (gamepad1.left_trigger > 0.1) {
                motor.setPower(-gamepad1.left_trigger);
            } else if (gamepad1.right_trigger > 0.1) {
                motor.setPower(gamepad1.right_trigger);
            } else {
                motor.setPower(0);
            }
        }
    }

    public void moveRobotFieldCentric() {
        drive.setWeightedDrivePower(
                new Pose2d(
                        -gamepad1.left_stick_y / ((gamepad1.left_bumper) ? 0.3 : 1),
                        -gamepad1.left_stick_x / ((gamepad1.left_bumper) ? 0.3 : 1),
                        -gamepad1.right_stick_x
                )
        );

        // Update everything. Odometry. Etc.
        drive.update();
    }
}

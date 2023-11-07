package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.OpModes.ExampleStuff.LocalizationStorageCloser;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Drivetrain extends SampleMecanumDrive {

    private final LinearOpMode opMode;
    public Drivetrain(HardwareMap hardwareMap, LinearOpMode opMode) {
        super(hardwareMap);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setPoseEstimate(LocalizationStorageCloser.poseIntoTeleOp);
        this.opMode = opMode;
    }

    public void moveRobotFieldCentric() {
        Pose2d estimate = getPoseEstimate();

        Vector2d input = new Vector2d(
                -opMode.gamepad1.left_stick_y * ((opMode.gamepad1.right_bumper) ? 0.3 : 1),
                -opMode.gamepad1.left_stick_x * ((opMode.gamepad1.right_bumper) ? 0.3 : 1)
        ).rotated(-estimate.getHeading());

        setWeightedDrivePower(
                new Pose2d(
                        input.getX(),
                        input.getY(),
                        -opMode.gamepad1.right_stick_x
                )
        );

        // Update everything. Odometry. Etc.
        update();
    }
}

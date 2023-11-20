package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name="LiftExample")
public class LiftExample extends LinearOpMode {
    SampleMecanumDrive drive;
    PIDFController liftController;
    DcMotorEx lift;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        liftController = new PIDFController(0,0,0,0);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();


            if (gamepad1.left_bumper) {
                lift.setPower(0.4);
            } else if (gamepad1.right_bumper) {
                lift.setPower(-0.4);
            } else {
                lift.setPower(0);
            }


//            if (gamepad1.b) {
//                lift.setPower(liftController.calculate(lift.getCurrentPosition(), 500));
//            }


            telemetry.addData("Motor position", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}

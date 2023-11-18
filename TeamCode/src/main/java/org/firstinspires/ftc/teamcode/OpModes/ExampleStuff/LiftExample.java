package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name="LiftExample")
public class LiftExample extends LinearOpMode {
    SampleMecanumDrive drive;

    DcMotorEx lift;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        lift = hardwareMap.get(DcMotorEx.class, "lift");

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

//            if (gamepad1.start) {
//                for (int i = 0; i < 10000000; i++) {
//                    telemetry.addData("amogus", "amogus");
//                }
//                telemetry.update();
//            }
        }
    }
}

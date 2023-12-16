package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="liftsimpleteleop")
public class simpleteleop extends LinearOpMode {
    public DcMotorEx lift;
    @Override
    public void runOpMode() throws InterruptedException {
        lift = hardwareMap.get(DcMotorEx.class, "lift");

        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad1.x) {
                lift.setPower(-0.7);
            } else if (gamepad1.b) {
                lift.setPower(0.7);
            } else {
                lift.setPower(0);
            }

        }
    }
}

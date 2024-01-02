package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="liftsimpleteleop")
public class simpleteleop extends LinearOpMode {
    public DcMotorEx lift;
    public void runOpMode() throws InterruptedException{
        lift=hardwareMap.get (DcMotorEx.class,"lift");
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad1.b) {
                lift.setTargetPosition(500);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(0.5);

            } else if (gamepad1.x) {
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(0.5);
            }
        }
    }
}

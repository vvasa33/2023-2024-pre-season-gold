package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp (name="Encoder Reader")
public class EncoderReader extends LinearOpMode {
    DcMotorEx lift;
    @Override
    public void runOpMode() throws InterruptedException {
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Encoder ticks", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}

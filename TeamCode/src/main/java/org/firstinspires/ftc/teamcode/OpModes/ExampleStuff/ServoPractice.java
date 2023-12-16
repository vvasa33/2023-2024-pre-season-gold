package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;

@TeleOp (name="servostuff")
public class ServoPractice extends LinearOpMode {
    Servo arm;
    DcMotorEx lift;


    @Override
    public void runOpMode() throws InterruptedException {
        arm = hardwareMap.get(Servo.class, "arm");
        lift = hardwareMap.get(DcMotorEx.class, "lift");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            int liftPosition = lift.getCurrentPosition();


            switch (HardwareConstants.currentLiftPosition) {
                case GROUND:

            }

        }
    }
}

package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp (name="testing")
public class testing extends LinearOpMode {
    CRServo servo;

    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(CRServo.class, "servo");


        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                servo.setPower(1);
            }
        }
    }
}

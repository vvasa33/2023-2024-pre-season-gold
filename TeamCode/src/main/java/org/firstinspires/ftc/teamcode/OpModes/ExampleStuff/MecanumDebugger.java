package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp (name="helpme")
public class MecanumDebugger extends LinearOpMode {
    DcMotorEx fl, fr, bl, br;

    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        br = hardwareMap.get(DcMotorEx.class, "br");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad1.x) {
                fl.setPower(1);
            } else {
                fl.setPower(0);
            }
            if (gamepad1.y) {
                fr.setPower(1);
            } else {
                fr.setPower(0);
            }
            if (gamepad1.b) {
                br.setPower(1);
            } else {
                br.setPower(0);
            }
            if (gamepad1.a) {
                bl.setPower(1);
            } else {
                bl.setPower(0);
            }
        }
    }
}

package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp (name="sensorstuff")
public class funwithsensors extends LinearOpMode {
    ColorSensor back, front;
    @Override
    public void runOpMode() throws InterruptedException {
        back = hardwareMap.get(ColorSensor.class, "back");
        front = hardwareMap.get(ColorSensor.class, "front");
        back.enableLed(true);
        front.enableLed(true);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            //if you're from the future please dont do this, bitshift/bitmask the value from back.argb() to impress teams!!1!!!11!
            telemetry.addData("Back sensor: ", back.red() + " " + back.green() + " " + back.blue());
            telemetry.addData("Front sensor: ", front.red() + " " + front.green() + " " + front.blue());
            telemetry.update();
        }
    }
}

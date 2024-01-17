package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@Config
@TeleOp (name="ServoSettings")
public class ServoSettings extends LinearOpMode {
    Servo arm1, arm2;
    Servo joint;
    Servo claw1, claw2;
    Servo airplane;

    public static double armPosition = 0;
    public static double jointPosition = 0;
    public static double clawPosition = 0;
    public static double claw2Position = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        arm1 = hardwareMap.get(Servo.class, "arm");
        //arm2 = hardwareMap.get(Servo.class, "arm2");
        joint = hardwareMap.get(Servo.class, "joint");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            arm1.setPosition(armPosition);
            //arm2.setPosition(armPosition);
            joint.setPosition(jointPosition);
            claw1.setPosition(clawPosition);
            claw2.setPosition(claw2Position);


        }
    }
}

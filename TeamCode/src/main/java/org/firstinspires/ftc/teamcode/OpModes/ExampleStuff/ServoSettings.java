package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@Config
@TeleOp (name="ServoSettings")
public class ServoSettings extends LinearOpMode {
    ServoImplEx arm1, arm2;
    ServoImplEx joint;
    ServoImplEx claw1, claw2;

    public static double armPosition = 0;
    public static double jointPosition = 0;
    public static double clawPosition = 0;
    public static double claw2Position = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        arm1 = hardwareMap.get(ServoImplEx.class, "arm1");
        arm2 = hardwareMap.get(ServoImplEx.class, "arm2");
        joint = hardwareMap.get(ServoImplEx.class, "joint");
        claw1 = hardwareMap.get(ServoImplEx.class, "claw1");
        claw2 = hardwareMap.get(ServoImplEx.class, "claw2");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            arm1.setPosition(armPosition);
            arm2.setPosition(armPosition);
            joint.setPosition(jointPosition);
            claw1.setPosition(clawPosition);
            claw2.setPosition(claw2Position);
        }
    }
}

package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@TeleOp(name="LiftExample")
public class LiftExample extends LinearOpMode {
    //SampleMecanumDrive drive;


    public static double p = 0, i = 0, d = 0;
    public static double f = 0;
    PIDController liftController;
    public static double ff;
    public static int target = 0;

    public double ticks_per_degree = 384.5;
    DcMotorEx lift;

    @Override
    public void runOpMode() throws InterruptedException {
        //drive = new SampleMecanumDrive(hardwareMap);
        lift = hardwareMap.get(DcMotorEx.class, "lift");

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        liftController = new PIDController(p,i,d);
        PhotonCore.start(hardwareMap);
        PhotonCore.experimental.setMaximumParallelCommands(6);


        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            liftController.setPID(p,i,d);
            int liftPos = lift.getCurrentPosition();
            double pid = liftController.calculate(liftPos, target);
            ff = Math.cos(Math.toRadians(target / ticks_per_degree)) * f;

            lift.setPower(pid + ff);

            telemetry.addData("Motor position", lift.getCurrentPosition());
            telemetry.addData("Target position", target);
            telemetry.update();
        }
    }
}

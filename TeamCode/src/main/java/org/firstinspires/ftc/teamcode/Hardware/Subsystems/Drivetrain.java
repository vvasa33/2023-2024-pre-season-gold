package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Drivetrain {
    private DcMotorEx fl, fr, bl, br;

    LinearOpMode opMode;

    public Drivetrain(LinearOpMode opMode) {
        this.opMode = opMode;

        fl = opMode.hardwareMap.get(DcMotorEx.class, "fl");
        fr = opMode.hardwareMap.get(DcMotorEx.class, "fr");
        bl = opMode.hardwareMap.get(DcMotorEx.class, "bl");
        br = opMode.hardwareMap.get(DcMotorEx.class, "br");

        //TODO fix this idk why our robot is so janky
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        //thanks to zach for the copy and paste
        fl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        fl.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void moveRobot() {
        double flPower = +opMode.gamepad1.left_stick_x - opMode.gamepad1.left_stick_y + opMode.gamepad1.right_stick_x;
        double frPower = -opMode.gamepad1.left_stick_x - opMode.gamepad1.left_stick_y - opMode.gamepad1.right_stick_x;
        double blPower = -opMode.gamepad1.left_stick_x - opMode.gamepad1.left_stick_y + opMode.gamepad1.right_stick_x;
        double brPower = +opMode.gamepad1.left_stick_x - opMode.gamepad1.left_stick_y - opMode.gamepad1.right_stick_x;

        double triggerVal = opMode.gamepad1.left_trigger;

        if (triggerVal > 0) {
            fl.setPower(flPower * (1 - triggerVal));
            fr.setPower(frPower * (1 - triggerVal));
            bl.setPower(blPower * (1 - triggerVal));
            fr.setPower(brPower * (1 - triggerVal));
        } else if (opMode.gamepad1.left_bumper) {
            fl.setPower(flPower / 3.0);
            fr.setPower(frPower / 3.0);
            bl.setPower(blPower / 3.0);
            br.setPower(brPower / 3.0);
        } else {
            fl.setPower(flPower);
            fr.setPower(frPower);
            bl.setPower(blPower);
            br.setPower(brPower);
        }
    }
}

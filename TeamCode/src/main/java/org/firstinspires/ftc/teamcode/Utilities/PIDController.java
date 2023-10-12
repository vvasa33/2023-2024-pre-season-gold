package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    private double kp = 0, ki = 0, kd = 0;
    private double reference;
    private double integralSum = 0;
    private double lastError = 0;
    ElapsedTime timer = new ElapsedTime();
    private DcMotorEx motor;

    public PIDController(DcMotorEx motor, double kp, double ki, double kd, double reference) {
        this.motor = motor;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.reference = reference;
    }

    public double calculate() {
        double position = motor.getCurrentPosition();
        double error = reference - position;
        //velocityError = targetVelocity - robotCurrentVelocity;
        //double u = (error * k1) + (velocityError * k2);
        double derivative = (error - lastError) / timer.seconds();
        integralSum += error * timer.seconds();

        double output = (kp * error) + (ki * integralSum) + (kd * derivative);

        timer.reset();
        lastError = error;
        return output;
    }
}

package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    private double kp, ki, kd, kf;
    private final double reference;
    private double integralSum = 0;
    private double lastError = 0;
    ElapsedTime timer = new ElapsedTime();
    private final DcMotorEx motor;

    public PIDController(DcMotorEx motor, PIDFCoefficients coefficients, double reference) {
        this.motor = motor;
        coefficients.p = kp;
        coefficients.i = ki;
        coefficients.d = kd;
        coefficients.f = kf;
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

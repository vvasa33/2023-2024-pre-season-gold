package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDFController {
    private double kp, ki, kd;
    //TODO tune kg for gravity feedforward for V-rail mounts
    private double kg;

    private double integralSum = 0;
    private double lastError = 0;
    ElapsedTime timer = new ElapsedTime();
    private final DcMotorEx motor;

    public PIDFController(DcMotorEx motor, PIDFCoefficients coefficients) {
        this.motor = motor;
        coefficients.p = kp;
        coefficients.i = ki;
        coefficients.d = kd;
        coefficients.f = kg;
    }

    public double calculate(double reference) {
        double position = motor.getCurrentPosition();
        double error = reference - position;
        //velocityError = targetVelocity - robotCurrentVelocity;
        //double u = (error * k1) + (velocityError * k2);
        double derivative = (error - lastError) / timer.seconds();
        integralSum += error * timer.seconds();

        double output = (kp * error) + (ki * integralSum) + (kd * derivative);

        output += kg;

        timer.reset();
        lastError = error;
        return output;
    }
}

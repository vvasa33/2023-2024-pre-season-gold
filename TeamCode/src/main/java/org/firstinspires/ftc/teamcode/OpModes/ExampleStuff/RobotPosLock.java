package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class RobotPosLock extends LinearOpMode {

    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    double xyP = 1;
    double headingP = 1;
    public void runOpMode() throws InterruptedException {
        Pose2d changeThis = new Pose2d(0,0,0);
    }

    public void lockTo(Pose2d targetPos) {
        Pose2d currPos = drive.getPoseEstimate();
        Pose2d difference = targetPos.minus(currPos);

        Vector2d xy = difference.vec().rotated(-currPos.getHeading());
        double heading = Angle.normDelta(targetPos.getHeading()) - Angle.normDelta(currPos.getHeading());
        drive.setWeightedDrivePower(
                new Pose2d(xy.times(xyP), heading * headingP)
        );
    }
}

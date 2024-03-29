package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous (name="badauto")
public class builderauto extends LinearOpMode {
    SampleMecanumDrive drive;
    ElapsedTime timer;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        timer = new ElapsedTime();

        waitForStart();

        //lessons learned: give your programmers time to program or else this happens

        //ran this during mt st josephs, it just runs motors for a set period of time
        //jk i didnt actually run this i was too scared
        while (timer.seconds() < 3) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            1,
                            0,
                            0
                    )
            );
            drive.update();
        }
    }
}

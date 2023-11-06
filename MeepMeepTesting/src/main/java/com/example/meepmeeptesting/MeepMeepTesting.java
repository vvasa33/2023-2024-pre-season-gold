package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    public static void main(String args[]) {
        MeepMeep meep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14.65)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(6.2, 62.7, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(45, 35.3, 0))
                                .splineToLinearHeading(new Pose2d(13.9,58.7, Math.toRadians(180)), Math.toRadians(180))
                                .splineTo(new Vector2d(-35.8, 58.9), Math.toRadians(180))
                                .splineTo(new Vector2d(-61, 35.8), Math.toRadians(180))
                                .build()
                );

        meep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTesting {

    public static void main(String args[]) {
        MeepMeep meep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14.65)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(6.2, 62.7, Math.toRadians(90)))
                                .lineTo(new Vector2d(22.7,41.6)) //go to the left spike mark
                                .splineToLinearHeading(new Pose2d(49,41.4, Math.toRadians(180)), Math.toRadians(0)) //go to the left backdrop
                                .splineTo(new Vector2d(10,10), Math.toRadians(180)) //go to the beginning of the stage door
                                .splineTo(new Vector2d(-35.6, 10), Math.toRadians(180)) //go through the door
                                .splineTo(new Vector2d(-58, 11.8), Math.toRadians(180)) //go to the left pixel stack
                                .splineToLinearHeading(new Pose2d(10, 10, 0), Math.toRadians(0)) //go back

                                .build()
                );

        meep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
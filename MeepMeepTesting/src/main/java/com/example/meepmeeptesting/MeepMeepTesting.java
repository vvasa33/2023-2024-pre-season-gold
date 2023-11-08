package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTesting {

    public static void main(String[] args) {
        MeepMeep meep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14.65)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(14.65/2, 62.7, Math.toRadians(90)))
                                .lineTo(new Vector2d(22.7,46.4)) //vision spike left
                                .splineToLinearHeading(new Pose2d(47.1, 41.1, Math.toRadians(180)), Math.toRadians(0)) //board spot left
                                .splineTo(new Vector2d(10,10), Math.toRadians(180)) //align with the backstage door
                                .splineTo(new Vector2d(-36.3, 13), Math.toRadians(180)) //go through door
                                .splineTo(new Vector2d(-58, 11.8), Math.toRadians(180)) //go to left pixel stack
                                .splineToConstantHeading(new Vector2d(-36.3, 13), Math.toRadians(0)) //line back up with the backstage door
                                .splineToConstantHeading(new Vector2d(10,10), Math.toRadians(0)) //go through door again
                                .splineTo(new Vector2d(47.1, 28.4), Math.toRadians(0)) //go to the right scoring location
                                //NOW WE GO BACK

                                .build()
                );

        meep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

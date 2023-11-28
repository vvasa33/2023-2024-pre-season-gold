package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepFarBlue {

    public static void main(String[] args) {
        MeepMeep meep = new MeepMeep(800);

        RoadRunnerBotEntity meepmeep = new DefaultBotBuilder(meep)
                .setConstraints(55, 55, 4.115477328170351, Math.toRadians(194.6310524271845), 14.65)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-34, 62.7, Math.toRadians(90)))
                                .build()

                        );
    }
}

package org.firstinspires.ftc.teamcode.CameraStuff;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.Locale;

public class CameraTest extends LinearOpMode {
    private VisionPortal portal;
    private PropDetectorRed pipeline;
    @Override
    public void runOpMode() throws InterruptedException {
        pipeline = new PropDetectorRed();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .setCameraResolution(new Size(640,480))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(pipeline)
                .build();

        portal.saveNextFrameRaw(String.format(Locale.US, "CameraFrameCapture-%06d"));
        waitForStart();
        telemetry.addLine(String.valueOf(pipeline.getArea()));
        telemetry.update();

    }
}

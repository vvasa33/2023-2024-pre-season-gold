package org.firstinspires.ftc.teamcode.CameraStuff;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp (name="cameratest")
public class CameraTest extends LinearOpMode {
    private VisionPortal portal;
    private PropDetectorRed pipeline;
    @Override
    public void runOpMode() throws InterruptedException {
        pipeline = new PropDetectorRed();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "0")) //the zero represents the first webcam detected by the control hub os
                .setCameraResolution(new Size(640,480))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(pipeline)
                .build();

        //portal.saveNextFrameRaw(String.format(Locale.US, "CameraFrameCapture-%06d"));
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addLine(String.valueOf(pipeline.getArea()));
            telemetry.update();
        }

    }
}

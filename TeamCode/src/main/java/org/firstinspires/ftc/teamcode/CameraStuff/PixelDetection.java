package org.firstinspires.ftc.teamcode.CameraStuff;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class PixelDetection implements VisionProcessor {
    Mat test = new Mat();
    Mat highMat = new Mat();
    Mat lowMat = new Mat();
    Mat finalMat = new Mat();

    Scalar lowWhiteHSV = new Scalar(10, 6, 84);
    Scalar highWhiteHSV = new Scalar(0,0,100);
    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(test, frame, Imgproc.COLOR_RGB2HSV); //changes it to hsv
        Core.inRange(test, lowWhiteHSV, highWhiteHSV, finalMat); //filters out white


        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onScreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}

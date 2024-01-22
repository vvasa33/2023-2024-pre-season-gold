package org.firstinspires.ftc.teamcode.CameraStuff;

import android.graphics.Canvas;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@Config
public class PropDetectorRed implements VisionProcessor {
    Mat test = new Mat();
    Mat highMat = new Mat();
    Mat lowMat = new Mat();
    Mat finalMat = new Mat();
    double threshold = 0.4;
    Scalar lowHSVRedLower = new Scalar(0, 100, 20);  //Beginning of Color Wheel
    Scalar lowHSVRedUpper = new Scalar(10, 255, 255);

    Scalar redHSVRedLower = new Scalar(160, 100, 20); //Wraps around Color Wheel
    Scalar highHSVRedUpper = new Scalar(180, 255, 255);

    public static HardwareConstants.CameraAreas area = HardwareConstants.CameraAreas.CENTER;

    public static int x, y, x2, y2;

    static final Rect leftRect = new Rect(
            new Point(x,y),
            new Point(x2,y2)
    );

    static final Rect rightRect = new Rect(
            new Point(0,0),
            new Point(0,0)
    );

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, test, Imgproc.COLOR_RGB2HSV);

        Core.inRange(test, lowHSVRedLower, lowHSVRedUpper, lowMat); //basically doing it for the first part of the color range
        Core.inRange(test, redHSVRedLower, highHSVRedUpper, highMat);

        test.release();

        Core.bitwise_or(lowMat, highMat, finalMat);

        lowMat.release();
        highMat.release();

        double leftBox = Core.sumElems(finalMat.submat(leftRect)).val[0];
        double rightBox = Core.sumElems(finalMat.submat(rightRect)).val[0];

        double averagedLeftBox = leftBox / leftRect.area() / 255;
        double averagedRightBox = rightBox / rightRect.area() / 255; //Makes value [0,1]

        if(averagedLeftBox > threshold){        //Must Tune Red Threshold
            area = HardwareConstants.CameraAreas.LEFT;
        }else if(averagedRightBox> threshold){
            area = HardwareConstants.CameraAreas.CENTER;
        }else{
            area = HardwareConstants.CameraAreas.RIGHT;
        }

        finalMat.copyTo(frame);
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onScreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

    public HardwareConstants.CameraAreas getArea() {
        return area;
    }
}

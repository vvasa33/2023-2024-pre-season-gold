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
public class PropDetectorBlue implements VisionProcessor {
    Mat test = new Mat();
    Mat highMat = new Mat();
    Mat lowMat = new Mat();
    Mat finalMat = new Mat();
    double threshold = 0.1;
//    Scalar lowHSVRedLower = new Scalar(0, 100, 20);  //Beginning of Color Wheel
//    Scalar lowHSVRedUpper = new Scalar(10, 255, 255);
//
//    Scalar redHSVRedLower = new Scalar(160, 100, 20); //Wraps around Color Wheel
//    Scalar highHSVRedUpper = new Scalar(180, 255, 255);

    Scalar lowHSVRedLower = new Scalar(70,62,36);  //Beginning of Color Wheel
    Scalar lowHSVRedUpper = new Scalar(140,255,191);


    public static HardwareConstants.CameraAreas area = HardwareConstants.CameraAreas.CENTER;

    public static int x, y, x2, y2;

    static final Rect leftRect = new Rect(
            new Point(5,5),
            new Point(319,479)
    );

    static final Rect rightRect = new Rect(
            new Point(400,0),
            new Point(640,480)
    );





    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, test, Imgproc.COLOR_RGB2HSV);

//        Core.inRange(test, lowHSVRedLower, lowHSVRedUpper, lowMat); //basically doing it for the first part of the color range
//        Core.inRange(test, redHSVRedLower, highHSVRedUpper, highMat);
//
//
//        test.release();
//
//        Core.bitwise_or(lowMat, highMat, finalMat);
//
//        lowMat.release();
//        highMat.release();

        Core.inRange(test, lowHSVRedLower, lowHSVRedUpper, finalMat);


        double leftBox = Core.sumElems(finalMat.submat(leftRect)).val[0];
        double rightBox = Core.sumElems(finalMat.submat(rightRect)).val[0];

        double averagedLeftBox = leftBox / leftRect.area() / 255;
        double averagedRightBox = rightBox / rightRect.area() / 255; //Makes value [0,1]

        if(averagedLeftBox > threshold){        //Must Tune Red Threshold
            area = HardwareConstants.CameraAreas.CENTER;
        } else if(averagedRightBox > threshold){
            area = HardwareConstants.CameraAreas.RIGHT;
        } else {
            area = HardwareConstants.CameraAreas.LEFT;
        }

        Imgproc.rectangle(finalMat, leftRect, new Scalar(295.0d / 2, 100d, 100d));
        Imgproc.rectangle(finalMat, rightRect, new Scalar(295.0d / 2, 100d, 100d));

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

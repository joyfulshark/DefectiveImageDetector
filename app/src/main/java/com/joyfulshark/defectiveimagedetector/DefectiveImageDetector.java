package com.joyfulshark.defectiveimagedetector;

import android.widget.Toast;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class DefectiveImageDetector {

    private static final int HIGH_THRESH = 200;
    private static final int LOW_THRESH = 30;

    public static Mat loadImage(String imageFilePath) {
        Imgcodecs imageCodecs = new Imgcodecs();
        Mat img = imageCodecs.imread(imageFilePath);
        if(img.empty()) {
            return null;
        }
        return img;
    }

    static boolean isDefective(String imageFilePath){
        Mat img = loadImage(imageFilePath);
        if(img == null) {
            return true;
        }
        return isDarkOrBrightImage(img);
    }

    private static boolean isDarkOrBrightImage(Mat img) {
        double meanColor = computeMeanColor(img);
        if (meanColor > HIGH_THRESH || meanColor < LOW_THRESH) {
            return true;
        }
        return false;
    }

    private static double computeMeanColor(Mat img) {
        Mat grayImage = new Mat(img.rows(), img.cols(), CvType.CV_8U, new Scalar(1));
        Imgproc.cvtColor(img, grayImage, Imgproc.COLOR_RGB2GRAY);

        MatOfDouble meansrc = new MatOfDouble();
        MatOfDouble stdsrc= new MatOfDouble();

        Core.meanStdDev(grayImage, meansrc, stdsrc);

        return meansrc.get(0,0)[0];
    }

}

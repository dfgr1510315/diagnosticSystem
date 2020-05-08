package com.ljz.diagnostic_system.common.Utils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class ImageUtils {


    public static void main(String[] args) {
// Set image path
        String basePicPath = "D:\\IntelliJIDEA2018.2\\javaweb\\diagnosticSystem\\src\\main\\resources\\static\\files\\image\\";
        String filename1 = basePicPath + "cd3dfaf0-68d9-4cbd-a431-f57d33c2b9da.png";
        String filename2 = basePicPath + "f883cdc8-d745-4d93-9dc5-9ca6b5aa5888.png";


        double ret;
        ret = compareHistogram(filename1, filename2);
        System.out.println(ret);
        if (ret > 0) {
            System.out.println("相同.");
        } else {
            System.out.println("不同.");
        }
    }


    /**
     * Compare that two images is similar using histogram
     * @author minikim
     * @param filename1 - the first image
     * @param filename2 - the second image
     * @return integer - 1 if two images are similar, 0 if not
     */
    public static double compareHistogram(String filename1, String filename2) {
        int retVal = 0;
        long startTime = System.currentTimeMillis();
        //TODO:win环境下静态库路径
        //String basePicPath = "D:\\IntelliJIDEA2018.2\\javaweb\\diagnosticSystem\\src\\main\\resources\\static\\files\\image\\";
        //TODO:linux环境下文件路径
        String basePicPath = "/usr/local/tomcat/webapps/diagnostic_system/WEB-INF/classes/static/files/image/";
        filename1 = basePicPath + "diseaseImage/" + filename1;
        filename2 = basePicPath + "diagnosisImage/" + filename2;
        System.out.println("filename1:"+filename1);
        System.out.println("filename2:"+filename2);
        //TODO:win环境下静态库路径
        //System.load("D:\\JAVA\\OpenCV_3_4_7\\build\\java\\x64\\opencv_java347.dll");
        //TODO:linux环境下静态库路径
        System.load("/usr/local/opencv/opencv-3.4.3/build/lib/libopencv_java343.so");
        MatOfFloat ranges = new MatOfFloat(0,255);
        MatOfInt histSize = new MatOfInt(50);
        MatOfInt channels = new MatOfInt(0);

        // Load images to compare
        Mat img1 = Imgcodecs.imread(filename1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Mat img2 = Imgcodecs.imread(filename2, Imgcodecs.CV_LOAD_IMAGE_COLOR);

        Mat hsvImg1 = new Mat();
        Mat hsvImg2 = new Mat();

        // Convert to HSV
        Imgproc.cvtColor(img1, hsvImg1, Imgproc.COLOR_BGR2HSV);
        Imgproc.cvtColor(img2, hsvImg2, Imgproc.COLOR_BGR2HSV);

        // Set configuration for calchist()
        List<Mat> listImg1 = new ArrayList<>();
        List<Mat> listImg2 = new ArrayList<>();

        listImg1.add(hsvImg1);
        listImg2.add(hsvImg2);

        // Histograms
        Mat histImg1 = new Mat();
        Mat histImg2 = new Mat();

        // Calculate the histogram for the HSV imgaes
        Imgproc.calcHist(listImg1, channels, new Mat(), histImg1, histSize, ranges);
        Imgproc.calcHist(listImg2, channels, new Mat(), histImg2, histSize, ranges);

        Core.normalize(histImg1, histImg1, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        Core.normalize(histImg2, histImg2, 0, 1, Core.NORM_MINMAX, -1, new Mat());


        // Apply the histogram comparison methods
        // 0 - correlation: the higher the metric, the more accurate the match "> 0.9"
        // 1 - chi-square: the lower the metric, the more accurate the match "< 0.1"
        // 2 - intersection: the higher the metric, the more accurate the match "> 1.5"
        // 3 - bhattacharyya: the lower the metric, the more accurate the match  "< 0.3"
        //应用直方图比较法
        // 0-相关性：度量值越高，匹配越精确“>0.9”
        // 1-卡方：度量值越低，匹配“<0.1”的精度越高
        // 2-交集：度量越高，匹配越精确“>1.5”
        // 3-bhattacharyya：度量越低，匹配“<0.3”越精确
        /*double result0, result1, result2, result3;
        result0 = Imgproc.compareHist(histImg1, histImg2, 0);
        result1 = Imgproc.compareHist(histImg1, histImg2, 1);
        result2 = Imgproc.compareHist(histImg1, histImg2, 2);
        result3 = Imgproc.compareHist(histImg1, histImg2, 3);

        System.out.println("Method [0] " + result0);
        System.out.println("Method [1] " + result1);
        System.out.println("Method [2] " + result2);
        System.out.println("Method [3] " + result3);

        // If the count that it is satisfied with the condition is over 3, two images is same.
        //如果满足条件的计数超过3，则两个图像相同。
        int count=0;
        if (result0 > 0.9) count++;
        if (result1 < 0.1) count++;
        if (result2 > 1.5) count++;
        if (result3 < 0.3) count++;
        System.out.println(count);
        if (count >= 3) retVal = 1;

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("estimatedTime=" + estimatedTime + "ms");*/


        return Imgproc.compareHist(histImg1, histImg2, 0);
    }
}
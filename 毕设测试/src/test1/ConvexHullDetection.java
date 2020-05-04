package test1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
/**
 * 凸包检测算法
 * 绘制轮廓和凸包
 * @author 王宇兵
 *
 */
public class ConvexHullDetection {
	static Point[] points;
	public static Point[] Convexhull(Vector<MatOfPoint> contours, Mat m1) {
//		 System.out.println(contours.get(0).get(0,0)[0]);
//		 System.out.println(contours.get(0).get(0,0)[1]);
//	    System.out.println(contours.get(0).dump());
//		System.out.println("########################");
		// 查找凸包  
		List<MatOfInt> hull = new ArrayList<MatOfInt>(); 
	    for(int i=0; i < contours.size(); i++)
	    	hull.add(new MatOfInt());       
	    for(int i=0; i < contours.size(); i++)
	        Imgproc.convexHull(contours.get(i), hull.get(i));   //计算出图像的凸包，根据图像的轮廓点，通过函数convexhull转化成凸包的点点坐标，
                                                                //	 hull.get(i) 中的值为contours.get(i)中第几个凸点  
//	    System.out.println(hull.get(0).dump());	    
//	    System.out.println(hull.get(0).rows());
//	    System.out.println(hull.get(0).cols());
//	    System.out.println(hull.get(0).get(1, 0));//地址
//	    System.out.println(hull.get(0).get(1, 0)[0]);//内容
//	    System.out.println (hull.size());
	    
	    // 将MatOfInt转换为MatOfPoint以便画凸包  	      
	    List<Point[]> hullpoints = new ArrayList<Point[]>(); 
	    for(int i=0; i < hull.size(); i++){ 
	        points = new Point[hull.get(i).rows()]; 
	        for(int j=0; j < hull.get(i).rows(); j++){ 
	        	int index = (int)hull.get(i).get(j, 0)[0]; 
	            points[j] = new Point(contours.get(i).get(index, 0)[0], contours.get(i).get(index, 0)[1]);     //凸点数组	            
	        } 
	        hullpoints.add(points); 
	    }
	    
	    // 将Point arrays转换为MatOfPoint 
	    List<MatOfPoint> hullmop = new ArrayList<MatOfPoint>(); 
	    for(int i=0; i < hullpoints.size(); i++){ 
	        MatOfPoint mop = new MatOfPoint(); 
	        mop.fromArray(hullpoints.get(i)); 
	        hullmop.add(mop); 
	    }
	    
	    //绘制轮廓和凸包
	    Mat overlay = new Mat(m1.size(), CvType.CV_8UC3); 
	    Scalar color = new Scalar(0, 255, 0); // Green 
	    for(int i=0; i < contours.size(); i++){ 
	        Imgproc.drawContours(overlay, contours, i, color); 
	        Imgproc.drawContours(overlay, hullmop, i, color,3); 
	    } 
		HighGui.imshow("绘制凸包", overlay);
		HighGui.waitKey(0);
//		System.out.println("凸包检测算法指尖点##################");
//		for(int i=0;i<points.length;i++)
//			System.out.println(points[i].x+" , "+points[i].y);
		return points;
	}
}

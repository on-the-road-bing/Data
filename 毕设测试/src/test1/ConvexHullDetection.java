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
 * ͹������㷨
 * ����������͹��
 * @author �����
 *
 */
public class ConvexHullDetection {
	static Point[] points;
	public static Point[] Convexhull(Vector<MatOfPoint> contours, Mat m1) {
//		 System.out.println(contours.get(0).get(0,0)[0]);
//		 System.out.println(contours.get(0).get(0,0)[1]);
//	    System.out.println(contours.get(0).dump());
//		System.out.println("########################");
		// ����͹��  
		List<MatOfInt> hull = new ArrayList<MatOfInt>(); 
	    for(int i=0; i < contours.size(); i++)
	    	hull.add(new MatOfInt());       
	    for(int i=0; i < contours.size(); i++)
	        Imgproc.convexHull(contours.get(i), hull.get(i));   //�����ͼ���͹��������ͼ��������㣬ͨ������convexhullת����͹���ĵ�����꣬
                                                                //	 hull.get(i) �е�ֵΪcontours.get(i)�еڼ���͹��  
//	    System.out.println(hull.get(0).dump());	    
//	    System.out.println(hull.get(0).rows());
//	    System.out.println(hull.get(0).cols());
//	    System.out.println(hull.get(0).get(1, 0));//��ַ
//	    System.out.println(hull.get(0).get(1, 0)[0]);//����
//	    System.out.println (hull.size());
	    
	    // ��MatOfIntת��ΪMatOfPoint�Ա㻭͹��  	      
	    List<Point[]> hullpoints = new ArrayList<Point[]>(); 
	    for(int i=0; i < hull.size(); i++){ 
	        points = new Point[hull.get(i).rows()]; 
	        for(int j=0; j < hull.get(i).rows(); j++){ 
	        	int index = (int)hull.get(i).get(j, 0)[0]; 
	            points[j] = new Point(contours.get(i).get(index, 0)[0], contours.get(i).get(index, 0)[1]);     //͹������	            
	        } 
	        hullpoints.add(points); 
	    }
	    
	    // ��Point arraysת��ΪMatOfPoint 
	    List<MatOfPoint> hullmop = new ArrayList<MatOfPoint>(); 
	    for(int i=0; i < hullpoints.size(); i++){ 
	        MatOfPoint mop = new MatOfPoint(); 
	        mop.fromArray(hullpoints.get(i)); 
	        hullmop.add(mop); 
	    }
	    
	    //����������͹��
	    Mat overlay = new Mat(m1.size(), CvType.CV_8UC3); 
	    Scalar color = new Scalar(0, 255, 0); // Green 
	    for(int i=0; i < contours.size(); i++){ 
	        Imgproc.drawContours(overlay, contours, i, color); 
	        Imgproc.drawContours(overlay, hullmop, i, color,3); 
	    } 
		HighGui.imshow("����͹��", overlay);
		HighGui.waitKey(0);
//		System.out.println("͹������㷨ָ���##################");
//		for(int i=0;i<points.length;i++)
//			System.out.println(points[i].x+" , "+points[i].y);
		return points;
	}
}

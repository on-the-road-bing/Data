package test1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * 曲率检测算法，排除凹槽点
 * @author 王宇兵
 *
 */
public class FingerSearch1 {
	static int index;
	double area, maxArea = 0;	
	static List<Point> fingerTips=new ArrayList<>();
	static List<Point> fingerButtoms=new ArrayList<>();
	static Point points_y_min;
	public static List<Point> Finger_Number(List<MatOfPoint> contours,Point center,Mat m1) {
		double area, maxArea = 0;
		for (int i=0; i < contours.size(); i++){                                 //查找最大轮廓
				area = Imgproc.contourArea( contours.get(i));
				if (area > maxArea){
					maxArea = area;
					index = i;
				}			
		}
		Point points_y_min=new Point();
		List<Point> SrcMtx=Contour_Point_Sort.PointSort(contours.get(index).toList());  //对轮廓上的点重拍列
		points_y_min=Contour_Point_Sort.points_y_min();
		
		Point p, q, r;	
		double dot,cross;
		for (int i =25; i < (SrcMtx.size()-25); i++)
		{
			q = SrcMtx.get(i-25);
			p = SrcMtx.get(i);
			r = SrcMtx.get(i+25);
			dot = (q.x - p.x ) * (r.x - p.x) + (q.y - p.y ) * (r.y - p.y);         //向量內积
			double x1=Math.pow(q.x - p.x , 2)+Math.pow(q.y - p.y,2);
			double x2=Math.pow(r.x - p.x , 2)+Math.pow(r.y - p.y,2);
			double cos=dot/(Math.sqrt(x1)*(Math.sqrt(x2)));                        //曲率	
			if(cos>0) {                                                            
				cross = (q.x - p.x ) * (r.y - p.y) - (r.x - p.x ) * (q.y - p.y);   //向量外积分
				if (cross >0){                                                     //凹槽点过滤
					fingerTips.add(p);                                             //曲率检测算法筛选出的指尖点 
					Imgproc.circle(m1, p, 5,new Scalar(0),  Imgproc.FILLED);
					Imgproc.line(m1, center, p, new Scalar(0), 2);	
//					HighGui.imshow("曲率检测算法寻找指尖", m1);HighGui.waitKey(); 
				}
				else {
					fingerButtoms.add(p);                                          //找出凹槽点
				}
			}			
		} 
		 
		HighGui.imshow("曲率检测算法寻找指尖", m1);HighGui.waitKey(0); 

		return fingerTips;
	}	
	public static Point points_y_min() {
		return points_y_min;		
	}
	public static List<Point> fingerButtoms(){
		return fingerButtoms;
	}
}
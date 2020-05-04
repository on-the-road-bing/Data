package test1;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * 步骤一、手臂点过滤算法
 * 分别求检测到的指尖点到重心和手部最低点的距离
 * 当前者小于后者时确定为指尖点
 * 步骤二、集中点过滤算法
 * 分别
 * @author 王宇兵
 *
 */
public class Arm_point_Filter {
	public static List<Point> filter_Algorithm(List<Point> Points,Point center,Point point_y_min,Mat mat) {
		List<Point> afterArm_point_Filter=new ArrayList<>();
		for(int i=0;i<Points.size();i++) {
			Point p=Points.get(i);
//			double distancetoCenter=Math.pow(p.x-center.x,2)+Math.pow(p.y-center.y,2);
//			double distancetoPoint_y_min=Math.pow(p.x-point_y_min.x,2)+Math.pow(p.y-point_y_min.y,2);
			double distancetoCenter=distance(p,center);
			double distancetoPoint_y_min=distance(p,point_y_min);
			if(distancetoCenter<distancetoPoint_y_min) {				
				afterArm_point_Filter.add(p);					
//				Imgproc.circle(mat, p, 5,new Scalar(100),  Imgproc.FILLED);
			}			
		}
		System.out.println("手臂点过滤后的指尖点数量："+afterArm_point_Filter.size());
		Focus_point_Filter(afterArm_point_Filter);                       //步骤二、集中点过滤算法
		System.out.println("集中点过滤后的指尖点数量："+afterArm_point_Filter.size());
		for(int i=0;i<afterArm_point_Filter.size();i++) {
			Imgproc.circle(mat,afterArm_point_Filter.get(i) , 5,new Scalar(100),  Imgproc.FILLED);
		}
		HighGui.imshow("手臂点过滤算法", mat);
		HighGui.waitKey(0);
		return afterArm_point_Filter;				
	}
	public static void Focus_point_Filter(List<Point> Points) {
		for(int i=0;i<Points.size()-1;i++) {
			if(distance(Points.get(i),Points.get(i+1))<200) {
				Points.remove(i);
				i--;
			}
		}	
	}
	public static double distance(Point a,Point b) {
		return Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2);
	}

}

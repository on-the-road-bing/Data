package test1;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Point;


/**
 * 对轮廓上的点进行重排列
 * 1.先找出轮廓上点的最大纵坐标对应的点
 * 2.从最大纵坐标对应的点开始，按顺序将轮廓上后续的点存入afterpointssort
 * 3.将最大纵坐标对应的点之前的点存入afterpointssort，进而实现轮廓上的点重排列
 * 
 * @author 王宇兵
 *
 */
public class Contour_Point_Sort {
	static Point points_y_min=new Point() ;

	public static List<Point>  PointSort(List<Point> points) {
		double[] points_y= new double[points.size()];                    
		for(int i=0;i<points.size();i++) {
			points_y[i]=points.get(i).y;                                    //points_y存放轮廓上点的纵坐标
		}
		int points_y_index=0;                                               //下标
		double points_y_max=points_y[0];                                    //最大值
		for(int i=1;i<points_y.length-1;i++) {                              //求points_y中纵坐标最大值的下标
			if(points_y[i]>points_y_max) {					
				points_y_max=points_y[i];
				points_y_index=i;
			}		
		}
		points_y_min=points.get(points_y_index);                          //将纵坐标最小的点赋值给点points_y_min
		List<Point> afterpointssort = new ArrayList<>();                  //加入后面的点
		for(int i=0;i<points.size()-points_y_index;i++) {
			afterpointssort.add(points.get(points_y_index+i));			
		}
		for(int i=0;i<points_y_index;i++) {                              //加入前面的点
			afterpointssort.add(points.get(i));	
		}
		return afterpointssort;
	} 
	
	public static Point points_y_min() {
		return points_y_min;
	}
}
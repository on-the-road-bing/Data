package test1;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Point;
/**
 * 分别求检测到的指尖间点到重心和手部最低点的距离
 * 当前者小于后者时确定为指尖间的点
 * 指尖间的点集中点处理，只保留一个
 * @author 王宇兵
 *
 */
public class Buttom_Points_filter {
	static List<Point> buttoms_Filter=new ArrayList<>();
	public static List<Point> Filter(List<Point> fingersButtoms,Point center,Point point_y_min) {
		for(int i=0;i<fingersButtoms.size();i++) {
			Point p=fingersButtoms.get(i);
			double distancetoCenter=Math.pow(p.x-center.x,2)+Math.pow(p.y-center.y,2);
			double distancetoPoint_y_min=Math.pow(p.x-point_y_min.x,2)+Math.pow(p.y-point_y_min.y,2);
			if(distancetoCenter<distancetoPoint_y_min) 				
				buttoms_Filter.add(p);				
		}
		for(int i=0;i<buttoms_Filter.size()-1;i++) {
			if(distance(buttoms_Filter.get(i),buttoms_Filter.get(i+1))<200) {
				buttoms_Filter.remove(i);
				i--;
			}
		}
		return  buttoms_Filter;
	}
	public static double distance(Point a,Point b) {
		return Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2);
	}	
}

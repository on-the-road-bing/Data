package test1;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Point;


/**
 * �������ϵĵ����������
 * 1.���ҳ������ϵ������������Ӧ�ĵ�
 * 2.������������Ӧ�ĵ㿪ʼ����˳�������Ϻ����ĵ����afterpointssort
 * 3.������������Ӧ�ĵ�֮ǰ�ĵ����afterpointssort������ʵ�������ϵĵ�������
 * 
 * @author �����
 *
 */
public class Contour_Point_Sort {
	static Point points_y_min=new Point() ;

	public static List<Point>  PointSort(List<Point> points) {
		double[] points_y= new double[points.size()];                    
		for(int i=0;i<points.size();i++) {
			points_y[i]=points.get(i).y;                                    //points_y��������ϵ��������
		}
		int points_y_index=0;                                               //�±�
		double points_y_max=points_y[0];                                    //���ֵ
		for(int i=1;i<points_y.length-1;i++) {                              //��points_y�����������ֵ���±�
			if(points_y[i]>points_y_max) {					
				points_y_max=points_y[i];
				points_y_index=i;
			}		
		}
		points_y_min=points.get(points_y_index);                          //����������С�ĵ㸳ֵ����points_y_min
		List<Point> afterpointssort = new ArrayList<>();                  //�������ĵ�
		for(int i=0;i<points.size()-points_y_index;i++) {
			afterpointssort.add(points.get(points_y_index+i));			
		}
		for(int i=0;i<points_y_index;i++) {                              //����ǰ��ĵ�
			afterpointssort.add(points.get(i));	
		}
		return afterpointssort;
	} 
	
	public static Point points_y_min() {
		return points_y_min;
	}
}
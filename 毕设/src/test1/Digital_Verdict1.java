package test1;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * 指尖点到数字的判决
 * 指尖点0：数字0
 * 指尖点1:  1/9/7
 * 指尖点2：2/6/8
 * 指尖点3：  3
 * 指尖点4：  4
 * 指尖点5：  5
 * @author 王宇兵
 *
 */
public class Digital_Verdict1 {
	static Point a,b,c;
	static Point depth;
	static Point p;

	public static void FingertoNumber(List<Point> fingerTips,List<Point> fingerbuttoms,Mat m1) {		
		int number=fingerTips.size();
		
		//冒泡排序fingerTips中的点
		for(int i=0;i<fingerTips.size()-1;i++) {
			for(int j=0;j<fingerTips.size()-1-i;j++) {
				if(fingerTips.get(j).x<fingerTips.get(j+1).x) {
					p=fingerTips.get(j);
					fingerTips.set(j, fingerTips.get(j+1));
					fingerTips.set(j+1, p);										
				}				
			}
		}
		for(int i=0;i<fingerbuttoms.size()-1;i++) {          ////冒泡排序fingbuttoms中的点
			for(int j=0;j<fingerbuttoms.size()-1-i;j++) {
				if(fingerbuttoms.get(j).x<fingerbuttoms.get(j+1).x) {
					p=fingerbuttoms.get(j);
					fingerbuttoms.set(j, fingerTips.get(j+1));
					fingerbuttoms.set(j+1, p);										
				}				
			}
		}
		for(int i=0;i<fingerTips.size();i++) {
			Imgproc.circle(m1,fingerTips.get(i) , 5,new Scalar(100),  Imgproc.FILLED);
		}
		for(int i=0;i<fingerbuttoms.size();i++) { 
		    Imgproc.circle(m1,fingerbuttoms.get(i), 10,new Scalar(155),  Imgproc.FILLED);
		}
		HighGui.imshow("指尖点和指尖中的底点", m1);
		HighGui.waitKey(0);
		
		switch(number){
		case 0:
			System.out.println("数字0");
			break;	
		case 1:
		{
			a=fingerTips.get(0);
			depth=fingerbuttoms.get(0);
			double distance=Math.sqrt(Math.pow(a.x - depth.x,2)+Math.pow(a.y - depth.y,2));
			System.out.println(distance);
			if(distance>300)
				System.out.println("数字1");
			else if(distance>150&&distance<=300)
				System.out.println("数字9");
			else
				System.out.println("数字7");
			break;
		}
						
		case 2:	
		{
			a=fingerTips.get(0);
			b=fingerTips.get(1);	
			depth=fingerbuttoms.get(0);
			double dot = (a.x - depth.x ) * (b.x - depth.x) + (a.y - depth.y ) * (b.y - depth.y);         //向量內积
			double x1=Math.pow(a.x - depth.x , 2)+Math.pow(a.y - depth.y,2);
			double x2=Math.pow(b.x - depth.x , 2)+Math.pow(b.y - depth.y,2);
			double arccos=Math.acos(dot/(Math.sqrt(x1)*(Math.sqrt(x2))));    //曲率
			arccos=Math.toDegrees(arccos);
			System.out.println(arccos);
			if(arccos<=65) {
				System.out.println("数字2");
			}
			else if(arccos>65&&arccos<Math.PI/2){
				System.out.println("数字8");		
			}
			else {
				System.out.println("数字6");
			}
			break;
			}
		case 3:
			System.out.println("数字"+number);
			break;
		case 4:
			System.out.println("数字"+number);
			break;
		case 5:
			System.out.println("数字"+number);
			break;			
		}
	}
}

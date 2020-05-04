package test1;

import java.util.Vector;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * ָ��㵽���ֵ��о�
 * ָ���0������0
 * ָ���1:  1/9/7
 * ָ���2��2/6/8
 * ָ���3��  3
 * ָ���4��  4
 * ָ���5��  5
 * @author �����
 *
 */
public class Digital_Verdict {
	static Point a,b,c;
	static Point depth;
	static Point p;

	public static void FingertoNumber(Vector<Point> fingerTips,Vector<Point> fingerbuttoms,Mat m1) {		
		int number=fingerTips.size();
		
		//ð������fingerTips�еĵ�
		for(int i=0;i<fingerTips.size()-1;i++) {
			for(int j=0;j<fingerTips.size()-1-i;j++) {
				if(fingerTips.get(j).x<fingerTips.get(j+1).x) {
					p=fingerTips.get(j);
					fingerTips.set(j, fingerTips.get(j+1));
					fingerTips.set(j+1, p);										
				}				
			}
		}
		for(int i=0;i<fingerbuttoms.size()-1;i++) {          ////ð������fingbuttoms�еĵ�
			for(int j=0;j<fingerbuttoms.size()-1-i;j++) {
				if(fingerbuttoms.get(j).x<fingerbuttoms.get(j+1).x) {
					p=fingerbuttoms.get(j);
					fingerbuttoms.set(j, fingerTips.get(j+1));
					fingerbuttoms.set(j+1, p);										
				}				
			}
		}
		 
		switch(number){
		case 0:
			System.out.println(number);
			break;	
		case 1:
		{
			a=fingerTips.get(0);
			depth=fingerbuttoms.get(0);
			double distance=Math.sqrt(Math.pow(a.x - depth.x,2)+Math.pow(a.y - depth.y,2));
			System.out.println(distance);
			if(distance>200)
				System.out.println("����1");
			else if(distance>120&&distance<=200)
				System.out.println("����9");
			else
				System.out.println("����7");
			break;
		}
			
			
		case 2:	
		{
			a=fingerTips.get(0);
			b=fingerTips.get(1);	
			depth=fingerbuttoms.get(0);
			double dot = (a.x - depth.x ) * (b.x - depth.x) + (a.y - depth.y ) * (b.y - depth.y);         //�����Ȼ�
			double x1=Math.pow(a.x - depth.x , 2)+Math.pow(a.y - depth.y,2);
			double x2=Math.pow(b.x - depth.x , 2)+Math.pow(b.y - depth.y,2);
			double arccon=Math.acos(dot/(Math.sqrt(x1)*(Math.sqrt(x2))));    //����	
			System.out.println(arccon);
			if(arccon<=Math.PI/6) {
				System.out.println(number);
			}
			else if(arccon>Math.PI/6&&arccon<Math.PI/2){
				System.out.println("����8");		
			}
			else {
				System.out.println("����6");
			}
			break;
			}
		case 3:
			System.out.println(number);
			break;
		case 4:
			System.out.println(number);
			break;
		case 5:
			System.out.println(number);
			break;			
		}
		

	}
}

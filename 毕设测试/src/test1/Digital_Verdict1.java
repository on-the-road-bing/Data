package test1;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * ָ��㵽���ֵ��о�
 * ָ���0������0
 * ָ���1:  1/9/7    ����
 * ָ���2��2/6/8     �Ƕ�
 * ָ���3��  3
 * ָ���4��  4
 * ָ���5��  5
 * @author �����
 *
 */
public class Digital_Verdict1 {
	static Point a,b,c;
	static Point depth;
	static Point p;

	public static void FingertoNumber(List<Point> fingerTips,List<Point> fingerbuttoms,Mat m1) {		
		int number=fingerTips.size();		
		
		for(int i=0;i<fingerTips.size()-1;i++) {              //ð������fingerTips�еĵ�
			for(int j=0;j<fingerTips.size()-1-i;j++) {
				if(fingerTips.get(j).x<fingerTips.get(j+1).x) {
					p=fingerTips.get(j);
					fingerTips.set(j, fingerTips.get(j+1));
					fingerTips.set(j+1, p);										
				}				
			}
		}
		for(int i=0;i<fingerbuttoms.size()-1;i++) {          //ð������fingbuttoms�еĵ�
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
		HighGui.imshow("ָ����ָ���еĵ׵�", m1);
		HighGui.waitKey(0);
		
		switch(number){
		case 0:
			System.out.println("����0");
			break;	
		case 1:
		{
			a=fingerTips.get(0);
			depth=fingerbuttoms.get(0);
			double distance=Math.sqrt(Math.pow(a.x - depth.x,2)+Math.pow(a.y - depth.y,2));
			System.out.println(distance);
			if(distance>250)
				System.out.println("����1");
			else if(distance>150&&distance<=250)
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
			double arccos=Math.acos(dot/(Math.sqrt(x1)*(Math.sqrt(x2))));    //����
			arccos=Math.toDegrees(arccos);
			System.out.println(arccos);
			if(arccos<=65) {
				System.out.println("����2");
			}
			else if(arccos>65&&arccos<Math.PI/2){
				System.out.println("����8");		
			}
			else {
				System.out.println("����6");
			}
			break;
			}
		case 3:
			System.out.println("����3");
			break;
		case 4:
			System.out.println("����4");
			break;
		case 5:
			System.out.println("����5");
			break;			
		}
	}
}

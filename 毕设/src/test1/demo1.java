package test1;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.print.attribute.standard.PrinterLocation;

import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import test1.mat2BufferedImage;

public class demo1 {	
	private static final String CV_THRESH_BINARY = null;
	public static void main(String[] args) throws IOException {			
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);		// ������libname����ָ����ϵͳ��
		load_Mat();   // ����ͼ��
	}		
	public static void load_Mat() throws IOException {
		Mat src = Imgcodecs.imread("D:/bishe/img/1_1.jpg");			
		Mat dst = new Mat();
		Mat gray = new Mat();
		Mat ycrcb = new Mat();
		Mat Cr = new Mat();
		Mat hierarchy = new Mat();
		Mat m1=new Mat();
		Mat m2=new Mat();
		Mat m3=new Mat();
		
		if(src.empty()) {
			System.out.println("ͼƬ����ʧ��");
			return;
		}
		try{		  
            float width=src.width();
            float height=src.height();            
            Imgproc.resize(src, dst, new Size(width/5,height/5));                //����ͼ���С 
        }catch(Exception e){
            e.printStackTrace();
        }
		
		BufferedImage image=mat2BufferedImage.matToBufferedImage(dst);           //mat��ʽת��ΪBufferedImage��ʽ
		
		int threshold = CalculateOTsu.OTUS(image);                               //���������ֵ		
        System.out.println("�����ֵΪ��" + threshold);
        
        Imgproc.cvtColor (dst, ycrcb,Imgproc.COLOR_RGB2YCrCb) ;                  //ɫ�ʿռ�ת��  
        Vector<Mat> channels= new Vector<>();                                    // ��������Ĳ�ɫͨ������
		Core.split(ycrcb,channels); 
		Cr=channels.get(2);
           
		Imgproc.threshold(Cr, gray,threshold,255,Imgproc.THRESH_OTSU);           //����Ӧ��ֵ��ֵ��ͼ��
		HighGui gui = new HighGui();
		
		Morphological_operations.open(gray);                                     //��̬ѧ��������������
		Morphological_operations.close(gray);                                    //��̬ѧ���������ղ���
		gui.imshow("��̬ѧ����", gray);                                           //��ʾͼƬ
		HighGui.waitKey(); 	
		m1=gray.clone();
		m2=gray.clone();
		m3=gray.clone();

//		Imgproc.GaussianBlur(gray, gray,new Size(3, 3), 0, 0);                   //��˹�˲�
//		Imgproc.Canny(gray, gray, 50, 50 * 3, 3, false);                         //canny�㷨��Ե���
		m1=gray.clone();
		m2=gray.clone();
		Vector<MatOfPoint> contours=new Vector<>();                              //���������һ�� MatOfPoint����һ������,������������ List �С�
		Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_EXTERNAL , Imgproc.CHAIN_APPROX_SIMPLE);	 //��������		
		
		Point[] points=ConvexHullDetection.Convexhull(contours,gray);                           //͹������㷨
			
		Moments moment = Imgproc.moments(contours.get(0), false);                //��
		
		Point center =new Point(moment.m10/moment.m00, moment.m01/moment.m00);   //��������
		Imgproc.circle(gray, center, 8 ,new Scalar(0), Imgproc.FILLED);
		
		Hu_Similarity.HuData(moment);                                            //��Hu�ص����ƶ�
		
//		Finger_Search.Finger_Number(contours, center, m1);                       //����һ�����ʼ���㷨	
		List<Point> fingersButtoms=FingerButtom_Search.Finger_Number(contours, center, gray);	  //�ҵ�ָ����������С�ĵð��۵�
		
		List<Point> fingerTips=FingerSearch1.Finger_Number(contours, center, gray);  //�����������ʼ���㷨	
		Point  points_y_min=Contour_Point_Sort.points_y_min();
//		

//	    ���ʼ���㷨��͹������㷨�Ĺ�ͬ��	
		List<Point> Points_Contains =new ArrayList<>();
		for(int i=0;i<fingerTips.size();i++) {
			for(int j=0;j<points.length;j++) {
				if(fingerTips.get(i).x==points[j].x&&fingerTips.get(i).y==points[j].y) {
					Points_Contains.add(fingerTips.get(i));
				}
			}
		}
		System.out.println("���ʼ���㷨��͹������㷨�Ĺ�ͬ��");
		for(int i=0;i<Points_Contains.size();i++) {
			System.out.println(Points_Contains.get(i).x+" , "+Points_Contains.get(i).y);
			Imgproc.circle(m1,Points_Contains.get(i), 5,new Scalar(155),  Imgproc.FILLED);
		}
		HighGui.imshow("���ʼ���㷨��͹������㷨�Ĺ�ͬ��", m1);
		HighGui.waitKey(0);
	
		List<Point> after_point_Filter=Arm_point_Filter.filter_Algorithm(Points_Contains, center, points_y_min,m2);//�ֱ۵�ͼ��е����	
		Digital_Verdict1.FingertoNumber(after_point_Filter,fingersButtoms,m3);
	}	

}
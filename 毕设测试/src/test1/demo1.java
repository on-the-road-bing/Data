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
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);		                    // 加载由libname参数指定的系统库
		String str=GetPath.Mat_Path();
		if(str.isEmpty()) {
			 System.out.println("文件为空！！！");			 
		}
		else if(str.endsWith(".jpg")||str.endsWith(".JPG")||str.endsWith(".png")||str.endsWith(".PNG")){			
			System.out.println(str);
			load_Mat(str);			
		}
		else {
			 System.out.println("文件格式有误,请重新输入！！！");			 
		}	
		return;
	}	
		
	public static void load_Mat(String str) throws IOException {
				
//		Mat src = Imgcodecs.imread("D:/bishe/img/9.jpg");
		
		Mat src = Imgcodecs.imread(str);
		Mat dst = new Mat();
		
		
		Mat gray = new Mat();
		Mat ycrcb = new Mat();
		Mat Cr = new Mat();
		Mat hierarchy = new Mat();
		Mat m1=new Mat();
		Mat m2=new Mat();
		Mat m3=new Mat();	
		if(src.empty()) {
			System.out.println("图片加载失败");
			return;
		}
		try{		  
            float width=src.width();
            float height=src.height();            
            Imgproc.resize(src, dst, new Size(width/5,height/5));                //重置图像大小 
        }catch(Exception e){
            e.printStackTrace();
        }
		
		BufferedImage image=mat2BufferedImage.matToBufferedImage(dst);           //mat格式转换为BufferedImage格式
		
		int threshold = CalculateOTsu.OTUS(image);                               //计算最佳阈值		
        System.out.println("########################最佳阈值为：" + threshold);
        
        Imgproc.cvtColor (dst, ycrcb,Imgproc.COLOR_RGB2YCrCb) ;                  //色彩空间转换  
        Vector<Mat> channels= new Vector<>();                                    // 分离出来的彩色通道数据
		Core.split(ycrcb,channels); 
		Cr=channels.get(2);
           
		Imgproc.threshold(Cr, gray,threshold,255,Imgproc.THRESH_OTSU);           //自适应阈值二值化图像		
		Morphological_operations.open(gray);                                     //形态学操作——开操作
		Morphological_operations.close(gray);                                    //形态学操作——闭操作
		HighGui.imshow("形态学操作", gray);                                           //显示图片
		HighGui.waitKey(); 	
		
//		Imgproc.GaussianBlur(gray, gray,new Size(3, 3), 0, 0);                   //高斯滤波
//		Imgproc.Canny(gray, gray, 50, 50 * 3, 3, false);                         //canny算法边缘检测
		m1=gray.clone();
		m2=gray.clone();
		m3=gray.clone();
		Vector<MatOfPoint> contours=new Vector<>();                              //检测轮廓。一个 MatOfPoint保存一个轮廓,所有轮廓放在 List 中。
		Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_EXTERNAL , Imgproc.CHAIN_APPROX_SIMPLE);	 //查找轮廓		
		
		Point[] points=ConvexHullDetection.Convexhull(contours,gray);            //对轮廓进行凸包检测，找出凸点
			
		Moments moment = Imgproc.moments(contours.get(0), false);                //矩		
		Point center =new Point(moment.m10/moment.m00, moment.m01/moment.m00);   //轮廓重心
		Imgproc.circle(gray, center, 8 ,new Scalar(0), Imgproc.FILLED);		
		Hu_Similarity.HuData(moment);                                            //求Hu矩的相似度
		
//		Finger_Search.Finger_Number(contours, center, m1);                       //方法一、曲率检测算法			
		
		List<Point> fingerTips=FingerSearch1.Finger_Number(contours, center, gray);  //方法二、曲率检测算法筛选出指尖点           
		Point  points_y_min=Contour_Point_Sort.points_y_min();		             // 找出轮廓上纵坐标最大的点         
		List<Point> fingerbuttoms=FingerSearch1.fingerButtoms();                 //曲率检测算法筛选出指尖间的点
		List<Point> after_Fingerbuttoms_filter=Buttom_Points_filter.Filter(fingerbuttoms,center,points_y_min);   //对筛选出指尖间的点进行集中点处理


		List<Point> Points_Contains =new ArrayList<>();                         
		for(int i=0;i<fingerTips.size();i++) {                                    //找出凸包检测出的凸点和曲率检测出的点取交集
			for(int j=0;j<points.length;j++) {
				if(fingerTips.get(i).x==points[j].x&&fingerTips.get(i).y==points[j].y) {
					Points_Contains.add(fingerTips.get(i));
				}
			}
		}
		System.out.println("曲率检测算法和凸包检测算法检测出的交集");
		for(int i=0;i<Points_Contains.size();i++) {
			System.out.println("("+Points_Contains.get(i).x+" , "+Points_Contains.get(i).y+")");
			Imgproc.circle(m1,Points_Contains.get(i), 5,new Scalar(155),  Imgproc.FILLED);
		}
		System.out.println("#########################");
		HighGui.imshow("曲率检测算法和凸包检测算法检测出的交集", m1);
		HighGui.waitKey(0);
	
		List<Point> after_FingerTips_Filter=Arm_point_Filter.filter_Algorithm(Points_Contains, center, points_y_min,m2);//对交集进行手臂点和集中点过滤，结果定义为指尖点		
		Digital_Verdict1.FingertoNumber(after_FingerTips_Filter,after_Fingerbuttoms_filter,m3);                         //对定义的指尖点进行判决
	}	
}
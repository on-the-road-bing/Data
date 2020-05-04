package test1;

import java.util.Vector;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
/**
 * 
 * @author 王宇兵
 *
 */
public class Finger_Search {
	static int index = 0;
	double area, maxArea = 0;
	public static void Finger_Number(Vector<MatOfPoint> contours,Point center,Mat m1) {
		double area, maxArea = 0;
		for (int i=0; i < contours.size(); i++){                                 //查找最大轮廓
				area = Imgproc.contourArea( contours.get(i));
				if (area > maxArea){
					maxArea = area;
					index = i;
				}			
		}
		Point[] SrcMtx=contours.get(0).toArray();
		System.out.println("轮廓点Point="+SrcMtx.length);	
		Vector<Point> fingerTips=new Vector<>();
		Vector<Point> fingerButtoms=new Vector<>();
		Point fingerTips_single,p,q,r;	
		double current=0,depth=50000,mosthigher = 0;
		int m = 0,k = 0,j,notice = 0,spos = 0;
		for(int i=0;i<SrcMtx.length;i++){			 
			double pn = Math.sqrt(((SrcMtx[i].x - center.x) * (SrcMtx[i].x - center.x) + (SrcMtx[i].y - center.y) * (SrcMtx[i].y - center.y)) ); //计算重心到轮廓边缘的距离
			if (pn>=current)
			   current=pn;//找出峰值		 		   
		    else{
			   m++;
			   if(m ==1 ){
				   notice=i;
				   fingerTips_single=SrcMtx[i];
		           depth=500000;
			   }				 
			   if (depth>=pn){
		           depth=pn;
		           k++;//从峰值向下开始爬。		 
			   }
			   else{  									
				   if(k>25){              //如果爬的步长 > 20  则认为是指尖的候选点。// spos=notice;
				         if(notice<10){
					           notice= 10;	 
				         }
                         //更进一步的获取精确的指尖位置	枚举候选点周围的 10 个点 选出距离重心最大的点			
			             for ( j = notice-10;j<notice+10;j++){
				               current = Math.sqrt(((SrcMtx[j].x - center.x) * (SrcMtx[j].x - center.x) + (SrcMtx[j].y - center.y) * (SrcMtx[j].y - center.y)) );
				               if(current>mosthigher){
				                  mosthigher = current;
				                  spos = j ;	 
				               }						    
			             }
			            // System.out.println("current="+current);
			             mosthigher=0;
			             if(current>250) {
			            	if(SrcMtx[spos].y<center.y) {
			               fingerTips_single =SrcMtx[spos];
//			               System.out.println(fingerTips_single.x);
			               Point fingerButtoms_single=SrcMtx[i-1];
			               fingerTips.add(fingerTips_single);
			               fingerButtoms.add(SrcMtx[i-1]);
			               Imgproc.circle(m1,SrcMtx[spos], 10,new Scalar(0),  Imgproc.FILLED);
//			               Imgproc.circle(m1,SrcMtx[i-1], 10,new Scalar(155),  Imgproc.FILLED);
			            	}
			             }
			             

				   
			             
			             
//			                q = SrcMtx[spos-1];
//				 			p = SrcMtx[spos];
//				 			r = SrcMtx[spos+1];	
//				 			double dot = (q.x - p.x ) * (r.x - p.x) + (q.y - p.y ) * (r.y - p.y);         //向量內积
//				 			double x1=Math.pow(q.x - p.x , 2)+Math.pow(q.y - p.y,2);
//				 			double x2=Math.pow(r.x - p.x , 2)+Math.pow(r.y - p.y,2);
//				 			double con1=dot/(Math.sqrt(x1)*(Math.sqrt(x2)));    //曲率	
//				 			System.out.println("con1:"+con1);
//				 			if(con1>=0) {                                                            
//				 				double cross = (q.x - p.x ) * (r.y - p.y) - (r.x - p.x ) * (q.y - p.y); //向量外积分
//				 				if (cross > 0){                                        //凹槽点过滤
//				 					fingerTips.add(p);
//
//				 					Imgproc.circle(m1, p, 5,new Scalar(0),  Imgproc.FILLED);
//				 					Imgproc.line(m1, center, p, new Scalar(0), 2);	
//											        			            
//				 				}
//				 			}
	
			             
			             
				   
				  }
			      current=0;
			      m=0;
			      k=0;
			     
			   }
		   }			
		}
//		 Imgproc.circle(m1,fingerTips.get(0), 5,new Scalar(155),  Imgproc.FILLED);
//		 Imgproc.circle(m1,fingerTips.get(1), 15,new Scalar(155),  Imgproc.FILLED);
////		 Imgproc.circle(m1,fingerTips.get(2), 25,new Scalar(155),  Imgproc.FILLED);
////		 Imgproc.circle(m1,fingerTips.get(3), 35,new Scalar(155),  Imgproc.FILLED);
//		 Imgproc.circle(m1,fingerButtoms.get(0), 5,new Scalar(100),  Imgproc.FILLED);
//		 Imgproc.circle(m1,fingerButtoms.get(1), 15,new Scalar(100),  Imgproc.FILLED);
////		 Imgproc.circle(m1,fingerButtoms.get(2), 25,new Scalar(100),  Imgproc.FILLED);
////		 Imgproc.circle(m1,fingerButtoms.get(3), 35,new Scalar(100),  Imgproc.FILLED);

		System.out.println("检测到指尖个数为："+fingerTips.size());            //指尖点数量
		HighGui.imshow("寻找指尖", m1);HighGui.waitKey(1);	
		
		Digital_Verdict.FingertoNumber(fingerTips,fingerButtoms,m1);

		
		
		
//首先方法		
//		List<Point> SrcMtx=contours.get(0).toList();
//		System.out.println("Point:"+SrcMtx.size());	
//		Vector<Point> fingerTips=new Vector<>();
//		Point p, q, r;	
//		double dot,cross;
//		for (int i = 20; i < (SrcMtx.size()-20); i++)
//		{
//			q = SrcMtx.get(i-20);
//			p = SrcMtx.get(i);
//			r = SrcMtx.get(i+20);
//			dot = (q.x - p.x ) * (r.x - p.x) + (q.y - p.y ) * (r.y - p.y);         //向量內积
//			double x1=Math.pow(q.x - p.x , 2)+Math.pow(q.y - p.y,2);
//			double x2=Math.pow(r.x - p.x , 2)+Math.pow(r.y - p.y,2);
//			double con1=dot/(Math.sqrt(x1)*(Math.sqrt(x2)));    //曲率	
//			System.out.println("con1:"+con1);	
//			if(con1>=0) {                                                            
//				cross = (q.x - p.x ) * (r.y - p.y) - (r.x - p.x ) * (q.y - p.y); //向量外积分
//				if (cross > 0){                                        //凹槽点过滤
//					fingerTips.add(p);
//					System.out.println("con1="+con1);
//					fingerTips.add(p);
//					Imgproc.circle(m1, p, 5,new Scalar(0),  Imgproc.FILLED);
//					Imgproc.line(m1, center, p, new Scalar(0), 2);	
//					HighGui.imshow("寻找指尖", m1);HighGui.waitKey(1); 
//				}
//			}			
//		}
  	    	    
	}	
}



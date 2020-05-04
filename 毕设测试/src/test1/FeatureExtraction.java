package test1;
/**
 * 方法一、二效果相同
 */
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class FeatureExtraction {
//方法一	
	public static double MOMENTS_M(Mat src,int p,int q)        //求空间矩
	{
		src.convertTo(src, CvType.CV_32S);
        int  size = (int) (src.total() * src.channels());
        int[] temp = new int[size];
        src.get(0, 0, temp);
	       float M_sum_y = 0;
	       float M_sum_xy = 0;
	       int H,W;
	       H=src.height();
	       W=src.width();
	       for(int i=0;i<H;i++)
	       {     
	    	   for(int j=0;j<W;j++){	               
	                  M_sum_y+=temp[i*W+j]*Math.pow(j,q);
	   	              M_sum_xy+=Math.pow(i,p)*M_sum_y;
	           }
	       }
	       return M_sum_xy;
	}
	
	public static double MOMENTS_U(Mat src,int p,int q,float X0,float Y0)   //求中心矩
	{
		src.convertTo(src, CvType.CV_32S);
        int  size = (int) (src.total() * src.channels());
        int[] temp = new int[size];
        src.get(0, 0, temp);
	       float N_sum_y = 0;
	       float N_sum_xy = 0;
	       int H,W;
	       H=src.height();
	       W=src.width();
	       for(int i=0;i<H;i++) {     
	    	   for(int j=0;j<W;j++){	    		 
	    		   N_sum_y+=temp[i*W+j]*Math.pow(j-Y0,q);
	    		   N_sum_xy+=Math.pow(i-X0,p)*N_sum_y;
	           }           
	       }
	       return N_sum_xy;
	} 

	
//方法二：
//	public static float MOMENTS_M(Mat src,int p,int q)        //求空间矩
//	{
//	       double[] scl;                                      //CvScalar是一个可以用来存放4个double数值的数组。
//	       float M_sum_y = 0;
//	       float M_sum_xy = 0;
//	       int H,W;
//	       H=src.height();
//	       W=src.width();
//	       for(int i=0;i<H;i++)
//	       {     
//	    	   for(int j=0;j<W;j++){
//	                  scl=src.get(i,j);                       //单通道图像，获取的是每一个像素点的灰度值
//	                  M_sum_y+=scl[0]*Math.pow(j,q);
//	           }
//	           M_sum_xy+=Math.pow(i,p)*M_sum_y;
//	       }
//	       return M_sum_xy;
//	}
//	
//	public static float MOMENTS_U(Mat src,int p,int q,float X0,float Y0)   //求中心矩
//	{
//
//	       double[] scl;
//	       float N_sum_y = 0;
//	       float N_sum_xy = 0;
//	       int H,W;
//	       H=src.height();
//	       W=src.width();
//	       for(int i=0;i<H;i++) {     
//	    	   for(int j=0;j<W;j++){
//	    		   scl=src.get(i,j);
//	    		   N_sum_y+=scl[0]*Math.pow(j-Y0,q);
//	           }
//	           N_sum_xy+=Math.pow(i-X0,p)*N_sum_y;
//	       }
//	       return N_sum_xy;
//	} 	
	
	
}


//灰度图像像素值
class TestOpenCV{
     public static void demo( Mat src){
          src.convertTo(src, CvType.CV_32S);
          int  size = (int) (src.total() * src.channels());
          int[] temp = new int[size];
          src.get(0, 0, temp);
//          System.out.print("temp");
     }
}



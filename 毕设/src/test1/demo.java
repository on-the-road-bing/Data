package test1;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import test1.mat2BufferedImage;

public class demo {	
//	private static final String CV_THRESH_BINARY = null;
	public static void main(String[] args) throws IOException {			
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);		// ������libname����ָ����ϵͳ��
		load_Mat();                                         // ����ͼ��
	}	
	
	public static void load_Mat() throws IOException {
		Mat src = Imgcodecs.imread("D:/bishe/Test/5.jpg");			
		Mat dst = new Mat();
		Mat gray = new Mat();
		Mat ycrcb = new Mat();
		Mat Cr = new Mat();
		if(src.empty()) {
			System.out.println("ͼƬ����ʧ��");
			return;
		}
		try{		  
            float width=src.width();
            float height=src.height();            
            Imgproc.resize(src, dst, new Size(width/5,height/5));      //����ͼ���С 
        }catch(Exception e){
            e.printStackTrace();
        }
		
		//mat��ʽת��ΪBufferedImage��ʽ
		BufferedImage image=mat2BufferedImage.matToBufferedImage(dst);	
		
		int threshold = CalculateOTsu.OTUS(image);                 //���������ֵ		
        System.out.println("�����ֵΪ��" + threshold);
        
        Imgproc.cvtColor (dst, ycrcb,Imgproc.COLOR_RGB2YCrCb) ;          
        Vector<Mat> channels= new Vector<>();                      // ��������Ĳ�ɫͨ������
		Core.split(ycrcb,channels); 
		Cr=channels.get(2);
 //biaoji              
		Imgproc.threshold(Cr, gray,threshold,255,Imgproc.THRESH_OTSU);           //����Ӧ��ֵ��ֵ��ͼ��
		HighGui gui = new HighGui();
		gui.namedWindow("��ֵ��ͼ��", HighGui.WINDOW_NORMAL);
		gui.imshow("��ֵ��ͼ��", gray);                                           //��ʾͼƬ
		HighGui.waitKey(); 
		
		//���ô�����Morphological_operations���ͼ����̬ѧ����
		Morphological_operations.open(gray);                                     //������
		Morphological_operations.close(gray);                                    //�ղ���
		
//˫���˲�
//		Imgproc.bilateralFilter(src, dst, 30, 50, 15);
//		HighGui.imshow("˫���˲�", gray);
//		HighGui.waitKey(3);	
		
	    //canny�㷨��Ե���
		Imgproc.GaussianBlur(gray, gray,new Size(3, 3), 0, 0);
		Imgproc.Canny(gray, gray, 50, 50 * 3, 3, false);
		HighGui.imshow("Canny�㷨", gray);
		HighGui.waitKey(1); 
		
			
		//findContours����
//		List<MatOfPoint> contours=new ArrayList<MatOfPoint>();//��⵽��������һ�� MatOfPoint ����һ�������������������� List �С�
//		Mat hierarchy = new Mat();
//		Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_CCOMP , Imgproc.CHAIN_APPROX_SIMPLE);
//		System.out.println(contours.size());    //���һ������
//		
//		
//		Mat imageContours=Mat.zeros(gray.size(),CvType.CV_8U);		
//		Mat Contours=Mat.zeros(gray.size(),CvType.CV_8U);  //�����ҵ��ı�Ե
//	
//		for (int i = 0; i < contours.size(); i++) {
//		    //Mat temp = new Mat(src.size(), CvType.CV_8U, new Scalar(0));
//		    Imgproc.drawContours(Contours, contours,  i, new Scalar(255), -1);
//		}
//		HighGui.imshow("��������", Contours);
//		HighGui.waitKey(2); 

		
		//7��hu�����
		float M00,M01,M10,M11,M02,M20,M12,M21,M03,M30;
	    float X_,Y_;                                       
	    float U00,U02,U20,U11,U03,U30,U21,U12;
	    float N02,N20,N11,N03,N30,N12,N21;
	    float H1,H2,H3,H4,H5,H6,H7;
	       M00=FeatureExtraction.MOMENTS_M(gray,0,0);
	       M01=FeatureExtraction.MOMENTS_M(gray,0,1);
	       M10=FeatureExtraction.MOMENTS_M(gray,1,0);
	       M11=FeatureExtraction.MOMENTS_M(gray,1,1);
	       M02=FeatureExtraction.MOMENTS_M(gray,0,2);
	       M20=FeatureExtraction.MOMENTS_M(gray,2,0);
	       M12=FeatureExtraction.MOMENTS_M(gray,1,2);
	       M21=FeatureExtraction.MOMENTS_M(gray,2,1);
	       M03=FeatureExtraction.MOMENTS_M(gray,0,3);
	       M30=FeatureExtraction.MOMENTS_M(gray,3,0);
	   
	       X_=M10/M00;                 // ͼ�����������X_,Y_
	       Y_=M01/M00;
	     	   	       
	       N20=guiyihuazhongxinju(gray,2,0,X_,Y_);
	       N02=guiyihuazhongxinju(gray,0,2,X_,Y_);
	       N11=guiyihuazhongxinju(gray,1,1,X_,Y_);
	       N30=guiyihuazhongxinju(gray,3,0,X_,Y_);
	       N03=guiyihuazhongxinju(gray,0,3,X_,Y_);    
	       N12=guiyihuazhongxinju(gray,1,2,X_,Y_);
	       N21=guiyihuazhongxinju(gray,2,1,X_,Y_);	       	              

	       //hu7�������
	       H1=N20+N02;
	       H2=(N20-N02)* (N20-N02)+4*N11*N11;
	       H3=(N30-3*N12)* (N30-3*N12)+(3*N21-N03)* (3*N21-N03);
	       H4=(N30+N12)* (N30+N12)+(N21+N03)* (N21+N03);
	       H5=(N30-3*N12)*(N30+N12)*((N30+N12)*(N30+N12)-3*(N21+N03)*(N21+N03))
	              +(3*N21-N03)*(N21+N03)*(3*(N30+N12)* (N30+N12)-(N21+N03)* (N21+N03));
	       H6=(N20-N02)*((N30+N12)* (N30+N12)-(N21+N03)* (N21+N03))
	              +4*N11*(N30+N12)*(N21+N03);
	       H7=(3*N21-N03)*(N30+N12)*((N30+N12)*(N30+N12)-3*(N21+N03)*(N21+N03))
	              +(3*N12-N30)*(N21+N03)*(3*(N30+N12)* (N30+N12)- (N21+N03)* (N21+N03));
	       System.out.println(H1);
	       System.out.println(H2);
	       System.out.println(H3);
	       System.out.println(H4);
	       System.out.println(H5);
	       System.out.println(H6);
	       System.out.println(H7);
	       double[] Huarray= new double[]{H1,H2,H3,H4,H5,H6,H7};      
           try {
			Hu7Data.FilewriterData(Huarray);			
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
           double[] HuarrayB=new double[] {1.3664879952557385E-4,	1.2989585795875058E-10,	4.584672485402619E-13	,1.0761899184308366E-13,	-2.0595056660565303E-26,	1.1151015042674228E-18,	1.213628760389901E-26	}; 
           double con=Similarity.SimilarCalcu(Huarray, HuarrayB);
           System.out.println("con="+con);
           
                   	       
	}
	//��һ�����ľ�
	public static float guiyihuazhongxinju(Mat mat,int p,int q,float X_,float Y_) {
		float  Upq=FeatureExtraction.MOMENTS_U(mat,p,q,X_,Y_);        //Upq(����)
	//	float  U00=FeatureExtraction.MOMENTS_U(mat,0,0,X_,Y_);
		float  m00= FeatureExtraction.MOMENTS_M(mat,0,0);             //m00        		
		float k=(p+q)/2.0f+1;                                         //��ĸ�Ľ���k
		return (float) (Upq/(Math.pow(m00, k)));

	}	
		
	
	
	
//δ�õ�	
	public static void RGBtoYCrCb(Mat mat) { 
		Mat ycrcb = new Mat();
		Mat Cr_Img = new Mat();
		Mat Y,Cr,Cb;
		Imgproc.cvtColor (mat, ycrcb,Imgproc.COLOR_RGB2YCrCb);//����ɫ�ռ��RGBת��ΪYCrCb
		Vector<Mat> channels= new Vector<>();  // ��������Ĳ�ɫͨ������
		Core.split(ycrcb,channels);            //��ͼ��ָ�Ϊ��ͨ��
		Y = channels.get(0);//�����ȣ�Ҳ���ǻҽ�ֵ����͸��RGB�����ź��������ģ������ǽ�RGB�źŵ��ض����ֵ��ӵ�һ��
		Cr = channels.get(1);//ɫ������ӳ��RGB�����źź�ɫ������RGB�ź�����ֵ֮��Ĳ���
		Cb = channels.get(2);//���Ͷȣ���ӳ��RGB�����ź���ɫ������RGB�ź�����ֵ֮��Ĳ��졣 
		Mat otsu_Img;
		otsu_Img=Cr.clone();		
	}	
		
}


	



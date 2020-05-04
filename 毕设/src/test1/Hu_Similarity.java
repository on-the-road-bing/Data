package test1;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

/**
 * 求轮廓Hu矩的相似度
 * @author 王宇兵
 *
 */
public class Hu_Similarity {
	static Mat m2=new Mat();
	public static void HuData(Moments moment) {
		Imgproc.HuMoments(moment, m2);
	    double[] Hu=new double[7];	   
//	    System.out.println(m2.dump());
	    m2.get(0, 0,Hu);
//	    for(int i=0;i<7;i++) {
//	    	System.out.println(Hu[i]);
//	    }
	    double[] Hu1=new double[] {0.3004678026753945,0.06118083544139656, 0.0003997815512124252, 8.930724777967478e-05, 1.044919304868816e-08,1.933392393326271e-06,-1.325054375555686e-08};
	    double con=SimilarCalcu(Hu, Hu1);
        System.out.println("Hu矩相似度"+con);
	}
	
	
//	public static  double  SimilarCalcu(double[] Ha,double[] Hb ) {       //余弦相似度
//	double dbR =0; //相似度  
//	double dSigmaST =0;  
//	double dSigmaS =0;  
//	double dSigmaT =0;  
//	double temp =0;   	  
//	for(int i=0;i<7;i++)  
//	{  
//	    temp = Ha[i]*Hb[i];     
//	    dSigmaST+=temp;            //分子
//	    dSigmaS+=Math.pow(Ha[i],2);  
//	    dSigmaT+=Math.pow(Hb[i],2);  
//	}  
//	dbR = dSigmaST/(Math.sqrt(dSigmaS)*Math.sqrt(dSigmaT));     //分子/分母，求相似度
//    return dbR;
//}

public static  double  SimilarCalcu(double[] Ha,double[] Hb ) {         //欧几里得距离相似度
	double dbR =0;                                                      //相似度  		
	double temp =0;   	  
	for(int i=0;i<4;i++)   
	    temp += Math.pow(Ha[i]-Hb[i],2);     	   
	dbR = Math.sqrt(temp);                                              //求相似度
    return dbR;
}

}

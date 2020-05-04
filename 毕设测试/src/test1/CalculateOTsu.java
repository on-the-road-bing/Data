package test1;
/**
 * 基于YCrCb空间的Otsu自适应阈值算法
 */
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

public class CalculateOTsu {
	public static int  OTUS(BufferedImage image) {	
	 int[] nSumPix= new int[256];                               //每个灰度值所占像素个数                
	 float[] nProDis= new float[256];                           //每个灰度值所占总像素比例
     for (int i = 0; i < 256; i++){    	
	        nSumPix[i] = 0;
	        nProDis[i] = 0;
	 }
     int threshold = 0;                                         //阈值
	 int width = image.getWidth();
	 int height = image.getHeight();
	                                                            //计算直方图
	 for(int i=0; i<width; i++) {
		 for(int j=0; j<height; j++) {
			 int rgb = image.getRGB(i,j);
			                /*因为使用getRGB(i,j)获取的该点的颜色值是ARGB，
			                           而在实际应用中使用的是RGB，所以需要将ARGB转化成RGB，
			                           即bufImg.getRGB(i, j) & 0xFFFFFF。*/
			 int r = (rgb & 0xff0000) >> 16;
			 int g = (rgb & 0xff00) >> 8;
			 int b = (rgb & 0xff);
//		     int y = (int)(r * 0.257 + g * 0.504 + b * 0.098)+16;	//计算灰度值
//			 int cr = (int)(r * 0.439 - g *0.368 - b * 0.071)+128;  //
			 int cr = (int)(r * 0.5000 - g *0.4187 - b * 0.0813)+128; //计算每个像素的cr值
			 nSumPix[cr] ++;
		}
	}
	 
// 计算每个灰度级占图像中的概率分布
	for(int i=0; i<256; i++) {		                          // 归一化直方图
		nProDis[i] = (float)nSumPix[i] /(width * height);
	}
	
//经典ostu算法,得到前景和背景的分割	
//w0为背景像素点占整幅图像的总比例，u0为w0平均灰度，w1为前景像素点占整幅图像的比例，u1为w1平均灰度	
	float w0, w1,u0, u1, u0_temp, u1_temp,delta_temp;
    double delta_max = 0.0;
    for (int i = 0; i < 256; i++)
    {
                                                              // 初始化相关参数
        w0 = w1 = u0_temp = u1_temp = u0 = u1 = delta_temp = 0;
        for (int j = 0; j < 256; j++)
        {   
            if (j <= i)                                       //背景部分 
            {                                                 // 当前i为分割阈值，第一类总的概率  
                w0 += nProDis[j];
                u0_temp += j * nProDis[j];
            }  
            else                                              //前景部分 
            {                                                 // 当前i为分割阈值，第二类总的概率
                w1 += nProDis[j];
                u1_temp += j * nProDis[j];
            }
        }
                                                              // 分别计算各类的平均灰度 
        u0 = u0_temp / w0;                                    //第一类的平均灰度
        u1 = u1_temp / w1;                                    //第二类的平均灰度
        delta_temp = (float)(w0*w1*Math.pow((u0-u1),2));      //计算类间方差
                                                              //也可用deltaTmp = w0 * (u0 - u)*(u0 - u) + w1 * (u1 - u)*(u1 - u);        
                                                              // 依次找到最大类间方差下的阈值          
        if (delta_temp > delta_max){
            delta_max = delta_temp;
            
            threshold = i;
        }
    }
    return threshold;	
	}
}

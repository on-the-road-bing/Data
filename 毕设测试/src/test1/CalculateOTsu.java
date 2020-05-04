package test1;
/**
 * ����YCrCb�ռ��Otsu����Ӧ��ֵ�㷨
 */
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

public class CalculateOTsu {
	public static int  OTUS(BufferedImage image) {	
	 int[] nSumPix= new int[256];                               //ÿ���Ҷ�ֵ��ռ���ظ���                
	 float[] nProDis= new float[256];                           //ÿ���Ҷ�ֵ��ռ�����ر���
     for (int i = 0; i < 256; i++){    	
	        nSumPix[i] = 0;
	        nProDis[i] = 0;
	 }
     int threshold = 0;                                         //��ֵ
	 int width = image.getWidth();
	 int height = image.getHeight();
	                                                            //����ֱ��ͼ
	 for(int i=0; i<width; i++) {
		 for(int j=0; j<height; j++) {
			 int rgb = image.getRGB(i,j);
			                /*��Ϊʹ��getRGB(i,j)��ȡ�ĸõ����ɫֵ��ARGB��
			                           ����ʵ��Ӧ����ʹ�õ���RGB��������Ҫ��ARGBת����RGB��
			                           ��bufImg.getRGB(i, j) & 0xFFFFFF��*/
			 int r = (rgb & 0xff0000) >> 16;
			 int g = (rgb & 0xff00) >> 8;
			 int b = (rgb & 0xff);
//		     int y = (int)(r * 0.257 + g * 0.504 + b * 0.098)+16;	//����Ҷ�ֵ
//			 int cr = (int)(r * 0.439 - g *0.368 - b * 0.071)+128;  //
			 int cr = (int)(r * 0.5000 - g *0.4187 - b * 0.0813)+128; //����ÿ�����ص�crֵ
			 nSumPix[cr] ++;
		}
	}
	 
// ����ÿ���Ҷȼ�ռͼ���еĸ��ʷֲ�
	for(int i=0; i<256; i++) {		                          // ��һ��ֱ��ͼ
		nProDis[i] = (float)nSumPix[i] /(width * height);
	}
	
//����ostu�㷨,�õ�ǰ���ͱ����ķָ�	
//w0Ϊ�������ص�ռ����ͼ����ܱ�����u0Ϊw0ƽ���Ҷȣ�w1Ϊǰ�����ص�ռ����ͼ��ı�����u1Ϊw1ƽ���Ҷ�	
	float w0, w1,u0, u1, u0_temp, u1_temp,delta_temp;
    double delta_max = 0.0;
    for (int i = 0; i < 256; i++)
    {
                                                              // ��ʼ����ز���
        w0 = w1 = u0_temp = u1_temp = u0 = u1 = delta_temp = 0;
        for (int j = 0; j < 256; j++)
        {   
            if (j <= i)                                       //�������� 
            {                                                 // ��ǰiΪ�ָ���ֵ����һ���ܵĸ���  
                w0 += nProDis[j];
                u0_temp += j * nProDis[j];
            }  
            else                                              //ǰ������ 
            {                                                 // ��ǰiΪ�ָ���ֵ���ڶ����ܵĸ���
                w1 += nProDis[j];
                u1_temp += j * nProDis[j];
            }
        }
                                                              // �ֱ��������ƽ���Ҷ� 
        u0 = u0_temp / w0;                                    //��һ���ƽ���Ҷ�
        u1 = u1_temp / w1;                                    //�ڶ����ƽ���Ҷ�
        delta_temp = (float)(w0*w1*Math.pow((u0-u1),2));      //������䷽��
                                                              //Ҳ����deltaTmp = w0 * (u0 - u)*(u0 - u) + w1 * (u1 - u)*(u1 - u);        
                                                              // �����ҵ������䷽���µ���ֵ          
        if (delta_temp > delta_max){
            delta_max = delta_temp;
            
            threshold = i;
        }
    }
    return threshold;	
	}
}

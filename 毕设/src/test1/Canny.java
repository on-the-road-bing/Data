package test1;

import org.opencv.core.Mat;

/**
 * Canny�㷨
 * @author �����
 */
public class Canny {
	private float gaussianKernelRadius = 2f;  
    private int gaussianKernelWidth = 16;  
    private float lowThreshold = 2.5f;  
    private float highThreshold = 7.5f;  
    // image width, height  
    private int width;  
    private int height;  
    private float[] data;  
    private float[] magnitudes;
    int index;
    
    public  void CannyAlgorithm(Mat src) {
    	width = (int) src.width();  
        height = (int) src.height(); 
        byte[] grayData = new byte[src.cols()*src.rows()];
        src.get(0,0,grayData);
        for(int i=0;i<grayData.length;i++)
        	System.out.print(grayData[i]);
        
        
        

        Mat dst =Mat.zeros(src.width(), src.height(), src.type());        
        int[] outPixels = new int[width * height];  //��ŻҶ�ͼ�����ص������ֵ
   	
    	// �����˹�����  
        float kernel[][] = new float[gaussianKernelWidth][gaussianKernelWidth];  
        for(int x=0; x<gaussianKernelWidth; x++)  
        {  
            for(int y=0; y<gaussianKernelWidth; y++)  
            {  
                kernel[x][y] = gaussian(x, y, gaussianKernelRadius);  //��ͨ�������㾫�ȵľ���ˡ�
            }  
        }  
        // ��˹ģ�� -�Ҷ�ͼ��        
        int krr = (int)gaussianKernelRadius;  
        for (int row = 0; row < height; row++) {  
            for (int col = 0; col < width; col++) {  
                index = row * width + col;  
                double weightSum = 0.0;  
                double redSum = 0;  
                for(int subRow=-krr; subRow<=krr; subRow++)  
                {  
                    int nrow = row + subRow;  
                    if(nrow >= height || nrow < 0)  
                    {  
                        nrow = 0;  
                    }  
                    for(int subCol=-krr; subCol<=krr; subCol++)  
                    {  
                        int ncol = col + subCol;  
                        if(ncol >= width || ncol <=0)  
                        {  
                            ncol = 0;  
                        }  
                        int index2 = nrow * width + ncol;  
                        int tr1 = (inPixels[index2] >> 16) & 0xff;  
                        redSum += tr1*kernel[subRow+krr][subCol+krr];  
                        weightSum += kernel[subRow+krr][subCol+krr];  
                    }  
                }  
                int gray = (int)(redSum / weightSum);  
                outPixels[index] = gray;  
            }  
        }
        
        int[] outPixels = new int[width * height];  
     
        
        
        // �����ݶ�-gradient, X����Y����  
        data = new float[width * height];  
        magnitudes = new float[width * height];  
        for (int row = 0; row < height; row++) {  
            for (int col = 0; col < width; col++) {  
                index = row * width + col;  
                              
                // ����X�����ݶ�  
                float xg = (getPixel(outPixels, width, height, col, row+1) -   
                        getPixel(outPixels, width, height, col, row) +   
                        getPixel(outPixels, width, height, col+1, row+1) -  
                        getPixel(outPixels, width, height, col+1, row))/2.0f;  
                float yg = (getPixel(outPixels, width, height, col, row)-  
                        getPixel(outPixels, width, height, col+1, row) +  
                        getPixel(outPixels, width, height, col, row+1) -  
                        getPixel(outPixels, width, height, col+1, row+1))/2.0f;  
                // ���������Ƕ�  
                data[index] = hypot(xg, yg);  
                if(xg == 0)  
                {  
                    if(yg > 0)  
                    {  
                        magnitudes[index]=90;                         
                    }  
                    if(yg < 0)  
                    {  
                        magnitudes[index]=-90;  
                    }  
                }  
                else if(yg == 0)  
                {  
                    magnitudes[index]=0;  
                }  
                else  
                {  
                    magnitudes[index] = (float)((Math.atan(yg/xg) * 180)/Math.PI);                    
                }  
                // make it 0 ~ 180  
                magnitudes[index] += 90;  
            }  
        }       
        
    }
    //�������ص������ֵ
    private int getPixel(int[] inPixels, int width, int height, int col,  
            int row) {  
        if(col < 0 || col >= width)  
            col = 0;  
        if(row < 0 || row >= height)  
            row = 0;  
        int index = row * width + col;  
        return inPixels[index];  
    }
    
    //�������в�����ƽ���͵�ƽ����
    private float hypot(float x, float y) {  
        return (float) Math.hypot(x, y);  
    }  
  
   //��ά�ռ���̬�ֲ�����Ϊ
    private  float gaussian(float x, float y, float sigma) {  
        float xDistance = x*x;  
        float yDistance = y*y;  
        float sigma22 = 2*sigma*sigma;        //sigmaΪ��׼�ƽ��Ϊ����
        float sigma22PI = (float)Math.PI * sigma22;  
        return (float)Math.exp(-(xDistance + yDistance)/sigma22)/sigma22PI;  
    }  
}

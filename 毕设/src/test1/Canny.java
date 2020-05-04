package test1;

import org.opencv.core.Mat;

/**
 * Canny算法
 * @author 王宇兵
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
        int[] outPixels = new int[width * height];  //存放灰度图像像素点的像素值
   	
    	// 计算高斯卷积核  
        float kernel[][] = new float[gaussianKernelWidth][gaussianKernelWidth];  
        for(int x=0; x<gaussianKernelWidth; x++)  
        {  
            for(int y=0; y<gaussianKernelWidth; y++)  
            {  
                kernel[x][y] = gaussian(x, y, gaussianKernelRadius);  //单通道、浮点精度的卷积核。
            }  
        }  
        // 高斯模糊 -灰度图像        
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
     
        
        
        // 计算梯度-gradient, X放与Y方向  
        data = new float[width * height];  
        magnitudes = new float[width * height];  
        for (int row = 0; row < height; row++) {  
            for (int col = 0; col < width; col++) {  
                index = row * width + col;  
                              
                // 计算X方向梯度  
                float xg = (getPixel(outPixels, width, height, col, row+1) -   
                        getPixel(outPixels, width, height, col, row) +   
                        getPixel(outPixels, width, height, col+1, row+1) -  
                        getPixel(outPixels, width, height, col+1, row))/2.0f;  
                float yg = (getPixel(outPixels, width, height, col, row)-  
                        getPixel(outPixels, width, height, col+1, row) +  
                        getPixel(outPixels, width, height, col, row+1) -  
                        getPixel(outPixels, width, height, col+1, row+1))/2.0f;  
                // 计算振幅与角度  
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
    //返回像素点的像素值
    private int getPixel(int[] inPixels, int width, int height, int col,  
            int row) {  
        if(col < 0 || col >= width)  
            col = 0;  
        if(row < 0 || row >= height)  
            row = 0;  
        int index = row * width + col;  
        return inPixels[index];  
    }
    
    //返回所有参数的平方和的平方根
    private float hypot(float x, float y) {  
        return (float) Math.hypot(x, y);  
    }  
  
   //二维空间正态分布方程为
    private  float gaussian(float x, float y, float sigma) {  
        float xDistance = x*x;  
        float yDistance = y*y;  
        float sigma22 = 2*sigma*sigma;        //sigma为标准差，平方为方差
        float sigma22PI = (float)Math.PI * sigma22;  
        return (float)Math.exp(-(xDistance + yDistance)/sigma22)/sigma22PI;  
    }  
}

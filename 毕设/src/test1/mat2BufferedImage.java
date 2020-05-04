package test1;
/**
 *格式转换mat格式转换为BufferedImage格式
 */
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
public class mat2BufferedImage {
 
	public static BufferedImage matToBufferedImage(Mat matrix) {		
//		int cols=matrix.cols();                  //列数
//		int rows=matrix.rows();                  //行数
//		int elemSize=(int)matrix.elemSize();     //Mat中单个像素点所占的字节数
//		byte[] data=new byte[cols*rows*elemSize];
//		int type;        
//		matrix.get(0 ,0,data);                   //可将mat的所有数据存入data.Java版本的openCV实现mat和byte的转换主要通过get 和 put          
//		switch(matrix.channels()){               
//		//Mat::channels 通道，矩阵中的每一个矩阵元素拥有的值的个数，比如说 3 * 4 矩阵中一共 12 个元素，如果每个元素有三个值，那么就说这个矩阵是 3 通道的，即 channels = 3。常见的是一张彩色图片有红、绿、蓝三个通道。
//		case 1:
//			type=BufferedImage.TYPE_BYTE_GRAY;   //int TYPE_BYTE_GRAY 表示无符号 byte 灰度级图像）。 
//			break;
//		case 3:
//			type=BufferedImage.TYPE_3BYTE_BGR;   //TYPE_3BYTE_BGR表示一个具有 8 位 RGB 颜色分量的图像,对应于 Windows 风格的 BGR 颜色模型，具有用 3 字节存储的 Blue、Green 和 Red 三种颜色。
//			byte b;
//			for(int i=0;i<data.length;i=i+3){
//				b=data[i];
//				data[i]=data[i+2];
//				data[i+2]=b;
//			}
//			break;
//			default:
//				return null;
//				
//		}
//		BufferedImage image2=new BufferedImage(cols,rows,type);    //创建并指定宽高、图像字节灰度
//		image2.getRaster().setDataElements(0, 0,cols,rows,data);
//		return image2;
		return (BufferedImage) HighGui.toBufferedImage(matrix);		
	} 
}



/**
 * 一般来说一个图片是由一个个像素组成的
在 RGB 模型中，一个像素一般由 R、G、B 组成，每个分量是 8 位，合起来就是 24 位
所以用 int 数组就成
BufferedImage.getRaster().getDataElements 就可以获得一个 int 数组
BufferedImage.getRaster().setDataElements 就可以把一个 int 数组设置进去
*
**/

/**
 * 有时候需要将数据存入byte数组中，三通道的时候，需要声明一个数组大小为mat.rows()*mat.cols()*3，才能将mat的数据装下，
 * 通过mat.get(0,0,data)，将数据存入data，通过mat.get可以获得每个点的值
*
***/

/**
 * mat2BufferedImage.matToBufferedImage(source);这个方法是将Opencv中Mat格式的图像转换成Java Swing中的BufferedImage格式
 * 因为在Swing中只支持BufferedImage格式图像显示。
 */


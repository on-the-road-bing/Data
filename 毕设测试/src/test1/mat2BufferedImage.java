package test1;
/**
 *��ʽת��mat��ʽת��ΪBufferedImage��ʽ
 */
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
public class mat2BufferedImage {
 
	public static BufferedImage matToBufferedImage(Mat matrix) {		
//		int cols=matrix.cols();                  //����
//		int rows=matrix.rows();                  //����
//		int elemSize=(int)matrix.elemSize();     //Mat�е������ص���ռ���ֽ���
//		byte[] data=new byte[cols*rows*elemSize];
//		int type;        
//		matrix.get(0 ,0,data);                   //�ɽ�mat���������ݴ���data.Java�汾��openCVʵ��mat��byte��ת����Ҫͨ��get �� put          
//		switch(matrix.channels()){               
//		//Mat::channels ͨ���������е�ÿһ������Ԫ��ӵ�е�ֵ�ĸ���������˵ 3 * 4 ������һ�� 12 ��Ԫ�أ����ÿ��Ԫ��������ֵ����ô��˵��������� 3 ͨ���ģ��� channels = 3����������һ�Ų�ɫͼƬ�к졢�̡�������ͨ����
//		case 1:
//			type=BufferedImage.TYPE_BYTE_GRAY;   //int TYPE_BYTE_GRAY ��ʾ�޷��� byte �Ҷȼ�ͼ�񣩡� 
//			break;
//		case 3:
//			type=BufferedImage.TYPE_3BYTE_BGR;   //TYPE_3BYTE_BGR��ʾһ������ 8 λ RGB ��ɫ������ͼ��,��Ӧ�� Windows ���� BGR ��ɫģ�ͣ������� 3 �ֽڴ洢�� Blue��Green �� Red ������ɫ��
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
//		BufferedImage image2=new BufferedImage(cols,rows,type);    //������ָ����ߡ�ͼ���ֽڻҶ�
//		image2.getRaster().setDataElements(0, 0,cols,rows,data);
//		return image2;
		return (BufferedImage) HighGui.toBufferedImage(matrix);		
	} 
}



/**
 * һ����˵һ��ͼƬ����һ����������ɵ�
�� RGB ģ���У�һ������һ���� R��G��B ��ɣ�ÿ�������� 8 λ������������ 24 λ
������ int ����ͳ�
BufferedImage.getRaster().getDataElements �Ϳ��Ի��һ�� int ����
BufferedImage.getRaster().setDataElements �Ϳ��԰�һ�� int �������ý�ȥ
*
**/

/**
 * ��ʱ����Ҫ�����ݴ���byte�����У���ͨ����ʱ����Ҫ����һ�������СΪmat.rows()*mat.cols()*3�����ܽ�mat������װ�£�
 * ͨ��mat.get(0,0,data)�������ݴ���data��ͨ��mat.get���Ի��ÿ�����ֵ
*
***/

/**
 * mat2BufferedImage.matToBufferedImage(source);��������ǽ�Opencv��Mat��ʽ��ͼ��ת����Java Swing�е�BufferedImage��ʽ
 * ��Ϊ��Swing��ֻ֧��BufferedImage��ʽͼ����ʾ��
 */


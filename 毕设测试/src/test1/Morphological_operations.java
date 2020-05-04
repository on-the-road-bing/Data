package test1;
/**
 * ͼ����̬ѧ����(���������ղ���)
 */

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

public class Morphological_operations {
	   //ͼ����̬ѧ���� ������(�ȸ�ʴ������)
		public static void open(Mat gray) {	
			//Imgproc.getStructuringElement ��ȡͼƬ��̬�ṹ
			Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5), new Point(-1, -1));
			Imgproc.morphologyEx(gray, gray, Imgproc.MORPH_OPEN, kernel);
//			HighGui.imshow("ͼ����̬ѧ���� ������(�ȸ�ʴ������)", gray);
//			HighGui.waitKey(0);
		}
		
		//ͼ����̬ѧ���� �ղ���(�����ͺ�ʴ)
		public static void close(Mat gray) {		
		//	Mat image = new Mat(gray.size(), gray.type());
			//Imgproc.getStructuringElement ��ȡͼƬ��̬�ṹ����Size(,)�Ĵ�С��ȥ���۵�
			Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5), new Point(-1, -1));
			Imgproc.morphologyEx(gray, gray, Imgproc.MORPH_CLOSE, kernel);
//			HighGui.imshow("ͼ����̬ѧ���� �ղ���(�����ͺ�ʴ)", gray);
//			HighGui.waitKey(1);
		}

}

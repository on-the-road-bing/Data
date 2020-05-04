package test1;
/**
 * 图像形态学操作(开操作、闭操作)
 */

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

public class Morphological_operations {
	   //图像形态学操作 开操作(先腐蚀后膨胀)
		public static void open(Mat gray) {	
			//Imgproc.getStructuringElement 获取图片形态结构
			Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5), new Point(-1, -1));
			Imgproc.morphologyEx(gray, gray, Imgproc.MORPH_OPEN, kernel);
//			HighGui.imshow("图像形态学操作 开操作(先腐蚀后膨胀)", gray);
//			HighGui.waitKey(0);
		}
		
		//图像形态学操作 闭操作(先膨胀后腐蚀)
		public static void close(Mat gray) {		
		//	Mat image = new Mat(gray.size(), gray.type());
			//Imgproc.getStructuringElement 获取图片形态结构调整Size(,)的大小来去除污点
			Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5), new Point(-1, -1));
			Imgproc.morphologyEx(gray, gray, Imgproc.MORPH_CLOSE, kernel);
//			HighGui.imshow("图像形态学操作 闭操作(先膨胀后腐蚀)", gray);
//			HighGui.waitKey(1);
		}

}

package test1;
/**
 *将hu特征数据写入文件夹
 *将数组中的数据写入txt文件
 * @author 王宇兵
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Hu7Data {
	public static void FilewriterData(double[] arr )throws IOException  {
		
		double[] arr2 = new double[arr.length];
		File file =new File("file");  //存放数组数据的文件
		FileWriter out = new FileWriter("d:\\array.txt"); //文件写入流
		//将数组中的数据写入到文件中。每行各数据之间TAB间隔
		for(int i=0;i<arr.length;i++) {
			out.write(arr[i]+"\t");			
		}
		out.write("\r\n");
		out.close();
		
		BufferedReader in = new BufferedReader(new FileReader("d:\\array.txt"));  //
		String line;      //一行数据
		while((line = in.readLine()) != null){
			String[] temp = line.split("\t"); 
			for(int j=0;j<temp.length;j++){
				arr2[j] = Double.parseDouble(temp[j]);	
			}
		}
		in.close();
		
		//显示读取出的数组
        for(int i=0;i<arr.length;i++){
		    System.out.println(arr2[i]);
		}
		System.out.println();
	}
}	
	


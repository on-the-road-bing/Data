package test1;
/**
 *��hu��������д���ļ���
 *�������е�����д��txt�ļ�
 * @author �����
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
		File file =new File("file");  //����������ݵ��ļ�
		FileWriter out = new FileWriter("d:\\array.txt"); //�ļ�д����
		//�������е�����д�뵽�ļ��С�ÿ�и�����֮��TAB���
		for(int i=0;i<arr.length;i++) {
			out.write(arr[i]+"\t");			
		}
		out.write("\r\n");
		out.close();
		
		BufferedReader in = new BufferedReader(new FileReader("d:\\array.txt"));  //
		String line;      //һ������
		while((line = in.readLine()) != null){
			String[] temp = line.split("\t"); 
			for(int j=0;j<temp.length;j++){
				arr2[j] = Double.parseDouble(temp[j]);	
			}
		}
		in.close();
		
		//��ʾ��ȡ��������
        for(int i=0;i<arr.length;i++){
		    System.out.println(arr2[i]);
		}
		System.out.println();
	}
}	
	


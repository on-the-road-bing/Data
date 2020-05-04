package test1;

import java.io.File;

import javax.swing.JFileChooser;

public class GetPath {
	public static String Mat_Path() {
		 String filePath = null;
		 JFileChooser fileChooser = new JFileChooser("D:\\");
		 fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		 int returnVal = fileChooser.showOpenDialog(fileChooser);
		 if(returnVal == JFileChooser.APPROVE_OPTION){
		      filePath= fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的路径		     
	     }			
		 return filePath;	 
	
	}
}


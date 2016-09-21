package com.file.test;

import java.io.File;

public class FileReNameTest {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\USER\\Desktop\\HELLOAPP");
		
		new FileReNameTest().changeFileName(file);
	}
	
	public void changeFileName(File file) {
		
		//判斷是否為file
		if(file.isFile()){
			
//			String fileName = file.getName();
//			String[] fileSplit = fileName.split("\\.");
//			
//			//檢查副檔名是否為java
//			if(fileSplit.length > 0 && "java".equals(fileSplit[fileSplit.length - 1].toLowerCase())){
//				String head = file.getName().substring(0, 1);
//				String tail = file.getName().substring(1, file.getName().length());
//				String newFileName = head.toUpperCase() + tail;
//				
////				System.out.println("head = " + head);
////				System.out.println("tail = " + tail);
//				
//				System.out.println(file.getName() + " to " + newFileName);
//				file.renameTo(new File(file.getParent(), newFileName.toLowerCase()));
//				return;
//			}
			
			//做更名的動作
			System.out.println(file.getName() + " to " + file.getName().toLowerCase());
			file.renameTo(new File(file.getParent(), file.getName().toLowerCase()));
			
		}
		//否則為folder
		else{
			
			//Folder也改為小寫
			if(this.checkFolderName(file.getName())){
				file.renameTo(new File(file.getParent(), file.getName().toLowerCase()));
			}
			
			
			//列出該folder所有檔案
			File[] fileList = file.listFiles();
			
			for(File fileOrFolder : fileList){
				
				//使用遞迴
				this.changeFileName(fileOrFolder);
				
			}
			
		}
	}
	
	private boolean checkFolderName(String folderName) {
		
		if("WEB-INF".equals(folderName)){
			return false;
		}
		
		return true;
	}
}

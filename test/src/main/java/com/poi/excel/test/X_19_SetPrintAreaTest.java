package com.poi.excel.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class X_19_SetPrintAreaTest {

	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet("Sheet1");
	    //sets the print area for the first sheet
	    wb.setPrintArea(0, "$A$1:$C$2");
	    
	    //Alternatively:
	    wb.setPrintArea(
	            0, //sheet index
	            0, //start column
	            1, //end column
	            0, //start row
	            0  //end row
	    );

	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}

}

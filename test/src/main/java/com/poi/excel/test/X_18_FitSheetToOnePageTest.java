package com.poi.excel.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class X_18_FitSheetToOnePageTest {

	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet("format sheet");
	    PrintSetup ps = sheet.getPrintSetup();

	    sheet.setAutobreaks(true);

	    ps.setFitHeight((short)1);
	    ps.setFitWidth((short)1);


	    // Create various cells and rows for spreadsheet.

	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}

}

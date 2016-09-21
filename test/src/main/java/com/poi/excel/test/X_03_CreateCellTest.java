package com.poi.excel.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class X_03_CreateCellTest {

	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
		CreationHelper creationHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet();
		
		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);
		// Create a cell and put a value in it.
		Cell cell = row.createCell(0);
		cell.setCellValue(1);
		
		// Or do it on one line.
		row.createCell(1).setCellValue(1.2);
	    row.createCell(2).setCellValue(
	    		creationHelper.createRichTextString("This is a string"));
	    row.createCell(3).setCellValue(true);
	    row.createCell(4).setCellValue("test string");
	    
	    //Writing output file
	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}

}

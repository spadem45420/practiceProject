package com.poi.excel.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class X_05_WorkDifferentTypeTest {

	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet("new sheet");
	    Row row = sheet.createRow((short)2);
	    row.createCell(0).setCellValue(1.1);
	    row.createCell(1).setCellValue(new Date());
	    row.createCell(2).setCellValue(Calendar.getInstance());
	    row.createCell(3).setCellValue("a string");
	    row.createCell(4).setCellValue(true);
	    row.createCell(5).setCellType(Cell.CELL_TYPE_ERROR);

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}

}

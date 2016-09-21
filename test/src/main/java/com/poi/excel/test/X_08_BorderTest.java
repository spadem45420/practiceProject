package com.poi.excel.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class X_08_BorderTest {

	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(1);

		// Create a cell and put a value in it.
		Cell cell = row.createCell(1);
		cell.setCellValue(4);

		// Style the cell with borders all around.
		CellStyle style = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLUE.getIndex());
		style.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cell.setCellStyle(style);
		
		// Do Iterate for WorkBook
//		for (Sheet sheetTmp : wb ) {
//	        for (Row rowTmp : sheetTmp) {
//	            for (Cell cellTmp : row) {
//	                // Do something here
//	            	cellTmp.setCellStyle(style);
//	            }
//	        }
//	    }
		
		// Decide which rows to process
	    int rowStart = Math.min(15, sheet.getFirstRowNum());
	    int rowEnd = Math.max(1400, sheet.getLastRowNum());

	    for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
	       Row r = sheet.getRow(rowNum);
	       if (r == null) {
	          // This whole row is empty
	    	   System.out.println("This whole row is empty");
	          // Handle it as needed
	          continue;
	       }

	       int lastColumn = Math.max(r.getLastCellNum(), 1000);

	       for (int cn = 0; cn < lastColumn; cn++) {
	          Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
	          if (c == null) {
	             // The spreadsheet is empty in this cell
	          } else {
	             // Do something useful with the cell's contents
	          }
	       }
	    }

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

}

package com.poi.excel.test;

import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class X_09_GetCellContentsTest {

	public static void main(String[] args) {
		
		//Create sheet
		Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet("new sheet");
	    Row rowTmp = sheet.createRow((short)2);
	    rowTmp.createCell(0).setCellValue(1.1);
	    rowTmp.createCell(1).setCellValue(new Date());
	    rowTmp.createCell(2).setCellValue(Calendar.getInstance());
	    rowTmp.createCell(3).setCellValue("a string");
	    rowTmp.createCell(4).setCellValue(true);
	    rowTmp.createCell(5).setCellType(Cell.CELL_TYPE_ERROR);
	    
	    //Get sheet contents
		DataFormatter formatter = new DataFormatter();
	    Sheet sheet1 = wb.getSheetAt(0);
	    for (Row row : sheet1) {
	        for (Cell cell : row) {
	            CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
	            System.out.print(cellRef.formatAsString());
	            System.out.print(" - ");

	            // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
	            String text = formatter.formatCellValue(cell);
	            System.out.println(text);

	            // Alternatively, get the value and format it yourself
	            switch (cell.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
	                    System.out.println(cell.getRichStringCellValue().getString());
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    if (DateUtil.isCellDateFormatted(cell)) {
	                        System.out.println(cell.getDateCellValue());
	                    } else {
	                        System.out.println(cell.getNumericCellValue());
	                    }
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
	                    System.out.println(cell.getBooleanCellValue());
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
	                    System.out.println(cell.getCellFormula());
	                    break;
	                case Cell.CELL_TYPE_BLANK:
	                    System.out.println();
	                    break;
	                default:
	                    System.out.println();
	            }
	        }
	    }
	}

}

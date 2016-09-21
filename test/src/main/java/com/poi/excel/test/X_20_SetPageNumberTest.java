package com.poi.excel.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class X_20_SetPageNumberTest {

	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook(); // or new XSSFWorkbook();
	    Sheet sheet = wb.createSheet("format sheet");
	    Footer footer = sheet.getFooter();

	    footer.setRight( "Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages() );



	    // Create various cells and rows for spreadsheet.

	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}

}

package com.poi.excel.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class X_01_WorkBookCreateTest {

	public static void main(String[] args) {
		try {
//			Workbook wb = new HSSFWorkbook();
//			FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//			wb.write(fileOut);
//			fileOut.close();

			Workbook wb = new XSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

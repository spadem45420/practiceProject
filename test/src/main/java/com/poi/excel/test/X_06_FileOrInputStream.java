package com.poi.excel.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class X_06_FileOrInputStream {

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		// Use a file
		// Workbook wb = WorkbookFactory.create(new File("workbook.xls"));

		// Use an InputStream, needs more memory
		 Workbook wb = WorkbookFactory.create(new
		 FileInputStream("workbook.xls"));

		// HSSFWorkbook, File
//		NPOIFSFileSystem fs = new NPOIFSFileSystem(new File("workbook.xls"));
//		HSSFWorkbook wb = new HSSFWorkbook(fs.getRoot(), true);
//		fs.close();

		// HSSFWorkbook, InputStream, needs more memory
		// NPOIFSFileSystem fs = new NPOIFSFileSystem(new
		// FileInputStream("workbook.xls"));
		// HSSFWorkbook wb = new HSSFWorkbook(fs.getRoot(), true);

		// XSSFWorkbook, File
		// OPCPackage pkg = OPCPackage.open(new File("workbook.xls"));
		// XSSFWorkbook wb = new XSSFWorkbook(pkg);
		// pkg.close();

		// XSSFWorkbook, InputStream, needs more memory
		// OPCPackage pkg = OPCPackage.open(new
		// FileInputStream("workbook.xls"));
		// XSSFWorkbook wb = new XSSFWorkbook(pkg);
		// pkg.close();
		
		
		 Sheet sheet = wb.getSheet("new sheet");
		 Row row = sheet.getRow(2);
		 System.out.println(row.getCell(0).getNumericCellValue());
	}

}

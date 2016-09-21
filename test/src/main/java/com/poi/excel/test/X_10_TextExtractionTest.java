package com.poi.excel.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.poi.excel.official.XLS2CSVmra;

public class X_10_TextExtractionTest {

	public static void main(String[] args) throws IOException {
		InputStream inputStream = new FileInputStream("workbook.xls");
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inputStream));
		ExcelExtractor extractor = new ExcelExtractor(wb);
		
		extractor.setFormulasNotResults(true);
		extractor.setIncludeSheetNames(false);
		System.out.println(extractor.getText());
		
		//=======================================================
		
		XLS2CSVmra xls2csv = new XLS2CSVmra("workbook.xls", 0);
		xls2csv.process();
	}

}

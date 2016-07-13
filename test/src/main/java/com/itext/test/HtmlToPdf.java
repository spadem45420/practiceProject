package com.itext.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

public class HtmlToPdf {
	
	/**
	 * html2Pdf測試。
	 * 
	 * @author USER
	 * @date 2016年7月8日下午5:00:30
	 * @return void
	 * @param args 無異議參數
	 * @throws DocumentException html2pdf錯誤時報錯
	 * @throws IOException 找無該資料夾路徑時報錯 {@link PdfConstantPath.Chapter1#ch01_1}
	 */
	public static void main(String[] args) throws DocumentException, IOException {
		try {
			//取得template
			String template = new HtmlToPdf().getTemplate();
			
			OutputStream out = new FileOutputStream(PdfConstantPath.Chapter1.ch01_1);
//			OutputStream out = new FileOutputStream(HtmlToPdf.class.getResource("").getFile() + "test.pdf");
			ITextRenderer render = new ITextRenderer();
			
			//Set owner pssd & user pssd
			PDFEncryption pdfEncryption = new PDFEncryption();
			pdfEncryption.setUserPassword("123456".getBytes());
			pdfEncryption.setOwnerPassword("456789".getBytes());
			render.setPDFEncryption(pdfEncryption);


			//中文支持
			String path = HtmlToPdf.class.getResource("/").getPath() + "com/itext/template/";
			System.out.println(path);
			render.getFontResolver().addFont(path + "arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//			render.getFontResolver().addFont(path + "msjh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//			render.getFontResolver().addFont(path + "msjhbd.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//			render.getFontResolver().addFont(path + "msjhl.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//			render.getFontResolver().addFont(path + "kaiu.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			
			render.setDocumentFromString(template);
//			render.setDocument(template);
			render.layout();
			render.createPDF(out);
			render.finishPDF();
			render = null;
			out.close();
			
		} catch (com.lowagie.text.DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取的一個範例template
	 */
	private String getTemplate(){
//		Configuration cfg = new Configuration();
//		Configuration cfg = new Configuration(Configuration.getVersion());
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		Writer out = new StringWriter();
		
		Map<String, Object> templateParamMap = new HashMap<String, Object>();
		templateParamMap.put("test", "中文");
		
		try {
			cfg.setClassForTemplateLoading(this.getClass(), "/com/itext/template/");
			cfg.setDefaultEncoding("UTF-8");
//			Template template = cfg.getTemplate("test.ftl");
			Template template = cfg.getTemplate("common-html-mail-template.ftl");
			template.process(templateParamMap, out);
//			System.out.println(out.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		String inputFile = "D:/SCI/Test/itext/test.ftl";
//		String url = null;
//        try {
//			url = new File(inputFile).toURI().toURL().toString();
//			System.out.println("url = " + url);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		return url;
		
		return out.toString();
	}
}

package com.itext.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class HelloWorld {

	/** Path to the resulting PDF file. */
    public static final String RESULT
        = PdfConstantPath.Chapter1.ch01_1;
 
    /**
     * Creates a PDF file: hello.pdf
     * @param    args    no arguments needed
     */
    public static void main(String[] args)
    	throws DocumentException, IOException {
    	new HelloWorld().createPdf(RESULT);
    }
 
    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws    DocumentException 
     * @throws    IOException 
     */
    public void createPdf(String filename)
	throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        writer.setEncryption("123456".getBytes(), "456789".getBytes(),
        		PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("Hello World!"));
        // step 5
        document.close();
//        
    	
//		try {
//			OutputStream file = new FileOutputStream(new File(filename));
//			Document document = new Document();
//			PdfWriter pdfWriter = PdfWriter.getInstance(document, file);
//			pdfWriter.setEncryption("krishna".getBytes(), "testpass".getBytes(), PdfWriter.ALLOW_PRINTING,
//					PdfWriter.ENCRYPTION_AES_128);
//			document.open();
//			document.add(new Paragraph("First iText PDF"));
//			document.close();
//			file.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    
    }
}

package com.file.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class FileDownloadTest {
	
	private static String url = "http://203.67.198.13/SmartCatchFrontWeb/imageServlet.view?imageFolder=historical&imageName=20160612143021364.jpg";

	private static String file = "D:\\SCI\\Test\\download\\test.jpg";
	
	public static void saveUrl(final String filename, final String urlString) {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(filename);

			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void saveUrl2(String file, String url) {
		try {
			FileUtils.copyURLToFile(new URL(url), new File(file));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		//test 1
		FileDownloadTest.saveUrl(file, url);
		
		//test 2
//		FileDownloadTest.saveUrl2(file, url);
	}
}

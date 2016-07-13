package com.mail.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.junit.Test;

import com.sun.mail.util.MailSSLSocketFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SendMail2 {
//	public static void main(String args[]) throws UnsupportedEncodingException {
//			try {
//				new SendMail2().sendMail();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}
	
	@Test
	public void test2() {
		try {
			new SendMail2().sendMail("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>寄信的方法。</p>
	 * <p>
	 * 只是一個測試，所有的參數都寫死，只會用SCI的mail server寄信給charlie@smartcatch.com.tw
	 * </p>
	 * @author USER
	 * @date 2016年7月8日下午4:26:03
	 * @param test 無意義的參數
	 * @return void
	 * @throws Exception 
	 */
	public void sendMail(String test) throws Exception{
		List<String> toAddress = new ArrayList<String>();
		toAddress.add("charlie@smartcatch.com.tw");
		String subject = "測試測試222";
		String message = "測試";
		Message msg = this.getBaseEmailInfo(toAddress, subject);
		
		List<MimeBodyPart> attachments = new ArrayList<MimeBodyPart>();
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		File file = new File("D:/SCI/Test/測試文件.txt");
		mimeBodyPart.setFileName(MimeUtility.encodeText(file.getName()));
		System.out.println(file.getName());
		System.out.println(MimeUtility.encodeText(file.getName()));
		mimeBodyPart.attachFile(file);
		attachments.add(mimeBodyPart);
		
		// get mail-template by freemarker stuff.
		Configuration cfg = new Configuration();
		Writer out = new StringWriter();
		
		for(String serVo: toAddress) {
		
		try {
			
			cfg.setClassForTemplateLoading(this.getClass(), "/com/itext/template/");
			Template template = cfg.getTemplate("common-html-mail-template.ftl");
			
	        Map<String, String> rootMap = new HashMap<String, String>();
	        rootMap.put("test", message);
	        template.process(rootMap, out);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	  }
        // get mail-template by  freemarker stuff ends.
		
		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(out.toString(), "text/html;charset=UTF-8");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachments != null && attachments.size() > 0) {
			for (MimeBodyPart m : attachments)
				multipart.addBodyPart(m);
		}
		
		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);
		
//		send(sendMode, msg, toAddress, subject, out.toString());
	}

	/**
	 * 回傳既定格式的Message物件
	 */
	private Message getBaseEmailInfo(List<String> toAddress, String subject) throws Exception {
		if (toAddress != null && toAddress.size() > 0) {
			
			// sets SMTP server properties
			String host = "192.168.1.245";
			int port = 25;
			final String username = "mpos@smartcatch.com.tw";
			final String password = "54163424";// your password

			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", port);
			
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			};
			Session session = Session.getInstance(props, auth);

			// creates a new e-mail message
			Message msg = new MimeMessage(session);

			try {
				msg.setFrom(new InternetAddress("mpos@smartcatch.com.tw", "mpos"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			InternetAddress[] toaddressArray = new InternetAddress[toAddress.size()];
			for (int i = 0; i < toAddress.size(); i++)
				toaddressArray[i] = new InternetAddress(toAddress.get(i));
			
			msg.setRecipients(Message.RecipientType.TO, toaddressArray);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			
			return msg;
		} else {
			return null;
		}
	}
}

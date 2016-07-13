package com.mail.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMail {
	public static void main(String args[]) throws UnsupportedEncodingException {
		String host = "192.168.1.245";
		int port = 25;
		final String username = "mpos@smartcatch.com.tw";
		final String password = "54163424";// your password

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mpos@smartcatch.com.tw", "mpos"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("charlie@smartcatch.com.tw"));
			message.setSubject("測試寄信.");
			message.setText("Dear Levin, \n\n 測試 測試 測試 測試 測試 測試 email !");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);
			
			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message.toString(), "text/html;charset=UTF-8");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds attachments
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			File file = new File("D:/SCI/Test/測試文件.txt");
			mimeBodyPart.attachFile(file);
			mimeBodyPart.setFileName(MimeUtility.encodeText(file.getName()));
			multipart.addBodyPart(mimeBodyPart);
			
			// sets the multi-part as e-mail's content
			message.setContent(multipart);

			Transport.send(message);

			System.out.println("寄送email結束.");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

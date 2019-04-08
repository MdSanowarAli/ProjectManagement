package com.infoistic.projectmanagement;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	private static String USER_NAME = "razeevchocolate123"; // GMail user name (just the part before "@gmail.com")
	private static String PASSWORD = "01672505052"; // GMail password

	//private static String RECIPIENT = "juwel@infoistic.com";

	public static void sendMail(String to, String subject, String body) {
		String from = USER_NAME;
		String pass = PASSWORD;
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		
	
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[1];

			// To get the array of addresses
			for (int i = 0; i < 1; i++) {
				toAddress[i] = new InternetAddress(to);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			//message.setText(body, "text/html; charset=utf-8");
			//message.setContent(body, "text/html; charset=utf-8");
			message.setText(body, "UTF-8", "html");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Lib;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author Mr.Mic
 */
public class SendMailTLS {
 
	public  String SendMail(String to,String subject,String massage) {
 
		final String username = "simplebank72@gmail.com";
		final String password = "Simple@gmail";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(massage);
 
			Transport.send(message);
 
			return ("Massage is sended to "+to);
 
		} catch (MessagingException e) {
                    
			throw new RuntimeException(e);
		}
	}
        
        public  String BringMail(String subject,String massage) {
 
		final String username = "sirivajiragnanadahampasala@gmail.com";
		//final String password = "0756780065mic@";
                final String username2 = "ghasithalakmal@gmail.com";
		final String password2 = "0756780065micr";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username2, password2);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(username));
			message.setSubject(subject);
			message.setText(massage);
 
			Transport.send(message);
 
			return ("Massage is sended ");
 
		} catch (MessagingException e) {
                    
			throw new RuntimeException(e);
		}
	}
        
       /* public static void main(String[] args) {
        SendMailTLS sm = new SendMailTLS();
        sm.SendMail("ghasithalakmal@gmail.com", "test", "This is working.......");
    }*/
}
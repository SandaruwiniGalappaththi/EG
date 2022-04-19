package com.jersey.utility;



import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class Mailapi {

	public static boolean sendOtp(int i, String to, String value)
	{
		//Random r = new Random();
		//int i = r.nextInt(10000);
		
	             boolean output; 
		
		
		try {
			
			final String from="saradawijesinghe@gmail.com";
			final String fromPassword="sscsuppa123";
		//	String to="suppa562@gmail.com";
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			//get Session
			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				    return new PasswordAuthentication(from,fromPassword);
				    }
			});
			session.setDebug(true);
			//compose message
			
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			message.setSubject("otp");
			
			message.setContent("<h3>otp code:</h3> "+i + "<a href=''><button>"+value+"</button></a>","text/html");
			//send message
			Transport.send(message);
			System.out.println("messasge sent successfully");
			
				output=true;
		}catch (Exception e)
		{ 
			output=false;
			e.printStackTrace();
		}
		
		return output;
		
		
		
		
	}

	


	
		
	}




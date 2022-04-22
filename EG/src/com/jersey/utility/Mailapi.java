package com.jersey.utility;



import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class Mailapi {

	public static boolean sendOtp(int i, String to, String value, String txt)
	{
		//Random r = new Random();
		//int i = r.nextInt(10000);
		
	             boolean output; 
		
		
		try {
			
			final String from="egelectromaster@gmail.com";
			final String fromPassword="Saradasupun123456";
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
			message.setSubject("Welcome to EG Group");
			message.setContent( "<div style='max-width: 700px; margin:auto; border: 10px solid #ddd; padding: 50px 20px; font-size: 110%;'>"
					       		+ "<h2 style='text-align: center; text-transform: uppercase;color: teal;'>Welcome To EG Mail Serivice</h2>"
					       		+"<center><p>" +txt+ "</p>"
					       		+ "<h2>Your Otp Code:" +i
								+"</h2><h2><a href='https://courseweb.sliit.lk/login/index.php'><button style='background: crimson; text-decoration: none; color: white; padding: 10px 20px; margin: 10px 0; display: inline-block;'>" +value+ "</button></a>"
								+"</h2><c/enter>", "text/html");
					         
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




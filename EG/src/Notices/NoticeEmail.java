package Notices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NoticeEmail {
	//A common method to connect to the DB
	private Connection connect()
	 {
			Connection con = null;
	 try
	 {
		 	Class.forName("com.mysql.cj.jdbc.Driver");

		 	//Provide the correct details: DBServer/DBName, username, password
		 	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "Iloveme100%");
	 }
	 catch (Exception e){	
		 	e.printStackTrace();}
	    	return con;
	 }
	
	
	//method to send email
	public void sendNoticeMail(String recepient,String date, String topic, String desc, String person) throws MessagingException {
		Properties properties = new Properties();
		
		// email configuration
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myAccountEmail = "viruniravindi123@gmail.com";
		String password = "iloveme100%";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return  new PasswordAuthentication(myAccountEmail, password);
			}
		});
		
		Message message = prepareMessage(session, myAccountEmail, recepient,date, topic,  desc,  person);
		
		Transport.send(message);//sending message
	}
	
	
	// preparing  message of email
	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String date, String topic, String desc, String person) {		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			String txt = "<html><p style=\"background-color:red;color:white;\">Special Notice</p><p><h2> Date : "+ date + "</h2></p><h3> Topic : <b><font style=\"color:red;\">"+ topic + "  </font></b><p>  </p><b><font style=\"color:black;\">  "+desc+" </font><p> </p></b>by<b><font style=\"color:red;\"> "+person+ "</font></b></h3></html>";
			message.setSubject("Special Notice");
			message.setContent(txt,"text/html");
			return message;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 public String sendToUsers(String noticeID) { //method to send special notices to registered users as email
	  
	  String output="";
	  try {
			
			Connection con = connect();
		 	if (con == null)//checking connection
		 	{
		 		return "Error while connecting to the database for reading.";
		 	}
		 	
		 	//select all notices which had not been sent 
		 	String query = "select * from notices where noticeID='"+Integer.parseInt(noticeID)+"'"; 
		 	Statement stmt = con.createStatement(); 
		 	ResultSet rs = stmt.executeQuery(query);
		 	while(rs.next()) {
				String date = rs.getString("noticeDate"); 
				String topic = rs.getString("noticeTopic");
				String desc = rs.getString("noticeDesc"); 
				String person = rs.getString("noticePerson");
				
				//get customer information (to get customer mail)
				String query1 = "select * from customerdetails "; 
				Statement stmt1 = con.createStatement(); 
				ResultSet rs1 = stmt1.executeQuery(query1); 
			 	while(rs1.next()) {
			 		
					String customerEmail = rs1.getString("customerEmaill");
					sendNoticeMail(customerEmail,date,topic,desc,person);	
					output= "MAILS ARE SENT SUCCESSFULLY!!!";
										 
			 	}
		 	}
		 	con.close();
		 	return output;
		 	
		 	
	  }
	  catch(Exception e) {
		  return "error";
	  }
	
 }
}

package powerCutModel;

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

public class PowerCutMail {
 
	private Connection connect()
	 {
			Connection con = null;
	 try
	 {
		 	Class.forName("com.mysql.cj.jdbc.Driver");

		 	//Provide the correct details: DBServer/DBName, username, password
		 	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/powercut", "root", "sandaru@1S");
	 }
	 catch (Exception e){	
		 	e.printStackTrace();}
	    	return con;
	 }
	
	
	
	public void sendPowerCutMail(String recepient,String location1,String Starting, String ending, String Date) throws MessagingException {
		Properties properties = new Properties();
		
		// email configuration
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myAccountEmail = "sandaruwinigalappaththti@gmail.com";
		String password = "sandaru@1S";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return  new PasswordAuthentication(myAccountEmail, password);
			}
		});
		
		Message message = prepareMessage(session, myAccountEmail, recepient,location1,Starting,ending,Date);
		
		Transport.send(message);
	}
	
	
	// preparing email message
	private static Message prepareMessage(Session session, String myAccountEmail, String recepient,String location1,String Starting, String ending, String Date) {		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			String txt = "<html><p style=\"background-color:red;color:white;\">Power Cut alert - check it out!</p><p><h2>For your area "+ location1 + "</h2></p><h3> There is a power failure from <b><font style=\"color:red;\">"+ Starting + "  </font></b>to<b><font style=\"color:red;\">  "+ending+" </font></b>on<b><font style=\"color:red;\"> "+Date+ "</font></b></h3></html>";
			message.setSubject("Power Cut Schedule");
			message.setContent(txt,"text/html");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
  public String sendToRegistertedUsers() {
	  
	  Boolean status = true;
	  String place ="";
	  String output="";
	  try {
			
			Connection con = connect();
		 	if (con == null)
	 {
		 		return "Error while connecting to the database for reading.";
	 }
		 	
		 	
		 	System.out.println("yes");
		 	String query = "select * from schedules where mailsSent=false"; 
		 	Statement stmt = con.createStatement(); 
		 	ResultSet rs = stmt.executeQuery(query);
		 	while(rs.next()) {
		 		System.out.println("yes");
				String location = rs.getString("location"); 
				String start = rs.getString("start");
				String end = rs.getString("end"); 
				String onDate = rs.getString("onDate");
				//place = location;
				//output +=location;
				
				
				String query1 = "select * from consumerinfo where location='"+location+"'"; 
				Statement stmt1 = con.createStatement(); 
				ResultSet rs1 = stmt1.executeQuery(query1); 
			 	while(rs1.next()) {
					String name = rs1.getString("consumerName");
					String consumerEmail = rs1.getString("email");
					//output += consumerEmail;
					sendPowerCutMail(consumerEmail,location,start,end,onDate);	
					output= "MAILS ARE SENT SUCCESSFULLY!!!";
					
					 String query2 = "UPDATE schedules SET mailsSent=? WHERE location=?";
					 PreparedStatement preparedStmt = con.prepareStatement(query2);
					 // binding values
					 preparedStmt.setBoolean(1, status);
					 preparedStmt.setString(2, location);
					 
					 // execute the statement
					 preparedStmt.execute();
					 
					 
					
			 	}
		 	}
		 	con.close();
		 	return output;
		 	
		 	
	  }catch(Exception e) {
		  return "error";
	  }
	

  }

}
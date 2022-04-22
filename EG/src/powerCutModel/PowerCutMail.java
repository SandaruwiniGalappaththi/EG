package powerCutModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

						//database connection
						con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/powercut", "root", "sandaru@1S");
				}catch (Exception e){	
						e.printStackTrace();}
	    	return con;
	 }
	
	
	        //method for send mail
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
		
		        		Message message = prepareMessage(session, myAccountEmail, recepient,location1,Starting,ending,Date);//calling method
		
		    Transport.send(message);//sending message
	}
	
	
	// preparing email message
				private static Message prepareMessage(Session session, String myAccountEmail, String recepient,String location1,String Starting, String ending, String Date) {		
						try {
								Message message = new MimeMessage(session);
								message.setFrom(new InternetAddress(myAccountEmail));
								message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
								String txt = "<html><p style=\"background-color:red;color:white;\">Power Cut alert - check it out!</p><p><h2>For your area "+ location1 + "</h2></p><h3> There is a power failure from <b><font style=\"color:red;\">"+ Starting + "  </font></b>to<b><font style=\"color:red;\">  "+ending+" </font></b>on<b><font style=\"color:red;\"> "+Date+ "</font></b></h3></html>";
								message.setSubject("Power Cut Schedule");//set subject to email
								message.setContent(txt,"text/html");//set HTML content to body in email
								return message;
						} catch (Exception e) {
							e.printStackTrace();
						}
					return null;
				}
				
				
				
				
				
			    public String sendToRegistertedUsers() {//method to send power cut alert mails all registered users 
	  
					  Boolean status = true;
					  String output="";
					  	try {
			                
					  		Connection con = connect();
					  		if (con == null)//checking connection
							 {
								 		return "Error while connecting to the database for reading.";
							 }
		 	               //select all schedules which has not inform (by searching mailSent field false)
						 	String query = "select * from schedules where mailsSent=false"; 
						 	Statement stmt = con.createStatement(); 
						 	ResultSet rs = stmt.executeQuery(query);
						 	while(rs.next()) {
								String location = rs.getString("location"); //get details of those schedules
								String start = rs.getString("start");
								String end = rs.getString("end"); 
								String onDate = rs.getString("onDate");
				                          //get consumer information in those locations
										String query1 = "select * from consumerinfo where location='"+location+"' and alertSet=true"; 
										Statement stmt1 = con.createStatement(); 
										ResultSet rs1 = stmt1.executeQuery(query1); 
									 	while(rs1.next()) {
											int ID = rs1.getInt("ID");
											String consumerEmail = rs1.getString("email");//getting email address of those consumers
											sendPowerCutMail(consumerEmail,location,start,end,onDate);	//sent mails 
											output= "MAILS ARE SENT SUCCESSFULLY!!!";
					
														 String query2 = "UPDATE schedules SET mailsSent=? WHERE location=?";
														 PreparedStatement preparedStmt = con.prepareStatement(query2);
														 // binding values
														 preparedStmt.setBoolean(1, status);//update the status of mailSent columns 
														 preparedStmt.setString(2, location);
														 // execute the statement
														 preparedStmt.execute();
														 insertMailData(ID,location,status);//calling method to insert mail data to table
														 
			        	
									 	}
						 	}
						 	con.close();
						 	return output;
		 	
		 	
					  	}catch(Exception e) {
					  		return "error";
					  	}
	

			    }
  
  
  
			    public void insertMailData(int consumerID1,String location1,Boolean status1) {//method to insert mail related data when mail is sent
	  
			    	try
			    	{     //checking connection
			    		Connection con = connect();
			    		if (con == null)
			    		{
			    			System.out.println("Error while connecting to the database for inserting.");
			 		}
					  Date date = Calendar.getInstance().getTime();  //get current time
				      DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  //set format
				      String dateAndTime = dateFormat.format(date);
	 
				      //insert data to power cut mail alert table
				      String query3 = "insert into powercutmailalert(ID,consumerID,location,mailSent,datetime)" + " values (?, ?, ?, ?, ?)";
				      PreparedStatement preparedStmt1 = con.prepareStatement(query3);
				      // binding values
				 	    preparedStmt1.setInt(1,0);
				 		preparedStmt1.setInt(2,consumerID1);
						preparedStmt1.setString(3, location1);
						preparedStmt1.setBoolean(4, status1);
						preparedStmt1.setString(5, dateAndTime);
						// execute the statement
						preparedStmt1.executeUpdate();
		
			  }catch(Exception e) {
				  System.out.println(e);
		 }
		
	  }

}
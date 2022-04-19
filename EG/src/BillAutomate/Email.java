package BillAutomate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	private Connection connect() { 
		Connection con = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
		 
			//Provide the correct details: DBServer/DBName, user-name, password 
			String url = "jdbc:mysql://127.0.0.1:3306/customer";
			String user = "root";
			String password = "";
			con = DriverManager.getConnection(url, user, password); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return con; 
	} 
	
	
	// send mail 
	public static void sendMail(String recepient, String name, String amount) throws MessagingException {
		Properties properties = new Properties();
		
		// email configuration
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myAccountEmail = "bloomerstar@gmail.com";
		String password = "ycfgqootkkhsypbz";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return  new PasswordAuthentication(myAccountEmail, password);
			}
		});
		
		Message message = prepareMessage(session, myAccountEmail, recepient, name, amount);
		
		Transport.send(message);
	}
	
	
	// preparing email message
	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String name, String amount) {		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Electricity Bill");
			message.setText("Hello " + name + "\nYour bill amount is Rs." + amount);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	// send email to all
	public String sendEmailToAll() {
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for reading.</h4></div>"
						+ "</body></html>";
			} 
				
			 
			String query = "select * from user"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			
			// iterate through the rows in the result set
			while (rs.next()) {  
				String AccNo = rs.getString("accno"); 
				String Name = rs.getString("name"); 
				String Email = rs.getString("email"); 
				
				
				// access current date
				LocalDate currentdate = LocalDate.now();
				
				Integer Year = currentdate.getYear();
				Integer Month = currentdate.getMonthValue();
				String currentYear = Year.toString();
				String currentMonth = Month.toString();
				
				// calculate bill
				String amount = calcUnits(currentYear, currentMonth, AccNo);
				
				// Send into the sendMail()
				sendMail(Email, Name, amount);				
			} 
			
			con.close(); 
			
			// Complete the HTML table
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Mails Sent Successfully.</h4></div>"
					+ "</body></html>"; 
		} 
		catch (Exception e) { 
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Error while reading.</h4></div>"
					+ "</body></html>"; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	}
	
	
	// calculate units
	public String calcUnits(String year, String month, String accNo) {
		String output = "";
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for reading.</h4></div>"
						+ "</body></html>";
			} 
						 
			String query = "select * from calcbill where year='" +year+ "' and month='" +month+ "' and accno='" + accNo + "'"; 	
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			
			Integer previousMonth;
			String query2;
			ResultSet rs2;
			
			// iterate through the rows in the result set
			while (rs.next()) { 
				String Reading = rs.getString("reading"); 
				
				//convert month to an integer
				previousMonth = Integer.parseInt(month) - 1;
				query2 = "select * from calcbill where year='" +year+ "' and month='" + previousMonth.toString() + "' and accno='" + accNo + "'";	 
				rs2 = stmt.executeQuery(query2);
				
				while (rs2.next()) { 
					String Reading2 = rs2.getString("reading");
					
					// send to calcBill(units)
					output = calcBill(Integer.parseInt(Reading)-Integer.parseInt(Reading2));
					return output;
				}				
			} 			
			con.close(); 
		} 
		catch (Exception e) { 
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Error while reading.</h4></div>"
					+ "</body></html>"; 
			System.err.println(e.getMessage()); 
		} 
		return output;
	}
	
	
	// calculate bill
	public String calcBill(Integer units) {		
		Double amount = 0.0;
		double basic = 500.00;
		amount = basic + units*9.0;
		return amount.toString();
	}
}

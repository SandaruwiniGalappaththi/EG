package BillAutomate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
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
	BillAutomation billautomation = new BillAutomation();
	
	
	//Database Connection
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
			// access current date
			LocalDate currentdate = LocalDate.now();
			
			Integer year = currentdate.getYear();
			Month month = currentdate.getMonth(); 
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Electricity Bill");
			message.setContent("<center><h2>Hello " + name 
					+ "</h2><h4>Your bill amount is Rs." + amount 
					+ "</h4><h4>for " + month + " " + year
					+ "</h4><h3 style='color:#F6B600;'>Pay in here"
					+ "</h3><br><a href='www.google.com'>"
					+ "<button style='color:white; background-color:gray; padding:10px; border-radius:7px;'>Pay Now"
					+ "</button class='btn btn-primary'></a></center>", "text/html");
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

	
	// send email to a single person
	public String sendEmail(String accountNo) {
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
			
			String query = "select * from user where accno='" + accountNo + "'"; 
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
					output = calcBill(Integer.parseInt(Reading)-Integer.parseInt(Reading2), accNo);
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
	public String calcBill(Integer units, String accNo) {	
		String output = "";
		Double amount = 0.0;
		double perunit = 0;
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for reading.</h4></div>"
						+ "</body></html>";
			} 
				
			String query = "select * from rate"; 	
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			
			String query2 = "select type from user where accno='" +accNo+ "'"; 
			Statement stmt2 = con.createStatement(); 
			ResultSet rs2 = stmt2.executeQuery(query2);
				
			rs2.next();
			String type = rs2.getString("type");
			
			// iterate through the rows in the result set
			while (rs.next()) { 
				int isres = rs.getInt("isres");
				double basic = rs.getDouble("basic");
				double twentytofifty = rs.getDouble("twentytofifty");
				double fiftytoninty = rs.getDouble("fiftytoninty");
				double nintyabove = rs.getDouble("nintyabove");
				
				
				if(type.equals("Residential") && (isres == 1)) {
					perunit = Double.parseDouble(billautomation.perUnit("Residential"));
					if(units <= 20) {
						amount = basic + units * perunit;
					}else if(units <= 50) {
						amount = basic + 20 * perunit + ((units-20) * perunit * ((twentytofifty + 100)/100.0));
					}else if(units <= 90) {
						amount = basic + 30 * perunit + (30 * perunit * ((twentytofifty + 100)/100.0)) + ((units-50) * perunit * ((fiftytoninty + 100)/100.0));
					}else {
						amount = basic + 30 * perunit + (30 * perunit * ((twentytofifty + 100)/100.0)) + (40 * perunit * ((fiftytoninty + 100)/100.0) + ((units-90) * perunit * ((nintyabove + 100)/100.0)));
					}
					output = amount.toString();
					return output;
				}else if(type.equals("Commercial") && (isres == 0)) {
					perunit = Double.parseDouble(billautomation.perUnit("Commercial"));
					if(units <= 20) {
						amount = basic + units * perunit;
					}else if(units <= 50) {
						amount = basic + 20 * perunit + ((units-20) * perunit * ((twentytofifty + 100)/100.0));
					}else if(units <= 90) {
						amount = basic + 30 * perunit + (30 * perunit * ((twentytofifty + 100)/100.0)) + ((units-50) * perunit * ((fiftytoninty + 100)/100.0));
					}else {
						amount = basic + 30 * perunit + (30 * perunit * ((twentytofifty + 100)/100.0)) + (40 * perunit * ((fiftytoninty + 100)/100.0) + ((units-90) * perunit * ((nintyabove + 100)/100.0)));
					}
					output = amount.toString();
					return output;
				}else {
					continue;
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


	//Update Per Unit
	public String updateRates(String isres, String basic, String twentytofifty, String fiftytoninty, String nintyabove) { 
			String output = ""; 
			try { 
				Connection con = connect(); 
				if (con == null) {
					return "<html><head><title>Per Unit Page</title>"
							+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
							+ "</head><body>"
							+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for updating.</h4></div>"
							+ "</body></html>";
				} 
				
				// create a prepared statement
				String query = "UPDATE rate SET basic=?,twentytofifty=?,fiftytoninty=?,nintyabove=? WHERE isres=?"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				// binding values 
				preparedStmt.setDouble(1, Double.parseDouble(basic)); 
				preparedStmt.setDouble(2, Double.parseDouble(twentytofifty)); 
				preparedStmt.setDouble(3, Double.parseDouble(fiftytoninty)); 
				preparedStmt.setDouble(4, Double.parseDouble(nintyabove)); 
				preparedStmt.setInt(5, Integer.parseInt(isres)); 
				
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				output = "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Updated Successfully</h4></div>"
						+ "</body></html>";
			} 
			catch (Exception e) { 
				output = "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while updating</h4></div>"
						+ "</body></html>";
				System.err.println(e.getMessage()); 
			} 
			return output; 
		}
}

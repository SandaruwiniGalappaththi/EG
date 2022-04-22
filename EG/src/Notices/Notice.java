package Notices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notice {
	
	private Connection connect() 
	 { 
	 Connection con = null; 
	 try
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 
	 //Provide the correct details: DBServer/DBName, username, password 
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "Iloveme100%"); 
	 } 
	 catch (Exception e) 
	 {e.printStackTrace();} 
	 return con; 
	 } 
	
	
	
	
	
	public String inserNotice(String type,String code, String date, String topic, String desc, String person, String mail) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for inserting."; } 
	 // create a prepared statement
	 String query = " insert into notices  (`noticeID`,`noticeType`,`noticeCode`,`noticeDate`,`noticeTopic`,`noticeDesc`,`noticePerson`,`noticeMails`)" + " values (?, ?, ?, ?, ?, ?, ?,?)"; 
	 
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, type); 
	 preparedStmt.setString(3, code); 
	 preparedStmt.setString(4, date); 
	 preparedStmt.setString(5, topic);  
	 preparedStmt.setString(6, desc); 
	 preparedStmt.setString(7, person); 
	 preparedStmt.setString(8, mail); 

	 // execute the statement
	 
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Inserted successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while inserting notices"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	
	
	
	
	
	public String readNotices() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; } 
	 // Prepare the html table to be displayed
	 output = "<html><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">"
	 			+"<table border='1'><tr><th  style=\"background-color:lightgreen;\">Notice Type</th>"+
			 "<th  style=\"background-color:lightgreen;\">Notice Code</th>"+
			 "<th  style=\"background-color:lightgreen;\">Released Date</th>" +
			 "<th  style=\"background-color:lightgreen;\">Notice Tpoic</th>" +
			 "<th  style=\"background-color:lightgreen;\">Notice Description</th>" +
			 "<th  style=\"background-color:lightgreen;\">Published by</th>" +
			 "<th  style=\"background-color:lightgreen;\">Mails</th></tr>"; 
	 
	 String query = "select * from notices"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 
	 while (rs.next()) 
	 { 
	 String noticeID = Integer.toString(rs.getInt("noticeID")); 
	 
	 String noticeType = rs.getString("noticeType"); 
	 String noticeCode = rs.getString("noticeCode");
	 String noticeDate = rs.getString("noticeDate"); 
	 String noticeTopic = rs.getString("noticeTopic");
	 String noticeDesc = rs.getString("noticeDesc"); 
	 String noticePerson = rs.getString("noticePerson");
	 String noticeMails = rs.getString("noticeMails");
	 // Add into the html table
	
	 
	 output += "<td style=\"background-color:lavender;\">" + noticeType + "</td>"; 
	 output += "<td>" + noticeCode + "</td>"; 
	 output += "<td style=\"background-color:lavender;\">" + noticeDate + "</td>"; 
	 output += "<td>" + noticeTopic + "</td>"; 
	 output += "<td style=\"background-color:lavender;\">" + noticeDesc + "</td>"; 
	 output += "<td>" + noticePerson + "</td>"; 
	 output += "<td style=\"background-color:lavender;\">" + noticeMails + "</td></tr>"; 
	
	 
	 // buttons
	/* output += 
			 "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	 + "<input name='itemID' type='hidden' value='" + noticeID 
	 + "'>" + "</form></td></tr>"; 
	*/ 
	 
	 
	 } 
	 
	 con.close(); 
	 // Complete the html table
	 output += "</table></html>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the notices."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	
	
	
	
	
	public String updateNotice(String ID, String type,String code, String date, String topic, String desc, String person,String mails) 

	{ 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for updating."; } 
		 // create a prepared statement
		 String query = "UPDATE notices SET noticeType=?,noticeCode=?,noticeDate=?,noticeTopic=?,noticeDesc=?,noticePerson=?,noticeMails=?  WHERE noticeID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 // binding values
		 preparedStmt.setString(1, type); 
		 preparedStmt.setString(2, code); 
		 preparedStmt.setString(3, date); 
		 preparedStmt.setString(4, topic); 
		 preparedStmt.setString(5, desc);
		 preparedStmt.setString(6, person);  
		 preparedStmt.setString(7, mails);
		 preparedStmt.setInt(8, Integer.parseInt(ID));
		 
		// execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "Updated successfully"; 
		 } 
		 catch (Exception e) 
		 { 
		 output = "Error while updating the notice."; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 }
	
	
	
	

	
	public String deleteNotice(String noticeID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for deleting."; } 
	 // create a prepared statement
	 String query = "delete from notices where noticeID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(noticeID)); 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 
	 output = "Deleted successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while deleting the notice"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	
	

}

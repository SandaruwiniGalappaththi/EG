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
	
	public String inserNotice(String type, String date,  String desc) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for inserting."; } 
	 // create a prepared statement
	 String query = " insert into noticet  (`noticeID`,`noticeType`,`noticeDate`,`noticeDesc`)" + " values (?, ?, ?, ?)"; 
	 
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, type); 
	 preparedStmt.setString(3, date);  
	 preparedStmt.setString(4, desc); 
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
	 			+"<table border='1'><tr><th  style=\"background-color:lightgreen;\">Notice Type</th><th  style=\"background-color:lightgreen;\">Notice Date</th>" +
	 "<th  style=\"background-color:lightgreen;\">Notice Description</th>" + 
	 //"<th>Item Description</th>" +
	 "<th>Update</th><th>Remove</th></tr>"; 
	 
	 String query = "select * from noticet"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 
	 while (rs.next()) 
	 { 
	 String noticeID = Integer.toString(rs.getInt("noticeID")); 
	 
	 String noticeType = rs.getString("noticeType"); 
	// String itemPrice = Double.toString(rs.getDouble("itemPrice"));
	 String noticeDate = rs.getString("noticeDate"); 
	 String noticeDesc = rs.getString("noticeDesc"); 
	 // Add into the html table
	
	 output += "<td>" + noticeType + "</td>"; 
	 output += "<td>" + noticeDate + "</td>"; 
	 output += "<td>" + noticeDesc + "</td>"; 
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	 + "<input name='itemID' type='hidden' value='" + noticeID 
	 + "'>" + "</form></td></tr>"; 
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
	
	
	public String updateNotice(String ID, String type, String date, String desc) 

	{ 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for updating."; } 
		 // create a prepared statement
		 String query = "UPDATE noticet SET noticeType=?,noticeDate=?,noticeDesc=?  WHERE noticeID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 // binding values
		 preparedStmt.setString(1, type); 
		 preparedStmt.setString(2, date); 
		
		 preparedStmt.setString(3, desc); 
		 preparedStmt.setInt(4, Integer.parseInt(ID));
		 
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
	 String query = "delete from noticet where noticeID=?"; 
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

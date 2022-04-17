package powerCutModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Schedule {
	//A common method to connect to the DB
			private Connection connect()
			 {
					Connection con = null;
			 try
			 {
				 Class.forName("com.mysql.jdbc.Driver");

				 //Provide the correct details: DBServer/DBName, username, password
				 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/powercut", "root", "sandaru@1S");
			 }
			 catch (Exception e){	
			 		e.printStackTrace();}
			    	return con;
			 }
			
			public String insertschedule(String location1, String start1, String end1, String onDate1, String createdDate1)
			 {
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for inserting."; }
			 // create a prepared statement
			 String query = " insert into schedules(ID,location,start,end,onDate,createdDate)" + " values (?, ?, ?, ?, ?, ?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, location1);
			 preparedStmt.setString(3, start1);
			 preparedStmt.setString(4, end1);
			 preparedStmt.setString(5, onDate1);
			 preparedStmt.setString(6, createdDate1);
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Inserted successfully";
			 }
			 catch (Exception e)
			 {
			 output = "Error while inserting the item.";
			 System.err.println(e.getMessage());
			 }
			 return output;
			 }
  
			
			public String readSchedule()
			 {
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for reading."; }
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Location</th><th>Start Time</th>" +
			 "<th>End Time</th><th>On Date</th><th>Created Date</th>" +
			 "</tr>";

			 String query = "select * from schedules";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 while (rs.next())
			 {
			 String ID = Integer.toString(rs.getInt("ID"));
			 String location = rs.getString("location");
			 String start = rs.getString("start");
			 String end = rs.getString("end");
			 String onDate = rs.getString("onDate");
			 String createdDate = rs.getString("createdDate");
			 // Add into the html table
			 output += "<tr><td>" + location + "</td>";
			 output += "<td>" + start + "</td>";
			 output += "<td>" + end + "</td>";
			 output += "<td>" + onDate + "</td>";
			 output += "<td>" + createdDate + "</td>";
			 // buttons
			 output += "</tr>";
			 }
			 con.close();
			 // Complete the html table
			 output += "</table>";
			 }
			 catch (Exception e)
			 {
			 output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			 }
			 return output;
			 }
}

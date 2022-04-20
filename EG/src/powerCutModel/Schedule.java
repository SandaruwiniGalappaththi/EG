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
				 	Class.forName("com.mysql.cj.jdbc.Driver");

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
			 {           System.out.println("yju7");
						String output = "";
			 try
			 {
				 	Connection con = connect();
				 	if (con == null)
			 {
				 		return "Error while connecting to the database for reading."; }
				 	// Prepare the html table to be displayed
				 	output = "<html><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">"
				 			+ "  <table border='1'><tr><th class=\"col-sm-4\"style=\"background-color:lavender;\">Location</th><th class=\"col-sm-4\"style=\"background-color:lavender;\">Start Time</th>" +
				 				"<th class=\"col-sm-4\" style=\"background-color:lavender;\">End Time</th><th class=\"col-sm-4\" style=\"background-color:lavender;\">On Date</th>" +
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
						 output += "<tr><td class=\"col-sm-4\" style=\"background-color:lavender;\">" + location + "</td>";
						 output += "<td class=\"col-sm-4\" style=\"background-color:lavender;\">" + start + "</td>";
						 output += "<td class=\"col-sm-4\" style=\"background-color:lavender;\">" + end + "</td>";
						 output += "<td class=\"col-sm-4\" style=\"background-color:lavender;\">" + onDate + "</td>";
						 // buttons
						 output += "</tr>";
			 }
				 			con.close();
				 			// Complete the html table
				 			output += "</table></html>";
				 					
			 }
			 catch(Exception e)
			 {
				 output = "Error while reading the items.";
				 System.err.println(e.getMessage());
			 }
			 return output;
			 }
    public String readzone()
			 {
						String output = "";
			 try
			 {      System.out.println("went to zone");
				 	Connection con = connect();
				 	if (con == null)
			 {
				 		return "Error while connecting to the database for reading."; }
				 	// Prepare the html table to be displayed
				 	output = "<html><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">"
				 			+ "<table border='1'><tr>"
				 			+"<th class=\"col-sm-4\" style=\"background-color:lavender;\">zone</th>"
				 			+"<th class=\"col-sm-4\" style=\"background-color:lavender;\">zone</th></tr>";

				 		String query = "select * from zone";
				 		Statement stmt = con.createStatement();
				 		ResultSet rs = stmt.executeQuery(query);
				 		// iterate through the rows in the result set
				 			while (rs.next())
			 {
				 
				 	String zone = rs.getString("zone");
				 	String ch = rs.getString("zone_character");
				 	
						 // Add into the html table
						 output += "<tr><td class=\"col-sm-4\" style=\"background-color:lavender;\">" + zone + "</td>";
						 output += "<td class=\"col-sm-4\" style=\"background-color:lavender;\">" + ch + "</td>";
						 output += "</tr>";
			 }
				 			con.close();
				 			// Complete the html table
				 			output += "</table></html>";
				 					
			 }
			 catch(Exception e)
			 {
				 output = "Error while reading the items.";
				 System.err.println(e.getMessage());
			 }
			 return output;
			 }
			
		
			
	public String updateSchedule(String ID1,String location1,String start1,String end1,String onDate1, String createdDate1)
			{
				 String output = "";
				 try
				 {
				 Connection con = connect();
				 if (con == null)
				 {return "Error while connecting to the database for updating."; }
				 // create a prepared statement
				 String query = "UPDATE schedules SET location=?,start=?,end=?,onDate=?,createdDate=? WHERE ID=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 // binding values
				 preparedStmt.setString(1, location1);
				 preparedStmt.setString(2, start1);
				 preparedStmt.setString(3, end1);
				 preparedStmt.setString(4, onDate1);
				 preparedStmt.setString(5, createdDate1 );
				 preparedStmt.setInt(6, Integer.parseInt(ID1));
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 
				 output = "Updated successfully";
				 }
				 catch (Exception e)
				 {
				 output = "Error while updating the item.";
				 System.err.println(e.getMessage());
				 }
				 return output;
				 }
			
			
			public String deleteSchedule(String ID1)
			 {
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for deleting."; }
			 // create a prepared statement
			 String query = "delete from schedules where ID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(ID1));
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Deleted successfully";
			 }
			 catch (Exception e)
			 {
			 output = "Error while deleting the item.";
			 System.err.println(e.getMessage());
			 }
			 return output;
			 }
		
			
			
			public String read(String accNo)
			 {          System.out.println("hello");
						Boolean existschedule= false,existacc=false;
						String output = "";
			 try
			 {
				 	Connection con = connect();
				 	if (con == null)
			 {
				 		return "Error while connecting to the database for reading.";
				 		
			 }
				 	// Prepare the html table to be displayed
				 	output ="<table border='1'><tr>";
				 	System.out.println("yes");
				 	
							 
		String query = "select * from consumerinfo where accountNo='"+accNo+"'"; 
		Statement stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery(query); 
		System.out.println("yes");
		System.out.println(accNo);
		System.out.println(accNo.getClass());
		System.out.println(rs.next());
					while(rs.next()) {
						String location2 = rs.getString("location"); 
						System.out.println("nooooooooo");
						System.out.println("yes");
						existacc= true;
						
						String query1 = "select * from schedules where location='" + location2 + "'"; 
						Statement stmt1 = con.createStatement(); 
						ResultSet rs1 = stmt1.executeQuery(query1); 
				
						
						// iterate through the rows in the result set
						while(rs1.next()) {
							existschedule= true;
							String location = rs1.getString("location");
							String start = rs1.getString("start");
							String end = rs1.getString("end"); 
							String onDate = rs1.getString("onDate");	
							
						
						String query2 = "select * from zone where zone='" + location2 + "'"; 
						Statement stmt2 = con.createStatement(); 
						ResultSet rs2 = stmt2.executeQuery(query2); 
						
						// iterate through the rows in the result set
						while(rs2.next()) {
							String zonecharacter = rs2.getString("zone_character");
						 output += "<td>" + zonecharacter + "</td>"
								 +"<td>" + location + "</td>"
								 +"<td>" + start + "</td>"
								 +"<td>" + end + "</td>"
								 +"<td>" + onDate + "</td>";
						 output += "</tr>";
						 
						 
					}
					}
					}
						con.close();
				 			// Complete the html table
				 			output += "</table>";
				 			if(existacc == false) {
								return output = "INVALID ACCOUNT NUMBER!!!  PLEASE RECHECK"; 
							 }	
				 			if(existschedule == false) {
								return output = "NO SCHEDULE ADDED YET FOR YOUR AREA!!!"; 
							 }	
				 			
				 		return output;	
}
				 
			 catch(Exception e)
			 {
				 output = "Error while reading the items.";
				 System.err.println(e.getMessage());
				 return output;
			 }
			 
			 
			 
			 }
			

}

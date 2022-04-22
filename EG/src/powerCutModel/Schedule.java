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
		
						 	//database connection
						 	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/powercut", "root", "sandaru@1S");
					 }
					 catch (Exception e){	
						 	e.printStackTrace();}
					    	return con;
					 }
	
	
	
			//method for insert power cut schedules
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
		  
					
	public String readSchedule()//method for read all schedules
			 {       
						String output = "";
			 try
			 {     //checking connection
				 	Connection con = connect();
				 	if (con == null)
			 {
				 		return "Error while connecting to the database for reading."; }
				 	// Prepare the HTML table to be displayed
				 	output = "<html><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">"
				 			+ "  <table border='1'><tr><th class=\"col-sm-4\"style=\"background-color:lavender;\">Location</th><th class=\"col-sm-4\"style=\"background-color:lavender;\">Start Time</th>" +
				 				"<th class=\"col-sm-4\" style=\"background-color:lavender;\">End Time</th><th class=\"col-sm-4\" style=\"background-color:lavender;\">On Date</th>" +
				 				"</tr>";
                         //getting all schedule data from table
				 		String query = "select * from schedules";
				 		Statement stmt = con.createStatement();
				 		ResultSet rs = stmt.executeQuery(query);
				 		// iterate through the rows in the result set
				 			while (rs.next())
			 {
				 	String ID = Integer.toString(rs.getInt("ID"));//set power cut schedule values 
				 	String location = rs.getString("location");
				 	String start = rs.getString("start");
				 	String end = rs.getString("end");
					String onDate = rs.getString("onDate");
					
						 // Add into the HTML table
						 output += "<tr><td class=\"col-sm-4\" style=\"background-color:lavender;\">" + location + "</td>";
						 output += "<td class=\"col-sm-4\" style=\"background-color:lavender;\">" + start + "</td>";
						 output += "<td class=\"col-sm-4\" style=\"background-color:lavender;\">" + end + "</td>";
						 output += "<td class=\"col-sm-4\" style=\"background-color:lavender;\">" + onDate + "</td>";
						 // buttons
						 output += "</tr>";
			      }
				 			con.close();
				 			// Complete the HTML table
				 			output += "</table></html>";
				 					
			    }catch(Exception e)
			 	{
			 		output = "Error while reading the items.";
			 		System.err.println(e.getMessage());
			 	}
			 return output;
	
       }
	
	
	
    		public String readzone()//method for read zones
			 {
						String output = "";
						try
						{  //checking connection
								Connection con = connect();
								if (con == null)
			 {
									return "Error while connecting to the database for reading."; 
			 }
							 	// Prepare the HTML table to be displayed
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
							 	
									 // Add into the HTML table
									 output += "<tr><td class=\"col-sm-4\" style=\"background-color:lavender;\">" + zone + "</td>";
									 output += "<td class=\"col-sm-4\" style=\"background-color:lavender;\">" + ch + "</td>";
									 output += "</tr>";
						 }
				 			con.close();
				 			// Complete the HTML table
				 			output += "</table></html>";
				 					
			     }catch(Exception e)
			       {
				      output = "Error while reading the items.";
				      System.err.println(e.getMessage());
			       }
			     return output;
			 }
			
		
			
    		public String updateSchedule(String ID1,String location1,String start1,String end1,String onDate1, String createdDate1)//method for update schedules
			{
				 	String output = "";
				 		try
				 {
				 				Connection con = connect();
				 				if (con == null)
				 				{	
				 					return "Error while connecting to the database for updating."; 
				 				}
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
				    }catch (Exception e)
				     {
					 	output = "Error while updating the item.";
					 	System.err.println(e.getMessage());
				      }
				return output;
		
			}
	
	
			
			
			public String deleteSchedule(String ID1)//method for delete schedules
			 {
					String output = "";
						try
						{
								Connection con = connect();
								if (con == null)
								{
									return "Error while connecting to the database for deleting."; 
									}
							 // create a prepared statement
							 String query = "delete from schedules where ID=?";
							 PreparedStatement preparedStmt = con.prepareStatement(query);
							 // binding values
							 preparedStmt.setInt(1, Integer.parseInt(ID1));
							 // execute the statement
							 preparedStmt.execute();
							 con.close();
							 output = "Deleted successfully";
						}catch (Exception e)
			    
						
						{
				 output = "Error while deleting the item.";
				 System.err.println(e.getMessage());
					}
			 return output;
			 }
		
			

			public boolean findLocation(String location) {//method for check entered location is a valid location or not
				// TODO Auto-generated method stub
					try
				 		{//check connection
					 		Connection con = connect();
					 		if (con == null)
					 	{
					 		return false;
					 		
					 	}
				            //get location as same as entered location
							String query2 = "select * from zone where zone='" + location + "'"; 
							Statement stmt2 = con.createStatement(); 
							ResultSet rs2 = stmt2.executeQuery(query2); 
							
							// checking entered location is available
							while(rs2.next()) {
								return true;
							
						      }
			

				 		}catch(Exception e) {
	 
				
				
		    }
				return false;
				
        }
}

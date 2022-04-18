package display;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayM {
	
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
/*public String read(String accNo)
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
				
				
		*/
				
				
public String read(String acc) {
	
	System.out.println("hello");
	Boolean existschedule= false,existacc=false;
	String output = "";
	String ot = "";
	String rs2ok = "";
	String rs3ok = "";
	String letter= "";
	
	try {
		
		Connection con = connect();
	 	if (con == null)
 {
	 		return "Error while connecting to the database for reading.";
	 		
 }
	 	output ="<table border='1'><tr>"
	 			+"<th>zone</th>"
	 			+"<th>location</th>"
	 			+"<th>start</th>"
	 			+"<th>end</th>"
	 			+"<th>date</th></tr><tr>";
	 	System.out.println("yes");
	 	String query = "select * from consumerinfo where accountNo='"+acc+"'"; 
	 	Statement stmt = con.createStatement(); 
	 	ResultSet rs = stmt.executeQuery(query);
	 	while(rs.next()) {
			String location2 = rs.getString("location"); 
	 	    ot = location2;
	 	    existacc= true;
	 	    
	 	    
	 	    
	 	String query1 = "select * from schedules where location='"+location2+"'"; 
		Statement stmt1 = con.createStatement(); 
		ResultSet rs1 = stmt1.executeQuery(query1); 
	 	while(rs1.next()) {
			existschedule= true;
			String location = rs1.getString("location");
			String start = rs1.getString("start");
			String end = rs1.getString("end"); 
			String onDate = rs1.getString("onDate");	
	 	
	 
			String query2 = "select * from zone where zone='"+location2+"'"; 
			Statement stmt2 = con.createStatement(); 
			ResultSet rs2 = stmt2.executeQuery(query2); 
			while(rs2.next()) {
			String zonecharacter = rs2.getString("zone_character");
			letter = zonecharacter;
			System.out.println("pass");
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
		output += "</table>";
	 	String ou = rs2ok +ot+existacc+existschedule+rs3ok+letter;
	 	if(existacc == false) {
			return output = "INVALID ACCOUNT NUMBER!!!  PLEASE RECHECK"; 
		 }	
			if(existschedule == false) {
			return output = "NO SCHEDULE ADDED YET FOR YOUR AREA!!!"; 
		 }	
			
		return output;
	
	}catch(Exception e) {
		output = "Error while reading the items.";
		 System.err.println(e.getMessage());
		 return output;
	}

}



public String readByZone(String zoneLetter) {
	
	System.out.println("hello");
	Boolean existschedule= false,existzone=false;
	String output = "";
	String ot = "";
	
	
	try {
		
		Connection con = connect();
	 	if (con == null)
 {
	 		return "Error while connecting to the database for reading.";
	 		
 }
	 	output ="<table border='1'><tr>"
	 			+"<th>zone</th>"
	 			+"<th>location</th>"
	 			+"<th>start</th>"
	 			+"<th>end</th>"
	 			+"<th>date</th></tr><tr>";
	 	System.out.println("yes");
	
	String query = "select * from zone where zone_character='"+zoneLetter+"'"; 
 	Statement stmt = con.createStatement(); 
 	ResultSet rs = stmt.executeQuery(query);
 	while(rs.next()) {
		String zone = rs.getString("zone"); 
		existzone = true;
	    ot = zone;
	    
	    
	    String query1 = "select * from schedules where location='"+zone+"'"; 
		Statement stmt1 = con.createStatement(); 
		ResultSet rs1 = stmt1.executeQuery(query1); 
	 	while(rs1.next()) {
			existschedule= true;
			String location = rs1.getString("location");
			String start = rs1.getString("start");
			String end = rs1.getString("end"); 
			String onDate = rs1.getString("onDate");	
			System.out.println("pass");
			 output += "<td>" + zoneLetter + "</td>"
					 +"<td>" + location + "</td>"
					 +"<td>" + start + "</td>"
					 +"<td>" + end + "</td>"
					 +"<td>" + onDate + "</td>";
			  output += "</tr>";
			 
			
			
	 	}
 	
	}
 	con.close();
	output += "</table>";
	if(existzone == false) {
		return output = "REQUESTED ZONE IS NOT AN AVAILABLE ZONE!!!"; 
	 }	
		if(existschedule == false) {
		return output = "NO SCHEDULE ADDED YET FOR YOUR AREA!!!"; 
	 }	
	return output;
}catch(Exception e) {
	return "";
    }
  }

public String readByLocation(String loc) {
	// TODO Auto-generated method stub
	
	String output = "";
	String ot = "";
	String ot2 = "";
	Boolean existschedule= false, existlocation=false;
	try {
		
		Connection con = connect();
	 	if (con == null)
 {
	 		return "Error while connecting to the database for reading.";
	 		
 }
	 	output ="<table border='1'><tr>"
	 			+"<th>zone</th>"
	 			+"<th>location</th>"
	 			+"<th>start</th>"
	 			+"<th>end</th>"
	 			+"<th>date</th></tr><tr>";
	 	System.out.println("yes");
	 	
	 	String query = "select * from zone where zone='"+loc+"'"; 
	 	Statement stmt = con.createStatement(); 
	 	ResultSet rs = stmt.executeQuery(query);
	 	while(rs.next()) {
			String zoneLetter = rs.getString("zone_character"); 
			existlocation = true;
	String query1 = "select * from schedules where location='"+loc+"'"; 
 	Statement stmt1 = con.createStatement(); 
 	ResultSet rs1 = stmt1.executeQuery(query1);
 	while(rs1.next()) {
 		existschedule= true;
		String location = rs1.getString("location");
		String start = rs1.getString("start");
		String end = rs1.getString("end"); 
		String onDate = rs1.getString("onDate");	
		System.out.println("pass");
		 output += "<td>" + zoneLetter + "</td>"
				 +"<td>" + location + "</td>"
				 +"<td>" + start + "</td>"
				 +"<td>" + end + "</td>"
				 +"<td>" + onDate + "</td>";
		  output += "</tr>";
		  
		  
 	      }
     }
	 	con.close();
		output += "</table>";
		if(existlocation== false) {
			return output = "REQUESTED LOCATION IS NOT AN AVAILABLE LOCATION!!!"; 
	 }	
		if(existschedule == false) {
			return output = "NO SCHEDULE ADDED YET FOR REQUESTED AREA!!!"; 
	 }	
			ot = " " + existlocation + existschedule;
		return output;

}catch(Exception e) {
	return "";
}
}

}

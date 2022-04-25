package com.jersey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.jersey.dbconn.DbConnectionProvider;

public class UserDelete {

		public String deleteUser(String accountNo) { 
			
			String output = ""; 
			
			try {   
				    //create db connection
					Connection con = DbConnectionProvider.getConnection(); 
					if (con == null) 
					{return "Error while connecting to the database for deleting."; } 
					
					// create a prepared statement
					String query = "delete from user where accountNO=?"; 
					PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
					// binding values
	 
					preparedStmt.setString(1, accountNo);                 
	 
					// execute the statement
					preparedStmt.execute(); 
					con.close(); 
					output ="<html><head><title>User Profile</title>"
							+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
							+ "</head><body>"
							+ "<div class='card'><h4 class='text-center'>Deleted successfully</h4></div>"
							+ "</body></html>"; 
			  
			
				} catch (Exception e) { 
					
					output ="<html><head><title>User Profile</title>"
							+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
							+ "</head><body>"
							+ "<div class='card'><h4 class='text-center'>Invalid Account Number</h4></div>"
							+ "</body></html>"; 
			   
							System.err.println(e.getMessage()); 
						} 
	
			return output; 
	 
		 }
	
  }

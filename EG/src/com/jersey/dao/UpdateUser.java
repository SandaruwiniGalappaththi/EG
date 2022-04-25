package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import com.jersey.dbconn.DbConnectionProvider;
public class UpdateUser {

	public String updateUser(String name, String email, String password, String accountNo) { 
	 
			String output = ""; 
			try { 
					Connection con = DbConnectionProvider.getConnection(); 
					if (con == null) 
					{return "Error while connecting to the database for updating."; } 
					
					// create a prepared statement
					String query = "UPDATE user SET name=?,email=?,password=? WHERE accountNo=?"; 
					PreparedStatement preparedStmt = con.prepareStatement(query); 
					
					// binding values
					preparedStmt.setString(1, name); 
					preparedStmt.setString(2, email); 
					preparedStmt.setString(3, password); 
					preparedStmt.setInt(4, Integer.parseInt(accountNo)); 
					
					// execute the statement
					preparedStmt.execute(); 
					con.close(); 
					output = "<html><head><title>User Profile</title>"
							+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
							+ "</head><body>"
							+ "<div class='card'><h4 class='text-center'>Updated successfully</h4></div>"
							+ "</body></html>";		 
			
					} catch (Exception e) { 
						output ="<html><head><title>User Profile</title>"
								+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
								+ "</head><body>"
								+ "<div class='card'><h4 class='text-center'>Account Number Is Invalid.</h4></div>"
								+ "</body></html>"; 
			  
			 
								System.err.println(e.getMessage()); 
					} 
						
						return output; 			
		  }

   }


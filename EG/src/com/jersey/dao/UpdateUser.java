package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jersey.dbconn.DbConnectionProvider;

public class UpdateUser {

	public String updateUser(String name, String email, String password, String mobile,String otp) 
	 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = DbConnectionProvider.getConnection(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	 // create a prepared statement
	 String query = "UPDATE user SET name=?,email=?,password=?,mobile=? WHERE otp=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setString(1, name); 
	 preparedStmt.setString(2, email); 
	 preparedStmt.setString(3, password); 
	 preparedStmt.setString(4, mobile); 
	 preparedStmt.setInt(5, Integer.parseInt(otp));
 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Updated successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while updating the user."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
}
}

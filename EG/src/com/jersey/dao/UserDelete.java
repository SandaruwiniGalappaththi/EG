package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jersey.dbconn.DbConnectionProvider;

public class UserDelete {

	
	public String deleteUser(String otp) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = DbConnectionProvider.getConnection(); 
	 if (con == null) 
	 {return "Error while connecting to the database for deleting."; } 
	 // create a prepared statement
	 String query = "delete from user where otp=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 
	 preparedStmt.setInt(1, Integer.parseInt(otp));                 
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
	
}

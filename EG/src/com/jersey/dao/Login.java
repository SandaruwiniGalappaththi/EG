package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import org.json.JSONArray;
import org.json.JSONObject;
import com.jersey.dbconn.DbConnectionProvider;
import Login.LoginBean;

public class Login {

		public static String login(LoginBean loginBean) {
		
		//create db connection	
		Connection con =DbConnectionProvider.getConnection();
		String output= "";
		
		
		try {
			
			        //build a table
			output = "<html><head><title>User Profile</title>"
					+ "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">"
					+ "</head><body><table class='table' border='2'><tr>"
					+ "<th>Name</th>"
					+ "<th>Email</th>" 
					+ "<th>Password</th>" 
					+ "<th>Account Number</th>" 
					+ "<th>Update Account</th>"
					+ "<th>Remove Account</th></tr>"; 
			
			//cretae a prepared statement
			PreparedStatement ps=con.prepareStatement("select * from user where email=? and password=?");
			ps.setString(1,loginBean.getEmail());
			ps.setString(2,loginBean.getPassword());
			ResultSet rs = ps.executeQuery(); //execute the statement
			if(rs.next()) {
				
					String status = rs.getString("status");
					if(status.equals("Inactive")) {
						
						return "Please Verify Your Otp Number.";
					}
					else {
						  
						 String name = rs.getString("name"); 
						 String email = rs.getString("email"); 
						 String password = rs.getString("password") ; 
						 String accountNo = rs.getString("accountNo"); 
						 String otp = Integer.toString(rs.getInt("otp"));
						
						
						// Add into the html table					 
						 output += "<tr><td>" + name + "</td>"; 
						 output += "<td>" + email + "</td>"; 
						 output += "<td>" + password + "</td>"; 
						 output += "<td>" + accountNo + "</td>"; 
						 
						// buttons
						 output +="<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
								+ "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
								+ "<td><input name='btnRemove' type='button' value='Remove' class='btn btn-secondary'></td>"
								+ "<input name='otp' type='hidden' value='" + otp + "'>" + "</form></td></tr>";
						
						
						 PreparedStatement ps1 = con.prepareStatement("update user set status=? where email=?");
					
						 ps1.setString(1,"Logged");
						 ps1.setString(2, loginBean.getEmail());
						 int i =ps1.executeUpdate(); //execute the satement
						 if(i>0) {
							 
							 return ""+output + "</table>";
							}
						
						
					}
				
			} 
		    else 
			{
				return "fail";
			}
			
			
		
			} 
			catch (Exception e ) {
			//TODO: handle exception
			e.printStackTrace();
		}
		
		return "fail";		 
	}
}	
	
	

		

	

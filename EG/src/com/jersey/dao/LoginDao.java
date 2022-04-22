package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;


import com.jersey.dbconn.DbConnectionProvider;

import Login.LoginBean;

public class LoginDao {

	public static String login(LoginBean loginBean) 
	{
		Connection con =DbConnectionProvider.getConnection();
		String output= "";
		
		
		try {
			
			
			output = "<html><head><title>User Profile</title>"
					+ "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">"
					+ "</head><body><table class='table' border='2'><tr>"
					+ "<th>Name</th>"
					+ "<th>Email</th>" 
					+ "<th>Password</th>" 
					+ "<th>Mobile</th>" 
					+ "<th>Update Account</th>"
					+ "<th>Remove Account</th></tr>"; 
			
			PreparedStatement ps=con.prepareStatement("select * from user where email=? and password=?");
			ps.setString(1,loginBean.getEmail());
			ps.setString(2,loginBean.getPassword());
			ResultSet rs = ps.executeQuery();
			System.out.println(loginBean.getEmail());
			System.out.println(loginBean.getPassword());
			
			if(rs.next())
			{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("name",rs.getString(1));
				jsonObject2.put("email",rs.getString(2));
				jsonObject2.put("password",rs.getString(3));
				jsonObject2.put("mobile",rs.getString(4));
				JSONArray jsonArray = new JSONArray();
				jsonArray.put(jsonObject2);
				
				  
				 String name = rs.getString("name"); 
				 String email = rs.getString("email"); 
				 String password = rs.getString("password") ; 
				 String mobile = rs.getString("mobile");
				 String otp = Integer.toString(rs.getInt("otp"));
				
				
				// Add into the html table
				 output += "<tr><td>" + name + "</td>"; 
				 output += "<td>" + email + "</td>"; 
				 output += "<td>" + password + "</td>"; 
				 output += "<td>" + mobile + "</td>"; 
				// buttons
				 output += "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">"
						+ "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
				// + "<td><form method='post' action=''>"
				 + "<td><input name='btnRemove' type='button' value='Remove' class='btn btn-secondary'></td>"
				 + "<input name='otp' type='hidden' value='" + otp + "'>" + "</form></td></tr>";
				
				
				PreparedStatement ps1 = con.prepareStatement("update user set status=? where email=?");
			
				ps1.setString(1,"Logged");
				ps1.setString(2, loginBean.getEmail());
				int i =ps1.executeUpdate();
				if(i>0)
				{
					//return ""+jsonArray
					return ""+output + "</table>";
					
				}
				
				 
				
		 }
			else 
			{
				return "fail";
			}
			

		} catch (Exception e ) {
			//TODO: handle exception
			e.printStackTrace();
		}
		
		return "fail"; 
	
		
	}
	
	

		
}
	

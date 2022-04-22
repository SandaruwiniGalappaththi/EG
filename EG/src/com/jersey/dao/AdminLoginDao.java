package com.jersey.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jersey.dbconn.DbConnectionProvider;

import Login.AdminLoaginBean;


public class AdminLoginDao {

	public static String login(AdminLoaginBean adminloginBean) 
	{
		Connection con =DbConnectionProvider.getConnection();
		String output;
		
		try {
			
			
			output = "<table border='1'><tr><th>Name</th>"
					+ "<th>Email</th>" +
					 "<th>Mobile</th>" + 
					 "<th>Status</th>" +
					 "<th>Search User</th><th>Download</th></tr>";
			
			PreparedStatement ps=con.prepareStatement("select * from user where email=? and password=?");
			ps.setString(1,adminloginBean.getEmail());
			ps.setString(2,adminloginBean.getPassword());
			
			ResultSet rs = ps.executeQuery();
			System.out.println(adminloginBean.getEmail());
			System.out.println(adminloginBean.getPassword());
			
			if(rs.next())
			{
				PreparedStatement ps2=con.prepareStatement("select name,email,mobile,status from user");
				ResultSet rrs = ps2.executeQuery();
			
				while(rrs.next()) {
			
					
					 String name = rrs.getString("name"); 
					 String email = rrs.getString("email"); 
					 String mobile = rrs.getString("mobile") ; 
					 String status = rrs.getString("status");
//					 String otp = Integer.toString(rs.getInt("otp"));
					 

						// Add into the html table
						 output += "<tr><td>" + name + "</td>"; 
						 output += "<td>" + email + "</td>"; 
						 output += "<td>" + mobile + "</td>"; 
						 output += "<td>" + status + "</td>"; 
						// buttons
						 output += "<td><input name='btnUpdate' type='button' value='Search User' class='btn btn-secondary'></td>"
						// + "<td><form method='post' action=''>"
						 + "<td><input name='btnRemove' type='button' value='Download User' class='btn btn-secondary'></td>"
						 + "</form></td></tr>";
						
				}
			
				
				
				
				
				PreparedStatement ps1 = con.prepareStatement("update user set status=? where email=?");
			
				ps1.setString(1,"Admin");
				ps1.setString(2, adminloginBean.getEmail());
				int i =ps1.executeUpdate();
				if(i>0)
				{
					//return ""+jsonArray
					return ""+output + "</table>";
					
				}
				else {
					return "fail";
				}
				
			
			}

		} catch (Exception e ) {
			//TODO: handle exception
			e.printStackTrace();
		}
		
		return "fail"; 
	
		
	
	
	
	}

	
		
	
		
}
	
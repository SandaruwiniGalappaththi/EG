package com.jersey.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.jersey.dbconn.DbConnectionProvider;

import Login.ChangePasswordBean;

public class ChangePassword {

	public static boolean checkEmailPassword(ChangePasswordBean changePasswordBean)
	{
		boolean output;
		Connection con=DbConnectionProvider.getConnection();
		
		try {
			
			
			PreparedStatement ps=con.prepareStatement("select * from user where email=? and password=?");
			ps.setString(1, changePasswordBean.getEmail());
			ps.setString(2, changePasswordBean.getPassword());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				output = true;
			}
			else
			{
				output = false;
			}
			
			
			
		}catch (Exception e) {
			
			output = false;
			e.printStackTrace();
			
			
		}
		//System.out.println(output);
		return output;
		
	}
	

	
	
	
	public static boolean changePasswordUser(ChangePasswordBean changePasswordBean)
	{
		
		 boolean output;
		Connection con=DbConnectionProvider.getConnection();
		
		try {
			
			
			PreparedStatement ps=con.prepareStatement("update user set password=? where email=?");
			ps.setString(1, changePasswordBean.getNewPassword());
			ps.setString(2, changePasswordBean.getEmail());
			int i  = ps.executeUpdate();
			if(i>0)
			{
				output = true;
			}
			else
			{
				output = false;
			}
			
		}catch (Exception e) {
			output = false;
			e.printStackTrace();
			
			
		}
	
		return output;
		
		
	}
			
			
	
	
}




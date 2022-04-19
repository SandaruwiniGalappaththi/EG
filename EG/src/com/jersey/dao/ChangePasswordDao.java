package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.jersey.dbconn.DbConnectionProvider;

import Login.ChangePasswordBean;

public class ChangePasswordDao {

	public static boolean checkEmailPassword(ChangePasswordBean changePasswordBean)
	{
		Connection con=DbConnectionProvider.getConnection();
		
		try {
			
			
			PreparedStatement ps=con.prepareStatement("select * from user email=? and password=?");
			ps.setString(1, changePasswordBean.getEmail());
			ps.setString(2, changePasswordBean.getPassword());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
			
			
			
		}catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
			
			
		}
		return false;
		
	}
	

	
	
	
	public static boolean changePasswordUser(ChangePasswordBean changePasswordBean)
	{
		Connection con=DbConnectionProvider.getConnection();
		
		try {
			
			
			PreparedStatement ps=con.prepareStatement("update user set password=? where email=?");
			ps.setString(1, changePasswordBean.getNewPassword());
			ps.setString(2, changePasswordBean.getEmail());
			int i  = ps.executeUpdate();
			if(i>0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}catch (Exception e) {
			//TODO:handle exception
			e.printStackTrace();
			
			
		}
		return false;
		
		
	}
			
			
	
	
}




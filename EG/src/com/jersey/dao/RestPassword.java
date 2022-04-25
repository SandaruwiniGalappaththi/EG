package com.jersey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.jersey.dbconn.DbConnectionProvider;
import Login.RestPasswordBean;

public class RestPassword {

	public static boolean checkEmail(RestPasswordBean restPasswordBean) {
		boolean output;
		Connection con=DbConnectionProvider.getConnection();
		
		try {
					
				PreparedStatement ps=con.prepareStatement("select * from user where email=? and otp=?");
				ps.setString(1, restPasswordBean.getEmail());
				ps.setString(2, restPasswordBean.getOtp());
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					
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
	

	
	
	
		public static boolean restPasswordUser(RestPasswordBean restPasswordBean) {
		
		boolean output;
		Connection con=DbConnectionProvider.getConnection();
		
		try {
			
			
				PreparedStatement ps=con.prepareStatement("update user set password=? where email=?");
				ps.setString(1, restPasswordBean.getNewPassword());
				ps.setString(2, restPasswordBean.getEmail());
				int i  = ps.executeUpdate();
				if(i>0) {
					
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


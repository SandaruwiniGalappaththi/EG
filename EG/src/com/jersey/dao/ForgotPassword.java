package com.jersey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import com.jersey.dbconn.DbConnectionProvider;
import com.jersey.utility.Mailapi;
import Login.ForgotPasswordBean;

public class ForgotPassword {

		public static String forgotpassword(ForgotPasswordBean forgotPasswordBean) {
		
		//create randomly generate otp number	
		Random random = new Random();
		int i = random.nextInt(123456);
		
		//crate db connection
	    Connection con=DbConnectionProvider.getConnection();
		
		try {
			    //create prepared statement
				PreparedStatement ps=con.prepareStatement("select * from user where email=?");
				ps.setString(1, forgotPasswordBean.getEmail());
				ResultSet rs =ps.executeQuery(); // execute the statement
				if(rs.next()) {
					
					//create mail service for forgot password
					String txt = "Please click the button below to reset your password.";
					String value = "Reset Password";
					Mailapi.sendOtp(i, forgotPasswordBean.getEmail(), value, txt);
					
					//create prepared statement
					PreparedStatement ps1=con.prepareStatement("update user set otp=? where email=?");
					ps1.setInt(1, i);
					ps1.setString(2, forgotPasswordBean.getEmail());
					int ii = ps1.executeUpdate(); //execute the statement
					if(ii>0) {
						
						return "success";
					}
					else
					{
						return "fail";
					}		
				
				}
				
		}catch (Exception e )  {
			//TODO: handle exception
			e.printStackTrace();
	           }
		
		return "fail";
	}
}

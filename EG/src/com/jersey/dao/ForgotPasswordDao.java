package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import com.jersey.dbconn.DbConnectionProvider;
import com.jersey.utility.Mailapi;

import Login.ForgotPasswordBean;

public class ForgotPasswordDao {

	public static String forgotpassword(ForgotPasswordBean forgotPasswordBean)
	{
		Random random = new Random();
		int i = random.nextInt(1234);
		//int otp = new Random().nextInt(345);
		
		Connection con=DbConnectionProvider.getConnection();
		
		try {
			PreparedStatement ps=con.prepareStatement("select * from user where email=?");
			ps.setString(1, forgotPasswordBean.getEmail());
			ResultSet rs =ps.executeQuery();
			if(rs.next())
			{
				String value = "Reset Password";
				Mailapi.sendOtp(i, forgotPasswordBean.getEmail(), value);
				PreparedStatement ps1=con.prepareStatement("update user set otp=? where email=?");
				ps1.setInt(1, i);
				ps1.setString(2, forgotPasswordBean.getEmail());
				int ii = ps1.executeUpdate();
				if(ii>0)
				{
					return "success";
				}
				else
				{
					return "fail";
				}		
				
			}
		}catch (Exception e )
		      {
			//TODO: handle exception
			e.printStackTrace();
	           }
		return "fail";
	}
}

package com.jersey.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.jersey.dbconn.DbConnectionProvider;
import com.jersey.utility.Mailapi;

import Login.UserOtpVerificationBean;

public class UserOtpVerificationDao {


	public static String OtpVerification(UserOtpVerificationBean otpVerficationBean)
	{
		Connection con = DbConnectionProvider.getConnection();
		try {
			
			PreparedStatement ps = con.prepareStatement("select * from user where email =? and otp=?");
			ps.setString(1, otpVerficationBean.getEmail());
			ps.setInt(2, otpVerficationBean.getOtp());
			System.out.println(otpVerficationBean.getEmail());
			System.out.println(otpVerficationBean.getOtp());
	 		ResultSet rs=ps.executeQuery();
			
	 		//Mailapi.sendOtp(otpVerficationBean.getOtp());
	 		
			if(rs.next())	
			{
				
				PreparedStatement ps1 = con.prepareStatement("update user set status=? where email=?");
				ps1.setString(1, "active");
				ps1.setString(2, otpVerficationBean.getEmail());
				int i = ps1.executeUpdate();
				if(i>0)
				{
					return "success";
				}
				else
				{
					return "fail";
				}
			}
		//	else {
		//		   return "otp not valid";
		//	}
			
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		
		return "otp not valid";
		
		
		
		
		
		
		
	}
}



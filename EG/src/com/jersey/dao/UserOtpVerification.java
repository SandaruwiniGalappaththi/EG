package com.jersey.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.jersey.dbconn.DbConnectionProvider;
import com.jersey.utility.Mailapi;
import Login.UserOtpVerificationBean;

public class UserOtpVerification {

	public static String OtpVerification(UserOtpVerificationBean otpVerficationBean) {
		
		//create db connection
		Connection con = DbConnectionProvider.getConnection();
		
		try {
			
			//create prepared statement
			PreparedStatement ps = con.prepareStatement("select * from user where email =? and otp=?");
			ps.setString(1, otpVerficationBean.getEmail());
			ps.setInt(2, otpVerficationBean.getOtp());
			System.out.println(otpVerficationBean.getEmail());
			System.out.println(otpVerficationBean.getOtp());
	 		ResultSet rs=ps.executeQuery(); //execute the statement
				
			if(rs.next()) {
				
				//create a prepared statement
				PreparedStatement ps1 = con.prepareStatement("update user set status=? where email=?");
				ps1.setString(1, "Active");
				ps1.setString(2, otpVerficationBean.getEmail());
				int i = ps1.executeUpdate(); //execute the statement
				if(i>0) {
					
							return "success";
			     		}
						else
						{
							return "fail";
						}
				   }
		
				} catch (Exception e) {
						//TODO: handle exception
						e.printStackTrace();
					}
		
					return "otp not valid";
	
		}

}



package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import com.jersey.dbconn.DbConnectionProvider;
import com.jersey.utility.Mailapi;

import Login.UserBean;


public class UserDao {

public static String registerDao(UserBean rs)
  {
	Random random = new Random();
	int otp = random.nextInt(1234);
	//int otp = new Random().nextInt(345);
	
	Connection con=DbConnectionProvider.getConnection();
	
	try {
		
		PreparedStatement ps1=con.prepareStatement("select email from user where email=?");
		ps1.setString(1, rs.getEmail());
		ResultSet rrs=ps1.executeQuery();
		System.out.println(rrs.next());
		if(rrs.next())
		{
			return "already exist";
		}
		else {
			
			PreparedStatement ps=con.prepareStatement("insert into user values(?,?,?,?,?,?)");
			ps.setString(1, rs.getName());
			ps.setString(2, rs.getEmail());
			ps.setString(3, rs.getPassword());
			ps.setString(4, rs.getMobile());
			ps.setInt(5, otp);
			ps.setString(6, "inactive");
			int i=ps.executeUpdate();  
			String mail = rs.getEmail();
			if(i>0)	
			{
				String value = "Verify";
				Mailapi.sendOtp(otp, mail, value);
				//sendMail.sendOtp(otp);
				return "success";
				
				
			}
			else
			{
				return "fail";
			}
			
		}
		
		}  catch (Exception e) {
		 System.out.println(e);
	}
	
	
	return "fail";
}

}

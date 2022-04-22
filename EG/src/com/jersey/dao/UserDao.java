package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jersey.dbconn.DbConnectionProvider;
import com.jersey.utility.Mailapi;

import Login.UserBean;


public class UserDao {
	
	
	public static boolean testUsingSimpleRegex(String email) {
	  	   
        
        String regexPattern ="^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";
        Pattern pattern = Pattern.compile(regexPattern);  
        Matcher matcher = pattern.matcher(email); 
        System.out.println(email +" : "+ matcher.matches()+"\n");
        if(matcher.matches()) {
       	 
       	 return true;
        }
        else {
       	 return false;
        }
        
        
        
        }

public static String registerDao(UserBean rs)
  {
	
	
	
	
	Random random = new Random();
	int otp = random.nextInt(123456);
	//int otp = new Random().nextInt(345);
	
	Connection con=DbConnectionProvider.getConnection();
	
	
	try {
		
		PreparedStatement ps1=con.prepareStatement("select email from user where email=?");
		ps1.setString(1, rs.getEmail());
		ResultSet rrs=ps1.executeQuery();
		boolean ok =testUsingSimpleRegex(rs.getEmail()) ;
		if(rrs.next())
		{
			return "already exist";
		}
		else if(ok == false) {
			return "Invalid Email Format";
			
		}
		else {
		
		    
			
			PreparedStatement ps=con.prepareStatement("insert into user values(?,?,?,?,?,?)");
			ps.setString(1, rs.getName());
			ps.setString(2, rs.getEmail());
			ps.setString(3, rs.getPassword());
			ps.setString(4, rs.getAccountNo());
			ps.setInt(5, otp);
			ps.setString(6, "inactive");
			int i=ps.executeUpdate();  
			String mail = rs.getEmail();
			
			if(i>0)	
			{
			    String txt="Congratulations! You're almost set to start using EG Group of Compnay. Just click the button below to verify your Otp Number.";
				String value = "Verify";
				Mailapi.sendOtp(otp, mail, value, txt);
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

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


public class UserRegister {
	
	public static boolean testUsingSimpleRegex(String email) {
	  	        
		//create a validation for email format
        String regexPattern = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";
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

			public static String registerDao(UserBean rs) {
		
				    //create generate otp number
					Random random = new Random();
					int otp = random.nextInt(123456);
					Connection con=DbConnectionProvider.getConnection();	
					
					try {
		
					//create a prepared statement	 
					PreparedStatement ps1=con.prepareStatement("select email from user where email=?");
					ps1.setString(1, rs.getEmail());
					
					// execute the statement
					ResultSet rrs=ps1.executeQuery(); 
					
					// checking email validation
					boolean ok =testUsingSimpleRegex(rs.getEmail()) ; 
					
					if(rrs.next()) {
			
							return "already exist";
						}
						else if(ok == false) {
							return "Invalid Email Format";
			
						}
						else {	    
			                
							//create prepared statement
							PreparedStatement ps=con.prepareStatement("insert into user values(?,?,?,?,?,?)");
							ps.setString(1, rs.getName());
							ps.setString(2, rs.getEmail());
							ps.setString(3, rs.getPassword());
							ps.setString(4, rs.getAccountNo());
							ps.setInt(5, otp);
							ps.setString(6, "Inactive");
							int i=ps.executeUpdate();  
							String mail = rs.getEmail();
			
							if(i>0)	{
								String txt="Congratulations! You're almost set to start using EG Group of Compnay. Just click the button below to verify your Otp Number.";
								String value = "Verify";
								Mailapi.sendOtp(otp, mail, value, txt);

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

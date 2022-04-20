package com.jersey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jersey.dbconn.DbConnectionProvider;

import Login.SearchUserBean;




public class SearchUser {
	public static String search(SearchUserBean searchUserBean) 
	{
		Connection con =DbConnectionProvider.getConnection();
		String output= "";
		
		try {
			

			output = "<table border='2'><tr><th>Name</th>" +
					 "<th>Email</th>" +
					 "<th>Inquiries</th><th>FeedBack</th>" +
					 "<th>PaymentStatus</th></tr>";
			
			PreparedStatement ps=con.prepareStatement("select * from user where mobile=?");
			ps.setString(1,searchUserBean.getMobile());
			ResultSet rs = ps.executeQuery();
			System.out.println(searchUserBean.getMobile());
			
			if(rs.next())
			{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("name",rs.getString(1));
				jsonObject2.put("email",rs.getString(2));
				JSONArray jsonArray = new JSONArray();
				jsonArray.put(jsonObject2);
				
				  
				 String name = rs.getString("name"); 
				 String email = rs.getString("email"); 
				 String otp = Integer.toString(rs.getInt("otp"));
				
				
				// Add into the html table
				 output += "<tr><td>" + name + "</td>"; 
				 output += "<td>" + email + "</td>"; 
				
				// buttons
				 output += "<td><input name='btnInquiries' type='button' value='Inquiries' class='btn btn-secondary'></td>"
						// + "<td><form method='post' action=''>"
						+ "<td><input name='btnFeedcack' type='button' value='Feedback' class='btn btn-secondary'></td>"
						+ "<td><input name='btnPayment' type='button' value='Pament Status' class='btn btn-secondary'></td>"
				         + "<input name='otp' type='hidden' value='" + otp + "'>" + "</form></td></tr>";
				
				
				
			//	PreparedStatement ps1 = con.prepareStatement("update user set status=? where email=?");
			
			//	ps1.setString(1,"Logged");
			//	ps1.setString(2, loginBean.getEmail());
			//	int i =ps1.executeUpdate();
				//if(i>0)
				
					//return ""+jsonArray;
				 return ""+ output + "</table>";
				}
				
				
			
			else 
			{
				return "fail";
			}
			
			
			
		} catch (Exception e ) {
			//TODO: handle exception
			e.printStackTrace();
		}
		
		return "fail";
		
	}

		
	}
	

		
		


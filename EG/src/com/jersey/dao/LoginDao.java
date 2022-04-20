package com.jersey.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;


import com.jersey.dbconn.DbConnectionProvider;

import Login.LoginBean;

public class LoginDao {

	public static String login(LoginBean loginBean) 
	{
		Connection con =DbConnectionProvider.getConnection();
		
		try {
			
			PreparedStatement ps=con.prepareStatement("select * from user where email=? and password=?");
			ps.setString(1,loginBean.getEmail());
			ps.setString(2,loginBean.getPassword());
			ResultSet rs = ps.executeQuery();
			System.out.println(loginBean.getEmail());
			System.out.println(loginBean.getPassword());
			
			if(rs.next())
			{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("name",rs.getString(1));
				jsonObject2.put("email",rs.getString(2));
				jsonObject2.put("password",rs.getString(3));
				jsonObject2.put("mobile",rs.getString(4));
				JSONArray jsonArray = new JSONArray();
				jsonArray.put(jsonObject2);
				
				PreparedStatement ps1 = con.prepareStatement("update user set status=? where email=?");
				ps1.setString(1,"Logged"); 
				ps1.setString(2, loginBean.getEmail());
				int i =ps1.executeUpdate();
				if(i>0)
				{
					return ""+jsonArray;
				}
				
				
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
	

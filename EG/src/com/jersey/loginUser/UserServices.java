package com.jersey.loginUser;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Login.ChangePasswordBean;
import Login.ForgotPasswordBean;
import Login.UserBean;
import Login.LoginBean;
import Login.AdminLoaginBean;
import Login.UserOtpVerificationBean;

import com.jersey.dao.AdminLoginDao;
import com.jersey.dao.ChangePasswordDao;
import com.jersey.dao.ForgotPasswordDao;
import com.jersey.dao.LoginDao;
import com.jersey.dao.RestPassword;
import com.jersey.dao.SearchUser;
import com.jersey.dao.UserDao;
import com.jersey.dao.UserOtpVerificationDao;
import Login.RestPasswordBean;
import Login.SearchUserBean;

import com.jersey.dao.UpdateUser;
import com.jersey.dao.UserDelete;


@Path("/user")
public class UserServices 
{   
	
	 
	
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String registerUser(String userdata) throws JsonParseException, JsonMappingException, IOException 
    {
    
    	
    String str = null; 
    if(null!=userdata)   
    {   
    	
    	
    	ObjectMapper mapper = new ObjectMapper();
    	UserBean userbean = mapper.readValue(userdata, UserBean.class);
    	str = UserDao.registerDao(userbean);
 
    
    	
    	
    	if(str.equals("already exist"))
    	{
    		return "already exist....!!!";
    	}
    	else if(str.equals("success"))
    	{
    		return "Register successfully...!!!";
    	}
    	else if(str.equals("Invalid Email Format")) {
    		return "Invalid Email Format...!";
    	}
    	else
    	{
    		return "register fail...!!!";
    	}
    }
		//return str;
	return str;
		
		//UserBean userBean = new UserBean();
		//String result = UserDao.u(userBean);
	}
    
    
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String loginUser(String userdata) throws JsonParseException, JsonMappingException, IOException
    {
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
    		LoginBean loginBean = objectMapper.readValue(userdata, LoginBean.class);
    	
    String str = LoginDao.login(loginBean);
    
    	if(str.equals("fail")) 
    		
    		
    	{
    		return "Username and Password Incorrect ...!!!";
    	}
    	else
    	{
    		return str;
    	} 
   
    
    	} catch (Exception e) {
    		//TODO: handle exception
    		e.printStackTrace();
    	}
    	return "fail";
	
	}
    
	
    @Path("/verifyOtp")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String optUser(String userdata) throws JsonParseException, JsonMappingException, IOException
    {
    	try {
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		UserOtpVerificationBean otpVerificationBean=objectMapper.readValue(userdata,UserOtpVerificationBean.class);
    		String str=UserOtpVerificationDao.OtpVerification(otpVerificationBean);
    		if(str.equals("success"))
    				{
    				   return "otp verify success"	;
    				}
    		else
    		{
    			return "otp verify fail";
    		}
    	}catch (Exception e) {
    		//TODO: handle exception
    		e.printStackTrace();
    	}
    	
    	return "otp verify fail";
    	
    	
   // String str = "hello";
  
	//	return str;
	}
    
    
    @Path("/changepassword")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String changepasswordUser(String userdata) throws JsonParseException, JsonMappingException, IOException
    {
    	try {
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		ChangePasswordBean changePasswordBean=objectMapper.readValue(userdata,ChangePasswordBean.class);
    		
    		//boolean str=ChangePasswordDao.checkEmailPassword(changePasswordBean);
    		//boolean str1=ChangePasswordDao.changePasswordUser(changePasswordBean);
    		//if(str==true && str1==false)
    			
    	     if(ChangePasswordDao.checkEmailPassword(changePasswordBean) && ChangePasswordDao.changePasswordUser(changePasswordBean))
    	      {
    	    	  return "change password successfully...!!!";
    	      }
    	      else
    	      {
    	    	  return "Change password fail";
    	      }
    	      
    	} catch (Exception e) {
    		//TODO:handle exception
    		e.printStackTrace();
    	}
        return "fail";
    
    }
    
    @Path("/forgotpassword")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String forgotpasswordUser(String userdata)
    {
    
    		
    		Object obj = JSONValue.parse(userdata);
    		JSONObject jsonObject=(JSONObject)obj;
    		
    		ForgotPasswordBean forgotPasswordBean=new ForgotPasswordBean();
    		forgotPasswordBean.setEmail((String)jsonObject.get("email"));
    		String str = ForgotPasswordDao.forgotpassword(forgotPasswordBean);
    		
    		//boolean str=ChangePasswordDao.checkEmailPassword(changePasswordBean);
    		//boolean str1=ChangePasswordDao.changePasswordUser(changePasswordBean);
    		//if(str==true && str1==false)
    			
    	    if(str.equals("success"))
    	    {
    	    	return "otp send success";
    	    }
    	    else {
    	    	return "otp send failed";
    	    }
    	      
    	
    	
        } 
    
    
    
    @PUT
    @Path("/update") 
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.TEXT_PLAIN) 
    public String updateUser(String s) 
    { 
    //Convert the input string to a JSON object 
     JsonObject updateUser = new JsonParser().parse(s).getAsJsonObject(); 
    //Read the values from the JSON object
     String name = updateUser.get("name").getAsString(); 
     String email = updateUser.get("email").getAsString(); 
     String password = updateUser.get("password").getAsString(); 
     String mobile = updateUser.get("mobile").getAsString(); 
     String otp = updateUser.get("otp").getAsString();
     
     UpdateUser updateU =new UpdateUser();
   
     String output = updateU.updateUser(name, email, password, mobile,otp); 
    return output; 
    }
    
    
    @DELETE
    @Path("/deleteUser") 
    @Consumes(MediaType.APPLICATION_XML) 
    @Produces(MediaType.TEXT_PLAIN) 
    public String DeleteUser(String userdata) 
    { 
    //Convert the input string to an XML document
     Document doc = Jsoup.parse(userdata, "", Parser.xmlParser()); 
     
     UserDelete deletU =new UserDelete();
     
    //Read the value from the element <itemID>
     String otp = doc.select("otp").text(); 
     String output = deletU.deleteUser(otp); 
    return output; 
    }
    
    
    @Path("/resetpassword")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String restpasswordUser(String userdata) throws JsonParseException, JsonMappingException, IOException
    {
    	try {
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		RestPasswordBean restPasswordBean=objectMapper.readValue(userdata,RestPasswordBean.class);
    		
    		//boolean str=ChangePasswordDao.checkEmailPassword(changePasswordBean);
    		//boolean str1=ChangePasswordDao.changePasswordUser(changePasswordBean);
    		//if(str==true && str1==false)
    			
    	     if(RestPassword.checkEmail(restPasswordBean) && RestPassword.restPasswordUser(restPasswordBean))
    	      {
    	    	  return "change password successfully...!!!";
    	      }
    	      else
    	      {
    	    	  return "Change password fail";
    	      }
    	      
    	} catch (Exception e) {
    		//TODO:handle exception
    		e.printStackTrace();
    	}
        return "fail";
    
    }
    
    
    @Path("/searchuser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String serachUser(String userdata) throws JsonParseException, JsonMappingException, IOException
    {
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
    		SearchUserBean searchUserBean = objectMapper.readValue(userdata, SearchUserBean.class);
    	
    String str = SearchUser.search(searchUserBean);
    
    	if(str.equals("fail")) 
    		
    		
    	{
    		return "No any User holding this mobile number ...!!!";
    	}
    	else
    	{
    		return str;
    	} 
   
    
    	} catch (Exception e) {
    		//TODO: handle exception
    		e.printStackTrace();
    	}
    	return "fail";
	
	}
    
    @Path("/adminlogin")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String loginAdmin(String userdata) throws JsonParseException, JsonMappingException, IOException
    {
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
    		AdminLoaginBean adminloginBean = objectMapper.readValue(userdata, AdminLoaginBean.class);
    	
    String str = AdminLoginDao.login(adminloginBean);
    
    	if(str.equals("fail")) 
    		
    		
    	{
    		return "Username and Password Incorrect ...!!!";
    	}
    	else
    	{
    		return str;
    	} 
   
    
    	} catch (Exception e) {
    		//TODO: handle exception
    		e.printStackTrace();
    	}
    	return "fail";
	
	}
    
}

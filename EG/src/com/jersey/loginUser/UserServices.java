package com.jersey.loginUser;

//for REST service
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//for XML
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

//for JSON
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import Login.ChangePasswordBean;
import Login.ForgotPasswordBean;
import Login.UserBean;
import Login.LoginBean;
import Login.AdminLoaginBean;
import Login.UserOtpVerificationBean;
import Login.RestPasswordBean;
import Login.SearchUserBean;

import com.jersey.dao.AdminLogin;
import com.jersey.dao.ChangePassword;
import com.jersey.dao.ForgotPassword;
import com.jersey.dao.Login;
import com.jersey.dao.RestPassword;
import com.jersey.dao.SearchUser;
import com.jersey.dao.UserRegister;
import com.jersey.dao.UserOtpVerification;
import com.jersey.dao.UpdateUser;
import com.jersey.dao.UserDelete;


@Path("/user")
public class UserServices {   
		
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String registerUser(String userdata) throws JsonParseException, JsonMappingException, IOException {
        	
    	String str = null; 
    	if(null!=userdata) {   
    	   	
    		ObjectMapper mapper = new ObjectMapper();
    		UserBean userbean = mapper.readValue(userdata, UserBean.class);
    		str = UserRegister.registerDao(userbean);    	
    	
    	if(str.equals("already exist")) {
    		
    		return "<html><head><title>Register Here</title>"
    				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    				+ "</head><body>"
    				+ "<div class='card'><h4 class='text-center'>Already Exist...! Please Use another Email for Registring Purpose.</h4></div>"
    				+ "</body></html>";     				
    		}
    		else if(str.equals("success")) {
    			
    				return "<html><head><title>Register Here</title>"
    						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    						+ "</head><body>"
    						+ "<div class='card'><h4 class='text-center'>Register Successfully...!</h4></div>"
    						+ "</body></html>"; 
    				}
    				else if(str.equals("Invalid Email Format")) {
    					
    						return "<html><head><title>Register Here</title>"
    								+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    								+ "</head><body>"
    								+ "<div class='card'><h4 class='text-center'>Invalid Email Format...!</h4></div>"
    								+ "</body></html>";   				
    				}
    				else {
    							return "<html><head><title>Register Here</title>"
    									+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    									+ "</head><body>"
    									+ "<div class='card'><h4 class='text-center'>register fail...!!!</h4></div>"
    									+ "</body></html>";    				
    				}
    		}
		
    		return str;				
    }
      
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String loginUser(String userdata) throws JsonParseException, JsonMappingException, IOException {
    	
    	try {
    			ObjectMapper objectMapper = new ObjectMapper();
    			LoginBean loginBean = objectMapper.readValue(userdata, LoginBean.class);
    	
    			String str = Login.login(loginBean);
    
    			if(str.equals("Please Verify Your Otp Number.")) {
    		
    				return "<html><head><title>Login Here</title>"
    						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    						+ "</head><body>"
    						+ "<div class='card'><h4 class='text-center'>Please Verify Your Otp Number.</h4></div>"
    						+ "</body></html>"; 
    			}
    			else if(str.equals("fail")) {
    		
    					return "<html><head><title>Login Here</title>"
    							+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    							+ "</head><body>"
    							+ "<div class='card'><h4 class='text-center'>Username or Password Incorrect ...!!! Please Check and Log Again</h4></div>"
    							+ "</body></html>";   				
    			       }
    					else {
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
	public String optUser(String userdata) throws JsonParseException, JsonMappingException, IOException {
    	
    	try {
    		
    			ObjectMapper objectMapper = new ObjectMapper();
    			UserOtpVerificationBean otpVerificationBean=objectMapper.readValue(userdata,UserOtpVerificationBean.class);
    			String str=UserOtpVerification.OtpVerification(otpVerificationBean);
    			if(str.equals("success")) {
    				    
    				return "<html><head><title>Verifing Otp</title>"
    		    				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    		    				+ "</head><body>"
    		    				+ "<div class='card'><h4 class='text-center'>Otp Verify Success...!!!</h4></div>"
    		    				+ "</body></html>"; 
    				}
    				else
    				{
    						return "<html><head><title>Verifing Otp</title>"
    								+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    								+ "</head><body>"
    								+ "<div class='card'><h4 class='text-center'>Otp Verify Fail...!!! Please Re-Check Your Otp Or Email.</h4></div>"
    								+ "</body></html>";     					
    				}
    	
    			}catch (Exception e) {
    					//TODO: handle exception
    					e.printStackTrace();
    			 }
				
    	return "otp verify fail";
    	
    	
  
	}
    
    
    @Path("/changePassword")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String changepasswordUser(String userdata) throws JsonParseException, JsonMappingException, IOException
    {
    	try {
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		ChangePasswordBean changePasswordBean=objectMapper.readValue(userdata,ChangePasswordBean.class);
    		
    		
    			
    	     if(ChangePassword.checkEmailPassword(changePasswordBean) && ChangePassword.changePasswordUser(changePasswordBean))
    	      {
    	    	  return  "<html><head><title>Change Password</title>"
  	    				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
  	    				+ "</head><body>"
  	    				+ "<div class='card'><h4 class='text-center'>Password Changed successfully...!!!</h4></div>"
  	    				+ "</body></html>";
    	    			  
    	    			 
    	      }
    	      else
    	      {
    	    	  return    "<html><head><title>Change Password</title>"
    	    				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
      	    				+ "</head><body>"
      	    				+ "<div class='card'><h4 class='text-center'>Password Changed Fail...!!!</h4></div>"
      	    				+ "</body></html>";
    	    			  
    	    			  
    	      }
    	      
    	} catch (Exception e) {
    		//TODO:handle exception
    		e.printStackTrace();
    	}
        return "fail";
    
    }
    
    @Path("/forgotPassword")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String forgotpasswordUser(String userdata)
    {
    
    		
    		Object obj = JSONValue.parse(userdata);
    		JSONObject jsonObject=(JSONObject)obj;
    		
    		ForgotPasswordBean forgotPasswordBean=new ForgotPasswordBean();
    		forgotPasswordBean.setEmail((String)jsonObject.get("email"));
    		String str = ForgotPassword.forgotpassword(forgotPasswordBean);
    		
    		
    			
    	    if(str.equals("success"))
    	    {
    	    	return  "<html><head><title>Otp Mail Service</title>"
  	    				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
  	    				+ "</head><body>"
  	    				+ "<div class='card'><h4 class='text-center'>Otp Send Success...!!!</h4></div>"
  	    				+ "</body></html>";
    	    			
    	    			
    	    }
    	    else {
    	    	return  "<html><head><title>Otp Mail Service</title>"
  	    				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
  	    				+ "</head><body>"
  	    				+ "<div class='card'><h4 class='text-center'>Otp Send Failed...!!! Please Check Your Email Or Otp</h4></div>"
  	    				+ "</body></html>";
    	    			
    	    			
    	    }
    	      
    	
    	
        } 
    
    
    
    @PUT 
    @Path("/update") 
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.TEXT_PLAIN) 
    public String updateUser(String userdata) { 
    	
    	//Convert the input string to a JSON object 
    	JsonObject updateUser = new JsonParser().parse(userdata).getAsJsonObject(); 
    	
    	//Read the values from the JSON object
    	String name = updateUser.get("name").getAsString(); 
    	String email = updateUser.get("email").getAsString(); 
    	String password = updateUser.get("password").getAsString(); 
    	String accountNo = updateUser.get("accountNo").getAsString(); 
    	
     
    	UpdateUser updateU =new UpdateUser();
   
    	String output = updateU.updateUser(name, email, password, accountNo); 
    	return output; 
    }
    
    
    @DELETE
    @Path("/delete") 
    @Consumes(MediaType.APPLICATION_XML) 
    @Produces(MediaType.TEXT_PLAIN) 
    public String DeleteUser(String userdata) {
    	
    //Convert the input string to an XML document
     Document doc = Jsoup.parse(userdata, "", Parser.xmlParser()); 
     
     UserDelete deletU =new UserDelete();
     
    //Read the value from the element <userdata>
     String accountNo = doc.select("accountNo").text(); 
     String output = deletU.deleteUser(accountNo); 
     return output; 
    }
    
    
    @Path("/resetPassword")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String restpasswordUser(String userdata) throws JsonParseException, JsonMappingException, IOException {
    	
    	try {
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		RestPasswordBean restPasswordBean=objectMapper.readValue(userdata,RestPasswordBean.class);
    		
            if(RestPassword.checkEmail(restPasswordBean) && RestPassword.restPasswordUser(restPasswordBean)) {
    	    	  
            	return    "<html><head><title>Reset Password</title>"
    	    				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
      	    				+ "</head><body>"
      	    				+ "<div class='card'><h4 class='text-center'>Reset Password Successfully...!!!</h4></div>"
      	    				+ "</body></html>";
        	    }
    	      	else {
    	      			return "<html><head><title>Reset Password</title>"
    	      					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    	      					+ "</head><body>"
    	      					+ "<div class='card'><h4 class='text-center'>Reset Password Failed...!!! Please Check Your Email. </h4></div>"
    	      					+ "</body></html>";
    	      		 }
    	      
    	     } catch (Exception e) {
    	    	 	//TODO:handle exception
    	    	 	e.printStackTrace();
    	     	}
        return "fail";
    
    }
    
    
    @Path("/searchUser")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String serachUser(String userdata) throws JsonParseException, JsonMappingException, IOException {
    	
    	try {
    	
    		ObjectMapper objectMapper = new ObjectMapper();
    		SearchUserBean searchUserBean = objectMapper.readValue(userdata, SearchUserBean.class);
    		String str = SearchUser.search(searchUserBean);
    
    		if(str.equals("fail")) {
    			
    				return "<html><head><title>Search User</title>"
    						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    						+ "</head><body>"
    						+ "<div class='card'><h4 class='text-center'>No Any User holding This Account Number ...!!!. </h4></div>"
    						+ "</body></html>";
    		    }
    			else {
    		
    				return str;
    			} 
   
    		} catch (Exception e) {
    				//TODO: handle exception
    				e.printStackTrace();
    	         }
    	return "fail";
	
	}
    
    @Path("/adminLogin")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String loginAdmin(String userdata) throws JsonParseException, JsonMappingException, IOException {
    	
    	try {
    			ObjectMapper objectMapper = new ObjectMapper();
    			AdminLoaginBean adminloginBean = objectMapper.readValue(userdata, AdminLoaginBean.class);
    			String str = AdminLogin.login(adminloginBean);
    			
    			if(str.equals("fail")) {
    				
    					return "<html><head><title>Admin Login</title>"
    							+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
    							+ "</head><body>"
    							+ "<div class='card'><h4 class='text-center'> User Name Or Password Incorrect...!!! Pleace contact EG User Manager. </h4></div>"
    							+ "</body></html>";
    		         }
    				else {
    		
    						return str;
    					} 
   
    		} catch (Exception e) {
    			//TODO: handle exception
    			e.printStackTrace();
    	  }
    return "fail";
	
	}
    
}

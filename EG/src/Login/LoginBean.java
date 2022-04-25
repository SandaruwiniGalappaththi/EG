package Login;
//package com.jersey.bean;

//import java.io.Serializable;

public class LoginBean {
	
	public String email,password,status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email =email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password =password;
	}

	

}

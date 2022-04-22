package Login;

public class AdminLoaginBean {

	//package com.jersey.bean;

	//import java.io.Serializable
		
		public String name,email,password,accountNo,status;
		

		public String getAccountNo() {
			return accountNo;
		}

		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		
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


package ContactNumbers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class Contacts {
    private Connection connect() { 
	 	Connection con = null; 
	 	try { 
	 		Class.forName("com.mysql.cj.jdbc.Driver"); 
	 		
		      //Provide the correct details: DBServer/DBName, user-name, password 
	 		   String url = "jdbc:mysql://127.0.0.1:3306/contacts";
    	 	   String user = "root";
    	 	   String password = "";
	           con = DriverManager.getConnection(url, user, password); 
	           } 
	    catch (Exception e) {
	 		e.printStackTrace();
	 	} 
	 	return con; 
 	 } 
	         
	         
	         
	 //Search Contact
	 public String searchContact(String DistrictCode) { 
	     	String output = ""; 
	     	try { 
	     		Connection con = connect(); 
	     		if (con == null) {
	     			return "<html><head><title>Contact Page</title>"
	     				   + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
	     				   + "</head><body>"
	     				   + "<div class='card'><h4 class='text-center'>Error while connecting to the database for reading.</h4></div>"
	     				   + "</body></html>";
	     			} 
	     			
	     		// Prepare the HTML table to be displayed
	     		output = "<html><head><title>Contact Page</title>"
	     				+ "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">"
	     				+ "</head><body><table class='table' border='1'><tr>"
	     				+ "<th>DistrictCode</th>"
	     				+ "<th>Description</th>" 
	     				+ "<th>ComplainNo</th>" 
	     				+ "<th>CustomerServiceNo</th>" 
	     				+ "<th>NewConnectionsNo</th>"
	     				+ "<th>EmergencyNo</th>"
	     				+ "<th>Address</th></tr>"; 
	     			 
	     		String query = "select * from contact where DistrictCode='" + DistrictCode + "'"; 
	     		Statement stmt = con.createStatement(); 
	     		ResultSet rs = stmt.executeQuery(query); 
	     		
	     		// iterate through the rows in the result set
	     		while (rs.next()) { 
	        			 String Districtcode= rs.getString("DistrictCode"); 
	        			 String Description  = rs.getString("Description"); 
	        			 String ComplainNo     = rs.getString("ComplainNo"); 
	        			 String CustomerServiceNo =rs.getString("CustomerServiceNo"); 
	        			 String NewConnectionsNo  =rs.getString("NewConnectionsNo"); 
	        			 String EmergencyNo= rs.getString("EmergencyNo"); 
	        			 String Address = rs.getString("Address");
	     				
	        			 System.out.println("Hello");
	     				// Add into the HTML table
	     				output += "<tr><td>"+Districtcode + "</td>"; 
	     				output += "<td>" +Description + "</td>"; 
	     				output += "<td>" +ComplainNo  + "</td>"; 
	     				output += "<td>" +CustomerServiceNo + "</td>"; 
	     				output += "<td>" +NewConnectionsNo + "</td>"; 
	     				output += "<td>" +EmergencyNo + "</td>"; 
	     				output += "<td>" +Address  + "</td>"; 
	     					
						output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
									+ "<td><form method='post' action='items.jsp'>"
									+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
									+ "<input name='itemID' type='hidden' value='" + DistrictCode
									+ "'>" + "</form></td></tr>"; 
	     					
	     				} 
	     			
	     				con.close(); 
	     			
	     				// Complete the HTML table
	     				output += "</table></body></html>"; 
	     				} 
	       catch (Exception e) { 
	     				output = "Error while reading the items."; 
	     				System.err.println(e.getMessage()); 
	     				} 
	     				return output; 
	        } 
	
              
	 
	 //Read ContactNo
     public String readContact(){ 
    	  String output = ""; 
    	  try { 
    		  Connection con = connect(); 
    		  if (con == null) {
		  			return "Error while connecting to the database for reading."; 
    		   } 
					
    		  // Prepare the HTML table to be displayed
    		  output = "<html><head><title>Contact </title>"
				  	+ "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">"
				    + "</head><body><table class='table' border='1'><tr>"
				    + "<th>DistrictCode</th>"
				  	+ "<th>Description</th>" 
		            + "<th>ComplainNo</th>" 
		        	+ "<th>CustomerServiceNo</th>" 
		        	+ "<th>NewConnectionsNo</th>"
		        	+ "<th>EmergencyNo</th>"
		        	+ "<th>Address</th>"
		        	+ "<th>Update</th>"
		        	+ "<th>Remove</th></tr>"; 
				 
		        	String query = "select * from contact"; 
		            Statement stmt = con.createStatement(); 
		        	ResultSet rs = stmt.executeQuery(query); 
				
		        	// iterate through the rows in the result set
		        	while (rs.next()) { 
		        		String DistrictCode = rs.getString("DistrictCode"); 
		        		String Description  = rs.getString("Description"); 
		        		String ComplainNo     = rs.getString("ComplainNo"); 
		        		String CustomerServiceNo =rs.getString("CustomerServiceNo"); 
		        		String NewConnectionsNo  =rs.getString("NewConnectionsNo"); 
		        		String EmergencyNo= rs.getString("EmergencyNo"); 
		        		String Address = rs.getString("Address");
					
		        	    // Add into the HTML table
		        		output += "<tr><td>"+DistrictCode + "</td>"; 
			        	output += "<td>"+Description + "</td>"; 
						output += "<td>"+ ComplainNo+ "</td>"; 
						output += "<td>"+CustomerServiceNo + "</td>"; 
						output += "<td>"+NewConnectionsNo + "</td>"; 
					    output += "<td>"+EmergencyNo+ "</td>"; 
						output += "<td>"+Address+ "</td>"; 
				
						// buttons
						output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
									+ "<td><form method='post' action='items.jsp'>"
									+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
									+ "<input name='itemID' type='hidden' value='" + DistrictCode
									+ "'>" + "</form></td></tr>"; 
		        		  } 	
				
		        		  con.close(); 
				
		        		  // Complete the HTML table
		        		  output += "</table></body></html>"; 
		        	  } 
        	  catch (Exception e) { 
        		  output = "Error while reading the items."; 
        		  System.err.println(e.getMessage()); 
		        	  } 
		          return output; 			
		      }
		
      //Delete Contact
	  public String deleteContact(String DistrictCode){  
    		String output = "";  
			try {  
				Connection con = connect();  
				if (con == null) { 
				return "<html><head><title>Contact Page</title>" 
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>" 
						+ "</head><body>" 
						+ "<div class='card'><h4 class='text-center'><marquee>Error while connecting to the database for deleting.</marquee></h4></div>" 
						+ "</body></html>"; 
				  }  
				      
				  // create a prepared statement 
    			  String query = "delete from contact where DistrictCode=?";  
    				    PreparedStatement preparedStmt = con.prepareStatement(query); 
				      
	    				// binding
    					preparedStmt.setString(1, DistrictCode); 
				     
	    				// execute the statement 
	    				preparedStmt.execute();  
    					con.close();  
						output = "<html><head><title>Contact Page</title>"
								+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
								+ "</head><body>"
								+ "<div class='card'><h4 class='text-center' style=\"color: red;\"> Deleted Successfully</h4></div>"
								+ "</body></html>";
			
				      
				 } 
			catch (Exception e)  
    			{  
    				output = "<html><head><title>Contact Page</title>" 
						  + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>" 
						  + "</head><body>" 
						  + "<div class='card'><h4 class='text-center'><marquee>Error while deleting</marquee></h4></div>" 
						  + "</body></html>"; 
	    						System.err.println(e.getMessage());  
				}  
	    			return output;  
	   }  
				    
				    
	//Insert Contact
	 public String insertContact(String DistrictCode, String Description, String ComplainNo, String CustomerServiceNo, String NewConnectionsNo, String EmergencyNo, String Address) { 
	      String output = ""; 
          try { 
        	  Connection con = connect(); 
        	  if (con == null) {
	        		return "<html><head><title>Contact Page</title>"
	        				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
	        				+ "</head><body>"
	        				+ "<div class='card'><h4 class='text-center'><marquee>Error while connecting to the database for inserting.</marquee></h4></div>"
	        				+ "</body></html>";
				  }
							

				// create a prepared statement
				String query = "insert into contact(DistrictCode,Description,ComplainNo,CustomerServiceNo,NewConnectionsNo,EmergencyNo,Address )" + " values(?, ?, ?, ?, ?, ?, ?)"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
			    // binding values
						preparedStmt.setString(1, DistrictCode); 
						preparedStmt.setString(2, Description); 
						preparedStmt.setString(3, ComplainNo); 
						preparedStmt.setString(4, CustomerServiceNo); 
						preparedStmt.setString(5, NewConnectionsNo); 	
						preparedStmt.setString(6, EmergencyNo); 
						preparedStmt.setString(7, Address); 
							
				// execute the statement
						preparedStmt.execute(); 
						con.close(); 
						output = "<html><head><title>Per Unit Page</title>"
								+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
								+ "</head><body>"
								+ "<div class='card'><h4 class='text-center' style=\\\"color: red;\\>Inserted Contact  successfully</h4></div>"
								+ "</body></html>"; 
			 } 
			catch (Exception e) { 
						output = "<html><head><title>Per Unit Page</title>"
							   + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
							   + "</head><body>"
							    + "<div class='card'><h4 class='text-center' style=\\\"color: red;\\>Error while inserting</h4></div>"
								+ "</body></html>"; 
								System.err.println(e.getMessage()); 
							} 
							return output; 
			} 

					
					
					
					
		//Update Contact
		public String updateContact(String DistrictCode, String Description, String ComplainNo , String CustomerServiceNo, String NewConnectionsNo, String EmergencyNo, String Address ) { 
				String output = ""; 
				try { 
					Connection con = connect(); 
						if (con == null) {
							return "<html><head><title>Update Page</title>"
									+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
									+ "</head><body>"
									+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for updating.</h4></div>"
									+ "</body></html>";
						} 
									

						// create a prepared statement
						String query = "UPDATE contact SET Description=?,ComplainNo=?,CustomerServiceNo=?,NewConnectionsNo=?,EmergencyNo=?,Address=? WHERE DistrictCode=?"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
								
									
						// binding values		
						preparedStmt.setString(1, Description); 
						preparedStmt.setString(2, ComplainNo); 
						preparedStmt.setString(3, CustomerServiceNo); 
						preparedStmt.setString(4, NewConnectionsNo); 	
						preparedStmt.setString(5, EmergencyNo); 
						preparedStmt.setString(6, Address); 
					    preparedStmt.setString(7, DistrictCode); 
									
						// execute the statement
						preparedStmt.execute(); 
						con.close(); 
                                     
						output = "<html><head><title>Contact Page</title>"
								+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
								+ "</head><body>"
								+ "<div class='card'><h4 class='text-center' style=\"color: red;\"> Updated Successfully</h4></div>"
								+ "</body></html>";
			
						} 
				
					catch (Exception e) { 
								output = "<html><head><title>Contact Page</title>"
										+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
										+ "</head><body>"
									    + "<div class='card'><h4 class='text-center'>Error while updating</h4></div>"
										+ "</body></html>";
									System.err.println(e.getMessage()); 
								} 
								return output; 
							}
					
					} 
		

package ContactNumbers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class Application {
	
    private Connection connect() { 
	 	Connection con = null; 
	 	try { 
	 		Class.forName("com.mysql.cj.jdbc.Driver"); 

	 		//Provide the correct details: DBServer/DBName, user-name, password 
	 		String url = "jdbc:mysql://127.0.0.1:3306/applications";
	 		String user = "root";
	 		String password = "";
	 		con = DriverManager.getConnection(url, user, password); 
	 	} 
	 	catch (Exception e) {
	 		e.printStackTrace();
	 	} 
	 	return con; 
 	 } 
 
    public String getPdf(){ 
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
  				 
  				    + "<th> ServiceType</th>"
  				    + "<th>Description</th>"
  				     + "<th>Link</th>";
  			
	 
  		  String query = "select * from application"; 
  		  Statement stmt = con.createStatement(); 
  		  ResultSet rs = stmt.executeQuery(query); 
	
  		  // iterate through the rows in the result set
  		   while (rs.next()) { 
  			  String ServiceType = rs.getString("ServiceType"); 
  			  String Description= rs.getString("Description"); 
  			  String Link        = rs.getString("Link"); 

		
  			// Add into the HTML table
  			  output += "<tr><td>"+ServiceType + "</td>"; 
      		  output += "<td>"    +Description+ "</u></td>"; 
      		  output += "<td> <a href="+Link+">Click this link</a> </td>";
		

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
     
      
     	//Delete upoaded PDF
    	public String deletePdf(String ServiceType){  
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
    				String query = "delete from application where ServiceType=?";  
    				PreparedStatement preparedStmt = con.prepareStatement(query); 
      
    				// binding
    				preparedStmt.setString(1,ServiceType); 
     
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
    				output = "<html><head><title>Applications Page</title>" 
    						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>" 
    						+ "</head><body>" 
    						+ "<div class='card'><h4 class='text-center'><marquee>Error while deleting</marquee></h4></div>" 
    						+ "</body></html>"; 
    						System.err.println(e.getMessage());  
    			}  
    			return output;  
    			
    			
    			
    	}			
    			//Insert Application
				public String insertApplication(String ServiceType, String Description, String Link) { 
					      String output = ""; 
					        try { 
					        	Connection con = connect(); 
					        	if (con == null) {
					        		return "<html><head><title>Application Page</title>"
					        				+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					        				+ "</head><body>"
					        				+ "<div class='card'><h4 class='text-center'><marquee>Error while connecting to the database for inserting.</marquee></h4></div>"
					        				+ "</body></html>";
					        	}
						

						// create a prepared statement
						String query = "insert into application(ServiceType,Description,Link)" + " values(?, ?, ?)"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
							// binding values
							preparedStmt.setString(1,ServiceType); 
							preparedStmt.setString(2,Description); 
							preparedStmt.setString(3,Link); 

						
							// execute the statement
							preparedStmt.execute(); 
							con.close(); 
							output = "<html><head><title>Per Unit Page</title>"
									+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
									+ "</head><body>"
									+ "<div class='card'><h4 class='text-center' style=\\\"color: red;\\>Inserted successfully</h4></div>"
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
    
     
     
}

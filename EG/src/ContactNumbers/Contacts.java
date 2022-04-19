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
	
	//Read Per Unit
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
						+ "<th>District</th>"
						+ "<th>Description</th>" 
						+ "<th>Complain</th>" 
						+ "<th>CustomerService</th>" 
						+ "<th>NewConnections</th>"
						+ "<th>Emergency</th>"
						+ "<th>Address</th>"
						+ "<th>Update</th>"
						+ "<th>Remove</th></tr>"; 
				 
				String query = "select * from contact"; 
				Statement stmt = con.createStatement(); 
				ResultSet rs = stmt.executeQuery(query); 
				
				// iterate through the rows in the result set
				while (rs.next()) { 
					String District= rs.getString("District"); 
					String Description= rs.getString("Description"); 
					String  Complain= Integer.toString(rs.getInt("Complain")); 
					String CustomerService =Integer.toString(rs.getInt("CustomerService")); 
					String NewConnections  = Integer.toString(rs.getInt("NewConnections")); 
					String  Emergency= Integer.toString(rs.getInt("Emergency")); 
					String Address = rs.getString("Address");
					
					// Add into the HTML table
					output += "<tr><td>" +District + "</td>"; 
					output += "<td>" +Description + "</td>"; 
					output += "<td>" + Complain+ "</td>"; 
					output += "<td>" +CustomerService + "</td>"; 
					output += "<td>" +NewConnections + "</td>"; 
					output += "<td>" +Emergency+ "</td>"; 
					output += "<td>" +Address+ "</td>"; 
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
					+ "<td><form method='post' action='items.jsp'>"
					+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
					+ "<input name='itemID' type='hidden' value='" + District
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
		
	

		
		
		
		
	
	
}

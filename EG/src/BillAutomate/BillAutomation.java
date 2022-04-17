package BillAutomate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BillAutomation {
	private Connection connect() { 
		Connection con = null; 
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
		 
			//Provide the correct details: DBServer/DBName, user-name, password 
			String url = "jdbc:mysql://127.0.0.1:3306/bill_automate";
			String user = "root";
			String password = "";
			con = DriverManager.getConnection(url, user, password); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return con; 
	} 
	
	
	//Read items
	public String redPerUnit() { 
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "Error while connecting to the database for reading."; 
			} 
				
			// Prepare the HTML table to be displayed
			output = "<html><head><title>Item Page</title>"
					+ "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">"
					+ "</head><body><table class='table' border='1'><tr>"
					+ "<th>Bill Type</th>"
					+ "<th>KWH Charges</th>" 
					+ "<th>Fixed Charges</th>" 
					+ "<th>Fuel Charges</th>" 
					+ "<th>Rebate</th>"
					+ "<th>Tax Amount</th>"
					+ "<th>Total Amount</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>"; 
			 
			String query = "select * from perunit"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			
			// iterate through the rows in the result set
			while (rs.next()) { 
				String billType = rs.getString("billtype"); 
				String KWH = Double.toString(rs.getDouble("kwh")); 
				String Fixed = Double.toString(rs.getDouble("fixed")); 
				String Fuel = Double.toString(rs.getDouble("fuel")); 
				String Rebate = Double.toString(rs.getDouble("rebate")); 
				String Tax = Double.toString(rs.getDouble("tax")); 
				String Total = Double.toString(rs.getDouble("total")); 
				
				// Add into the HTML table
				output += "<tr><td>" + billType + "</td>"; 
				output += "<td>" + KWH + "</td>"; 
				output += "<td>" + Fixed + "</td>"; 
				output += "<td>" + Fuel + "</td>"; 
				output += "<td>" + Rebate + "</td>"; 
				output += "<td>" + Tax + "</td>"; 
				output += "<td>" + Total + "</td>"; 
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
				+ "<td><form method='post' action='items.jsp'>"
				+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
				+ "<input name='itemID' type='hidden' value='" + billType 
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

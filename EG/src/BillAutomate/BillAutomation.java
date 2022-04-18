package BillAutomate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BillAutomation {
	private Connection connect() { 
		Connection con = null; 
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
		 
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
	
	
	//Read Per Unit
	public String redPerUnit() { 
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for reading.</h4></div>"
						+ "</body></html>";
			} 
				
			// Prepare the HTML table to be displayed
			output = "<html><head><title>Per Unit Page</title>"
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
				String billType = rs.getString("type"); 
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
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Error while reading.</h4></div>"
					+ "</body></html>";
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
	
	
	//Search by Bill Type
	public String readPerUnitBySearch(String billtype) { 
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for reading.</h4></div>"
						+ "</body></html>";
			} 
			
			// Prepare the HTML table to be displayed
			output = "<html><head><title>Per Unit Page</title>"
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
			 
			String query = "select * from perunit where type='" + billtype + "'"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			
			// iterate through the rows in the result set
			while (rs.next()) { 
				String billType = rs.getString("type"); 
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
	
	
	//Insert Per Unit
	public String insertPerUnit(String billType, String KWH, String Fixed, String Fuel, String Rebate, String Tax, String Total) { 
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for inserting.</h4></div>"
						+ "</body></html>";
			}
			
			double tot = Double.parseDouble(KWH) + Double.parseDouble(Fixed) + Double.parseDouble(Fuel) + Double.parseDouble(Rebate) + Double.parseDouble(Tax);
			// verify total
			if (tot != Double.parseDouble(Total)) {
				output = "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Total Mismatch</h4></div>"
						+ "</body></html>";
				return output;
			}
			// create a prepared statement
			String query = "insert into perunit(type, kwh, fixed, fuel, rebate, tax, total)" + " values(?, ?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setString(1, billType); 
			preparedStmt.setDouble(2, Double.parseDouble(KWH)); 
			preparedStmt.setDouble(3, Double.parseDouble(Fixed)); 
			preparedStmt.setDouble(4, Double.parseDouble(Fuel)); 
			preparedStmt.setDouble(5, Double.parseDouble(Rebate)); 	
			preparedStmt.setDouble(6, Double.parseDouble(Tax)); 
			preparedStmt.setDouble(7, Double.parseDouble(Total)); 
			
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Inserted successfully</h4></div>"
					+ "</body></html>"; 
		} 
		catch (Exception e) { 
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Error while inserting</h4></div>"
					+ "</body></html>"; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
	
	
	//Update items
	public String updatePerUnit(String billType, String KWH, String Fixed, String Fuel, String Rebate, String Tax, String Total) { 
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for updating.</h4></div>"
						+ "</body></html>";
			} 
			
			double tot = Double.parseDouble(KWH) + Double.parseDouble(Fixed) + Double.parseDouble(Fuel) + Double.parseDouble(Rebate) + Double.parseDouble(Tax);
			// verify total
			if (tot != Double.parseDouble(Total)) {
				output = "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Total Mismatch</h4></div>"
						+ "</body></html>";
				return output;
			}
			
			// create a prepared statement
			String query = "UPDATE perunit SET kwh=?,fixed=?,fuel=?,rebate=?,tax=?,total=? WHERE type=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			// binding values 
			preparedStmt.setDouble(1, Double.parseDouble(KWH)); 
			preparedStmt.setDouble(2, Double.parseDouble(Fixed)); 
			preparedStmt.setDouble(3, Double.parseDouble(Fuel)); 
			preparedStmt.setDouble(4, Double.parseDouble(Rebate)); 
			preparedStmt.setDouble(5, Double.parseDouble(Tax)); 
			preparedStmt.setDouble(6, Double.parseDouble(Total));
			preparedStmt.setString(7, billType);
			
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Updated Successfully</h4></div>"
					+ "</body></html>";
		} 
		catch (Exception e) { 
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Error while updating</h4></div>"
					+ "</body></html>";
			System.err.println(e.getMessage()); 
		} 
		return output; 
	}
	
	
	//Delete Item
	public String deletePerUnit(String billType) { 
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "<html><head><title>Per Unit Page</title>"
						+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
						+ "</head><body>"
						+ "<div class='card'><h4 class='text-center'>Error while connecting to the database for deleting.</h4></div>"
						+ "</body></html>";
			} 
			
			// create a prepared statement
			String query = "delete from perunit where type=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, billType);
			
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Deleted successfully</h4></div>"
					+ "</body></html>";
		}
		catch (Exception e) 
		{ 
			output = "<html><head><title>Per Unit Page</title>"
					+ "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					+ "</head><body>"
					+ "<div class='card'><h4 class='text-center'>Error while deleting</h4></div>"
					+ "</body></html>";
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
}

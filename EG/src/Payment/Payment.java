package Payment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment{
	
	private Connection connect() { 
        Connection con = null; 
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver"); 

            //Provide the correct details: DBServer/DBName, user-name, password 
            String url = "jdbc:mysql://127.0.0.1:3306/Payment";
            String user = "root";
            String password = "n2G0u2B0i@N#u";
            con = DriverManager.getConnection(url, user, password); 
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        return con; 
     } 

	
	   //Insert Payment
    public String insertPayment(String name, String accNumber, String cvv, Date expDate, String phone, String email) { 
              String output = ""; 
                try { 
                    Connection con = connect(); 
                    if (con == null) {
                        return "<html><head><title>Payment Page</title>"
                                + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
                                + "</head><body>"
                                + "<div class='card'><h4 class='text-center'><marquee>Error while connecting to the database for inserting.</marquee></h4></div>"
                                + "</body></html>";
                    }
            

            // create a prepared statement
            String query = "insert into Payment_details(name,accNumber,cvv,expDate,phone,email)" + " values(?, ?, ?, ?, ?, ?)"; 
            PreparedStatement preparedStmt = con.prepareStatement(query); 
                // binding values
                preparedStmt.setString(1, name); 
                preparedStmt.setString(2, accNumber); 
                preparedStmt.setString(3, cvv); 
                preparedStmt.setDate(4, expDate); 
                preparedStmt.setString(5, phone);    
                preparedStmt.setString(6, email); 
            
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
    
    //Read Payment
    public String readPayment(){ 
        String output = ""; 
        try { 
            Connection con = connect(); 
            if (con == null) {
                return "Error while connecting to the database for reading."; 
            } 
      
            // Prepare the HTML table to be displayed
            output = "<html><head><title>Payment</title>"
                    + "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">"
                    + "</head><body><table class='table' border='1'><tr>"
                    + "<th>Name</th>"
                    + "<th>Account Number</th>" 
                    + "<th>CVV</th>" 
                    + "<th>EXP_Date</th>" 
                    + "<th>Phone</th>"
                    + "<th>Email</th>"
                    + "<th>Update</th>"
                    + "<th>Remove</th></tr>"; 
   
            String query = "select * from Payment_details"; 
            Statement stmt = con.createStatement(); 
            ResultSet rs = stmt.executeQuery(query); 
  
            // iterate through the rows in the result set
            while (rs.next()) { 
                String name = rs.getString("name"); 
                String accNumber  = rs.getString("accNumber"); 
                String cvv     = rs.getString("cvv"); 
                Date expDate =rs.getDate("expDate"); 
                String phone  =rs.getString("phone"); 
                String email= rs.getString("email"); 

      
                // Add into the HTML table
                output += "<tr><td>"+name + "</td>"; 
                output += "<td>"    +accNumber + "</td>"; 
                output += "<td>"    + cvv+ "</td>"; 
                output += "<td>"    +expDate + "</td>"; 
                output += "<td>"    +phone + "</td>"; 
                output += "<td>"    +email+ "</td>"; 
                
      
          // buttons
          output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
                      + "<td><form method='post' action='items.jsp'>"
                      + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
                      + "<input name='itemID' type='hidden' value='" + name
                      + "'>" + "</form></td></tr>"; 
            }     
  
            con.close(); 
  
            // Complete the HTML table
            output += "</table></body></html>"; 
        } 
        catch (Exception e) { 
            output = "Error while reading the Payments."; 
            System.err.println(e.getMessage()); 
        } 
        return output;            
      }



}

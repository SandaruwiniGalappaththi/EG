package Payment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat; 

public class Online_payment {
	
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
	
	//Update Payment
    public String payOnline(String paymentID, String name, String accNumber, String cvv , String expDate, String phone, String email) { 
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
                    String query = "UPDATE Payment_details SET name=?,accNumber=?,cvv=?,expDate=?,phone=?,email=? WHERE paymentID=?"; 
                    PreparedStatement preparedStmt = con.prepareStatement(query); 
                    
                    
              
                
                    // binding values
                
                    preparedStmt.setString(1, name); 
                    preparedStmt.setString(2, accNumber); 
                    preparedStmt.setString(3, cvv); 
                    preparedStmt.setDate(4, java.sql.Date.valueOf(expDate));    
                    preparedStmt.setString(5, phone); 
                    preparedStmt.setString(6, email); 
                    preparedStmt.setString(7, paymentID); 

                   
                    
                    // execute the statement
                    preparedStmt.execute(); 
                    con.close(); 
                     
                    output = "<html><head><title>Payment Page</title>"
                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
                            + "</head><body>"
                            + "<div class='card'><h4 class='text-center' style=\"color: red;\"> Updated Successfully</h4></div>"
                            + "</body></html>";

                } 
                catch (Exception e) { 
                    output = "<html><head><title>Payment Page</title>"
                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
                            + "</head><body>"
                            + "<div class='card'><h4 class='text-center'>Error while updating</h4></div>"
                            + "</body></html>";
                    System.err.println(e.getMessage()); 
                } 
                return output; 
            }

}

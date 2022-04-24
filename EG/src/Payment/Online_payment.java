package Payment;
import java.math.BigDecimal;
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
            String password = "sandaru@1S";
            con = DriverManager.getConnection(url, user, password); 
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        return con; 
     } 
	
	//Update Payment
    public String payOnline(int cusId, int AccNo, String amount) { 
                String output = ""; 
                double newbalance = 0;
                double newremain = 0;
                try { 
                	
                	double amount1 = Double.parseDouble(amount);
                    Connection con = connect(); 
                    if (con == null) {
                        return "<html><head><title>Update Page</title>"
                                + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
                                + "</head><body>"
                                + "<div class='card'><h4 class='text-center'>Error while connecting to the database for updating.</h4></div>"
                                + "</body></html>";
                    } 
                    String query = "select * from accinfo where cusID= '"+ cusId +"'"; 
                    Statement stmt = con.createStatement(); 
                    ResultSet rs = stmt.executeQuery(query); 
                    System.out.println(cusId);
                    // iterate through the rows in the result set
                    while (rs.next()) { 
                    	int cusID = rs.getInt("cusID");
                        int accNumber  = rs.getInt("accNo"); 
                        double balance  =rs.getDouble("balance"); 
                          if (balance < amount1) {
                        	  
                        	  return "account balance is insufficient";
                          }
                          newbalance = balance-amount1;
                          
                          String query2 = "update accinfo SET balance=? WHERE accNo=?"; 
                          PreparedStatement preparedStmt = con.prepareStatement(query2); 
                         // binding values
                          preparedStmt.setDouble(1, newbalance); 
                          System.out.println(newbalance);
                          preparedStmt.setInt(2, accNumber); 
                         // execute the statement
                          preparedStmt.execute(); 
                          
                          String query3 = "select * from eg_acc_details where EG_ID= '"+ cusId +"'"; 
                          Statement stmt3 = con.createStatement(); 
                          ResultSet rs3 = stmt3.executeQuery(query3); 
                          
                          // iterate through the rows in the result set
                          while (rs3.next()) { 
                              double remain  =rs3.getDouble("remainingAmount"); 
                                if (balance < amount1) {
                              	  
                              	  return "account balance is insufficient";
                                }
                                System.out.println(remain);
                                newremain = remain-amount1;
                          
                          String query4 = "update eg_acc_details SET remainingAmount=? WHERE EG_ID=?"; 
                          PreparedStatement preparedStmt1 = con.prepareStatement(query4); 
                         // binding values
                          preparedStmt1.setDouble(1, newremain); 
                          preparedStmt1.setInt(2, cusId); 
                         // execute the statement
                          preparedStmt1.execute(); 
                        
                    }
                    }
                    // create a prepared statement
                   
                   // preparedStmt.setString(3, cvv); 
                   // preparedStmt.setDate(4, java.sql.Date.valueOf(expDate));    
                   // preparedStmt.setString(5, phone); 
                   // preparedStmt.setString(6, email); 
                   // preparedStmt.setString(7, paymentID); 

                   
                    
                    
                    con.close(); 
                     
                    output = "<html><head><title>Payment Page</title>"
                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
                            + "</head><body>"
                            + "<div class='card'><h4 class='text-center' style=\"color: red;\">payment successfull</h4></div>"
                            + "</body></html>";

                } 
                catch (Exception e) { 
                    output = "<html><head><title>Payment Page</title>"
                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
                            + "</head><body>"
                            + "<div class='card'><h4 class='text-center'>Error while paying</h4></div>"
                            + "</body></html>";
                    System.err.println(e.getMessage()); 
                } 
                return output; 
            }

}

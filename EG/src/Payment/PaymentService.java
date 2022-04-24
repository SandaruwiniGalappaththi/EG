package Payment;
import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Payments")
public class PaymentService {
	
	Payment payment_obj = new Payment();
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertPayment(@FormParam("name") String name, 
							 @FormParam("accNumber") String accNumber, 
							 @FormParam("cvv") String cvv,
							 @FormParam("expDate") Date expDate, 
							 @FormParam("phone") String phone, 
							 @FormParam("email") String email) { 
		String output = payment_obj.insertPayment(name,accNumber,cvv,expDate,phone,email); 
		return output;
	}
	
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String getPayment() {		
	return payment_obj.readPayment();	
	}
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updatePayment(String paymentData) { 
		//Convert the input string to a JSON object 
		JsonObject paymentOb = new JsonParser().parse(paymentData).getAsJsonObject(); 
		
	//Read the values from the JSON object
	String paymentID= paymentOb.get("paymentID").getAsString();
	String name= paymentOb.get("name").getAsString(); 
	String accNumber= paymentOb.get("accNumber").getAsString(); 
	String cvv= paymentOb.get("cvv").getAsString(); 
	String expDate= paymentOb.get("expDate").getAsString(); 
	String phone= paymentOb.get("phone").getAsString(); 
	String email= paymentOb.get("email").getAsString();
	
	String output = payment_obj.updatePayment(paymentID,name,accNumber,cvv,expDate,phone,email); 
	return output;
	
	 }
	
	
	@DELETE 
 	@Path("/")  
 	@Consumes(MediaType.APPLICATION_XML)  
 	@Produces(MediaType.TEXT_PLAIN)  
 	public String deletePayment(String paymentData) {  
	//Convert the input string to an XML document 
	Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());  
    
  	//Read the value from the element <billType> 
  	String PaymentID = doc.select("PaymentID").text();  
  	String output = payment_obj.deletePaymentDetails(PaymentID);  
  	return output;  
  } 
	
	/*
	//online bill payment service
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String payOnline(String paymentData) { 
		//Convert the input string to a JSON object 
		JsonObject paymentOb = new JsonParser().parse(paymentData).getAsJsonObject(); 
		
	//Read the values from the JSON object
	String paymentID= paymentOb.get("paymentID").getAsString();
	String name= paymentOb.get("name").getAsString(); 
	String accNumber= paymentOb.get("accNumber").getAsString(); 
	String cvv= paymentOb.get("cvv").getAsString(); 
	String expDate= paymentOb.get("expDate").getAsString(); 
	String phone= paymentOb.get("phone").getAsString(); 
	String email= paymentOb.get("email").getAsString();
	
	String output = payment_obj.updatePayment(paymentID,name,accNumber,cvv,expDate,phone,email); 
	return output;
	
	 }
	*/

}

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
	
	

}

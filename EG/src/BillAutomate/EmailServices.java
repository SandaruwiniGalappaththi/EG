package BillAutomate;

import javax.ws.rs.Consumes;
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

@Path("/billmail")
public class EmailServices {
	Email email = new Email();
	
	
	// send email to all users
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String sendEmailToAll() {
		return email.sendEmailToAll();
	}
	
	
	// send email to a single user
	@GET
	@Path("/singlemail")
	@Produces(MediaType.TEXT_HTML)
	public String sendEmail(String itemData) {
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
		//Read the value from the element <billType>
		String accNo = doc.select("accNo").text(); 
		String output = email.sendEmail(accNo);
		return output;
	}
	
	
	// update rate details
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateRates(String itemData) { 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
		
		//Read the values from the JSON object
		String isres = itemObject.get("isres").getAsString(); 
		String basic = itemObject.get("basic").getAsString(); 
		String twenty = itemObject.get("twenty").getAsString(); 
		String fifty = itemObject.get("fifty").getAsString(); 
		String ninty = itemObject.get("ninty").getAsString(); 
		String output = email.updateRates(isres, basic, twenty, fifty, ninty); 
		return output; 
	}
	
	
	// insert bill
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertBill(@FormParam("Year") String year, 
							 @FormParam("Month") String month, 
							 @FormParam("Reading") String reading,
							 @FormParam("Account Number") String accno) { 
		String output = email.insertBill(year, month, reading, accno); 
		return output; 
	}
}

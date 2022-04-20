package ContactNumbers;

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

import BillAutomate.BillAutomation;



@Path("/Contacts")
public class ContactServices {
	
	Contacts contact = new Contacts();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String getContact() {		
		return contact.readContact();	
	}
	
	

	 @DELETE 
	 @Path("/")  
	 @Consumes(MediaType.APPLICATION_XML)  
	 @Produces(MediaType.TEXT_PLAIN)  
	 public String deleteNo(String itemData) {  
	  //Convert the input string to an XML document 
	  Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());  
	    
	  //Read the value from the element <billType> 
	  String District = doc.select("District").text();  
	  String output = contact.deleteContact(District);  
	  return output;  
	 } 
	


		@POST
		@Path("/") 
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String insertContact(@FormParam("District") String District, 
								 @FormParam("Description") String Description, 
								 @FormParam("Complain") String Complain,
								 @FormParam("CustomerService") String CustomerService, 
								 @FormParam("NewConnections") String NewConnections, 
								 @FormParam("Emergency") String Emergency,
								 @FormParam("Address") String Address) { 
			String output = contact.insertContact(District,Description,Complain,CustomerService,NewConnections,Emergency,Address); 
			return output; 
		}

}
	






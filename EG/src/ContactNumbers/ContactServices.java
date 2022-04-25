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

 


@Path("/Contacts")
public class ContactServices {
	
	Contacts contact = new Contacts();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String getContact() {		
		return contact.readContact();	
	}
	
	

	@GET
	@Path("/searchcontact")
	@Produces(MediaType.TEXT_HTML) 
	public String searchContact(String itemData) {		
			//Convert the input string to an XML document
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			//Read the value from the element <ContactType>
			String ContactType = doc.select("DistrictCode").text(); 
			
			String output = contact.searchContact(ContactType); 
			return output; 	
	 }
		
		
		
	@DELETE 
 	@Path("/")  
 	@Consumes(MediaType.APPLICATION_XML)  
 	@Produces(MediaType.TEXT_PLAIN)  
 	public String deleteNo(String itemData) {  
	 		//Convert the input string to an XML document 
	 		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());  
	    
	 		//Read the value from the element <billType> 
	 		String DistrictCode = doc.select("DistrictCode").text();  
	 		String output = contact.deleteContact(DistrictCode);  
	 		return output;  
	  } 
	


	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertContact(@FormParam("DistrictCode") String DistrictCode, 
				 			@FormParam("Description") String Description, 
				 			@FormParam("ComplainNo") String ComplainNo,
				 			@FormParam("CustomerServiceNo") String CustomerServiceNo, 
				 			@FormParam("NewConnectionsNo") String NewConnectionsNo, 
				 			@FormParam("EmergencyNo") String EmergencyNo,
				 			@FormParam("Address") String Address) 
		
	    {
		 //inserting validations
		if(DistrictCode.isEmpty() || Description.isEmpty()||ComplainNo.isEmpty() ||CustomerServiceNo.isEmpty()||NewConnectionsNo.isEmpty()||EmergencyNo.isEmpty()|| Address.isEmpty())
			  return "complain fields must be filled out";
	
		 else if(ComplainNo.length()!=10) {
		 return "ComplainNo length must be 10 characters long";
		 }
		 else if(CustomerServiceNo.length()!=10) {
		 return "Customer Service No length must be 10 characters long";
		 }
		 else if(NewConnectionsNo.length()!=10) {
		 return "New Connection Length must be 10 characters long";
		 }
		 else if(EmergencyNo.length()!=10) {
		 return "Emergency length must be 10 characters long";
		 }
	
			String output = contact.insertContact(DistrictCode,Description,ComplainNo,CustomerServiceNo,NewConnectionsNo,EmergencyNo,Address); 
			return output;
	    }
		
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) { 
			//Convert the input string to a JSON object 
			JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
			//Read the values from the JSON object
			String DistrictCode= itemObject.get("DistrictCode").getAsString(); 
			String Description= itemObject.get("Description").getAsString(); 
			String ComplainNo= itemObject.get("ComplainNo").getAsString(); 
			String CustomerServiceNo= itemObject.get("CustomerServiceNo").getAsString(); 
			String NewConnectionsNo= itemObject.get("NewConnectionsNo").getAsString(); 
			String EmergencyNo= itemObject.get("EmergencyNo").getAsString(); 
			String Address= itemObject.get("Address").getAsString();
			
			 //inserting validations
			if(DistrictCode.isEmpty() || Description.isEmpty()||ComplainNo.isEmpty() ||CustomerServiceNo.isEmpty()||NewConnectionsNo.isEmpty()||EmergencyNo.isEmpty()|| Address.isEmpty())
				  return "fields must be filled out";
		
			 else if(ComplainNo.length()!=10) {
			 return "Complain No length must be 10 characters long";
			 }
			 else if(CustomerServiceNo.length()!=10) {
			 return "Customer Service No length must be 10 characters long";
			 }
			 else if(NewConnectionsNo.length()!=10) {
			 return "New Connection Length must be 10 characters long";
			 }
			 else if(EmergencyNo.length()!=10) {
			 return "Emergency length must be 10 characters long";
			 }
			String output = contact.updateContact(DistrictCode,Description,ComplainNo,CustomerServiceNo,NewConnectionsNo,EmergencyNo,Address); 
			return output;
		
		 }
	
	}


	






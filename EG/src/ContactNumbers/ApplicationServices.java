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

@Path("/Application")
public class ApplicationServices {

	Application apply = new Application();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String getContact() {		
	return apply.getPdf();	
	}
	
	
 	@DELETE 
 	@Path("/")  
 	@Consumes(MediaType.APPLICATION_XML)  
 	@Produces(MediaType.TEXT_PLAIN)  
 	public String deletePdf(String itemData) {  
	//Convert the input string to an XML document 
	Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());  
    
  	//Read the value from the element <ServiceType> 
  	String ServiceType = doc.select("ServiceType").text();  
  	String output = apply.deletePdf(ServiceType);  
  	return output;  
    } 
 	
 	
    @PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateApply(String itemData) { 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
		//Read the values from the JSON object
		String ServiceType= itemObject.get("ServiceType").getAsString(); 
		String Description= itemObject.get("Description").getAsString(); 
		String Link= itemObject.get("Link").getAsString(); 
		String output = apply.updateApply(ServiceType,Description,Link); 
		return output;
	
	 }
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertContact(@FormParam("ServiceType") String ServiceType, 
				 @FormParam("Description") String Description,
				 @FormParam("Link") String Link) { 
		String output = apply.insertApplication(ServiceType,Description,Link); 
		return output; 
	}
}

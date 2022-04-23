package ContactNumbers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

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
 	public String deleteNo(String itemData) {  
	//Convert the input string to an XML document 
	Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());  
    
  	//Read the value from the element <ContactType> 
  	String ServiceType = doc.select("ServiceType").text();  
  	String output = apply.deletePdf(ServiceType);  
  	return output;  
  } 
	
	
	
}

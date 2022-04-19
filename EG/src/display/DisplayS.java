package display;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
@Path("/displayBySearch")
public class DisplayS {
	DisplayM d = new DisplayM();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
public String readzone()
{          
		return d.readzone();
}
	
	
	
	@GET
	@Path("/searchAcc")
	@Produces(MediaType.TEXT_HTML) 
	public String read(String itemData) {		
		//Convert the input string to an XML document
		try {
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			//Read the value from the element <accountNo>
			String accountNo = doc.select("accountNo").text(); 
			String output = d.read(accountNo); 
			return output;
		}catch(Exception e) {
		String output = "error";
		return output;
		}
		
	}
	
	
	
	
	
	@GET
	@Path("/searchZone")
	@Produces(MediaType.TEXT_HTML) 
	public String readByZone(String itemData) {		
		//Convert the input string to an XML document
		try {
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			//Read the value from the element <accountNo>
			String zone = doc.select("zone").text(); 
			String output = d.readByZone(zone); 
			return output;
		}catch(Exception e) {
		String output = "error";
		return output;
		}
		
	}
	
	
	
	
	
	
	@GET
	@Path("/searchLocation")
	@Produces(MediaType.TEXT_HTML) 
	public String readByLocation(String itemData) {		
		//Convert the input string to an XML document
		try {
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			//Read the value from the element <accountNo>
			String location = doc.select("location").text(); 
			String output = d.readByLocation(location); 
			return output;
		}catch(Exception e) {
		String output = "error";
		return output;
		}
		
	}
	
}

package display;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
@Path("/displayBySearch")
public class TimeTableService {
	TimeTable d = new TimeTable();
    //function for search power cut schedules by giving account number of the user
	@GET
	@Path("/searchAcc")
	@Produces(MediaType.TEXT_HTML) 
	public String readByAcc(String itemData) {		
		//Convert the input string to an XML document
		try {
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			//Read the value from the element <accountNo>
			String accountNo = doc.select("accountNo").text(); 
			String output = d.readByAcc(accountNo); //pass account number to readByAcc method in TimeTable class
			return output;
		}catch(Exception e) {
		String output = "error";
		return output;
		}
		
	}
	
	
	
	
	//function for search power cut schedules by giving zone of the user
	@GET
	@Path("/searchZone")
	@Produces(MediaType.TEXT_HTML) 
	public String readByZone(String itemData) {		
		//Convert the input string to an XML document
		try {
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			//Read the value from the element <zone>
			String zone = doc.select("zone").text(); 
			String output = d.readByZone(zone); //pass zone letter to readByZone method in TimeTable class
			return output;
		}catch(Exception e) {
		String output = "error";
		return output;
		}
		
	}
	
	
	
	
	
	//function for search power cut schedules by giving location of the user
	@GET
	@Path("/searchLocation")
	@Produces(MediaType.TEXT_HTML) 
	public String readByLocation(String itemData) {		
		//Convert the input string to an XML document
		try {
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			//Read the value from the element <accountNo>
			String location = doc.select("location").text(); 
			String output = d.readByLocation(location); //pass location to readByLocation method in TimeTable class
			return output;
		}catch(Exception e) {
		String output = "error";
		return output;
		}
		
	}
	
}

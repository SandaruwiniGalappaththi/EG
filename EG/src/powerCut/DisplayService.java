package powerCut;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import powerCutModel.Schedule;
@Path("/zone")
public class DisplayService {
	
	Schedule schedule1 = new Schedule();
	/*@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String read(String itemData) {		
		//Convert the input string to an XML document
		try {
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			 System.out.println("hj");
			//Read the value from the element <billType>
			String accountNo = doc.select("accountNo").text(); 
			String output = schedule1.read(accountNo); 
			return output;
		}catch(Exception e) {
		String output = "error";
		return output;
		}
		
	}*/
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
public String readzone()
{          
		return schedule1.readzone();
}

}

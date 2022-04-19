package powerCut;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import powerCutModel.PowerCutMail;
@Path("/mail")
public class PowerCutMailService {
    PowerCutMail m = new PowerCutMail();

	
	/*@GET
	@Path("/mailSent")
	@Produces(MediaType.TEXT_HTML) 
	public String sendPowerCutMail(String itemData) {		
		//Convert the input string to an XML document
		try {
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			 System.out.println("hj");
			//Read the value from the element <accountNo>
			String repMail = doc.select("repMail").text(); 
		    m.sendPowerCutMail(repMail); 
			return "successfully sent";
		}catch(Exception e) {
		String output = "error";
		return output;
		}
	}
	*/
	
	
	@GET
	@Path("/all")
	@Produces(MediaType.TEXT_HTML)
	public String sendToRegistertedUsers() {
		return m.sendToRegistertedUsers();
	}
	
}

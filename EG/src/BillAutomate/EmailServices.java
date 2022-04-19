package BillAutomate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

@Path("/billmail")
public class EmailServices {
	Email email = new Email();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String sendEmailToAll() {
		return email.sendEmailToAll();
	}
	
	
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
}

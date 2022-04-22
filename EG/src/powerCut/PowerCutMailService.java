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
    PowerCutMail mail = new PowerCutMail();
	
	//function to send power cut alerts through mails for registered users 
	@GET
	@Path("/all")
	@Produces(MediaType.TEXT_HTML)
	public String sendToRegistertedUsers() {
		return mail.sendToRegistertedUsers();//calling the function in PowerCutMail class
	}
	
}

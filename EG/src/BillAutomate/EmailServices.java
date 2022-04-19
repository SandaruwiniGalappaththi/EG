package BillAutomate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/billmail")
public class EmailServices {
	Email email = new Email();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String sendEmailToAll() {
		return email.sendEmailToAll();
	}
}

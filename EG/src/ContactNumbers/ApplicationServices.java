package ContactNumbers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Application")
public class ApplicationServices {

	Application apply = new Application();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String getContact() {		
	return apply.getPdf();	
	}
}

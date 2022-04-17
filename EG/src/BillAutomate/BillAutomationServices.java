package BillAutomate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/billautomate")
public class BillAutomationServices {

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String readPerUnit() {
		BillAutomation bill = new BillAutomation();
		return bill.redPerUnit();	
	}
}

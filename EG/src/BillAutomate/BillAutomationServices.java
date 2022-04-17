package BillAutomate;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/billautomate")
public class BillAutomationServices {
	BillAutomation bill = new BillAutomation();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String readPerUnit() {		
		return bill.redPerUnit();	
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertPerUnit(@FormParam("Bill Type") String billType, 
							 @FormParam("KWH Charges") String KWH, 
							 @FormParam("Fixed Charges") String Fixed,
							 @FormParam("Fuel Charges") String Fuel, 
							 @FormParam("Rebate") String Rebate, 
							 @FormParam("Tax Amount") String Tax,
							 @FormParam("Total Amount") String Total) { 
		String output = bill.insertPerUnit(billType, KWH, Fixed, Fuel, Rebate, Tax, Total); 
		return output; 
	}
}

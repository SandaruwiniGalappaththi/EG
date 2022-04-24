package display;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/setAlerts")
public class AlertService {
	
	Alert alert = new Alert();
	//function to set alerts
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
public String setAlert(@FormParam("accountNo") String accountNo,
		 @FormParam("email") String email) throws ParseException
{   
		                String output = "";
						output = alert.checkVerification(accountNo,email);//calling method to verify email and account no
						
						return output;
				
						

}
	
}

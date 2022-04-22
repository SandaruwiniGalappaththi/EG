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
public class ZoneService {
	
	Schedule schedule1 = new Schedule();
    //function for get all zone information
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
public String readzone()
{          
		return schedule1.readzone();//calling function in schedule class
}

}

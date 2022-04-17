package powerCut;
import powerCutModel.Schedule;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

//For REST Service
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/PowerCutSchedule")
public class ScheduleService {



	Schedule schedule = new Schedule();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readSchedule()
	 {
	  return schedule.readSchedule();
	}
}
	
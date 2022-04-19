package powerCut;
import powerCutModel.Schedule;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

@Path("/schedule")
public class ScheduleService {

		Schedule schedule = new Schedule();
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
	public String readSchedule()
{          
			return schedule.readSchedule();
}
		@GET
		@Path("/searchAcc")
		@Produces(MediaType.TEXT_HTML) 
		public String read(String itemData) {		
			//Convert the input string to an XML document
			try {
				Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
				 System.out.println("hj");
				//Read the value from the element <accountNo>
				String accountNo = doc.select("accountNo").text(); 
				String output = schedule.read(accountNo); 
				System.out.println(output);
				return output;
			}catch(Exception e) {
			String output = "error";
			return output;
			}
		}
		
		
	/*{//Convert the input string to an XML document
			 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
                 System.out.println("ghjkl");
				//Read the value from the element <accountNo>
				 String accountNo = doc.select("accountNo").text();
				 String output = schedule.read(accountNo);
				return output;
	}*/
	    
	
	
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
	public String insertSchedule(@FormParam("location") String location,
			 @FormParam("start") String start,
			 @FormParam("end") String end,
			 @FormParam("onDate") String onDate) throws ParseException,DateTimeParseException,NullPointerException
	{   
			String output = "";
			Boolean startOk = false,onDateOk = false,endOk= false;
			System.out.println("first entry");
	
	try{
        	LocalTime.parse(start);
        	System.out.println("Valid time string: " + start);
        	startOk = true;
    }
	catch(DateTimeParseException|NullPointerException e) {
    	
        	System.out.println("Invalid time string: " + start);
        	output = "INCORRECT TIME FORMAT.PLEASE USE HH:MM FORMAT";
    }
	
	try{
			LocalTime.parse(end);
			System.out.println("Valid time string: " + end);
			endOk = true;
    }
	catch(DateTimeParseException|NullPointerException e) {
    	
        	System.out.println("Invalid time string: " + end);
        	output = "INCORRECT TIME FORMAT.PLEASE USE HH:MM FORMAT";
    }	
	
	
	    		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
	    		sdfrmt.setLenient(false);
	 
			    /* Create Date object
			     * parse the string into date 
		             */
	try{  
	    
	        Date javaDate = sdfrmt.parse(onDate); 
	        LocalDate localDate = javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        int year  = localDate.getYear();
	        int month = localDate.getMonthValue();
	        int day   = localDate.getDayOfMonth();
	        	System.out.println(year+" " + month+" " + day);
	        	System.out.println(onDate+" is valid date format");
	        onDateOk = true;
	       
	  }
	    /* Date format is invalid */
       catch(ParseException e)
	 {
	        System.out.println(onDate+" is Invalid Date format");
	        output = "INCORRECT DATE FORMAT.PLEASE USE DD/MM/YYYY FORMAT";
	       
	 }
	    try {
	    	Duration timeElapsed = Duration.between(LocalTime.parse(start), LocalTime.parse(end));
	    	System.out.println(timeElapsed.toMinutes());
	    	if(timeElapsed.toMinutes() < 0) {
	    	  	startOk = false;
	    	 	endOk = false;
	    	 	output = "START TIME IS GREATER THAN END TIME";
	    	    }
	    }catch(DateTimeParseException|NullPointerException e) {
	    	
	    	System.out.println();
	    }
	   
	   
	    
	    if(startOk == true && endOk ==true && onDateOk ==true  ) {
	    		Date date = Calendar.getInstance().getTime();  
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
	        	String createdDate = dateFormat.format(date);  
	        	System.out.println(createdDate);  
	        	output = schedule.insertschedule(location,start,end,onDate,createdDate);
	  	  
	    }
	    
	return output;
    
	}
	
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateSchedule(String itemData)
		{
		//Convert the input string to a JSON object
		 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		//Read the values from the JSON object
		 String ID = itemObject.get("ID").getAsString();
		 String location = itemObject.get("location").getAsString();
		 String start = itemObject.get("start").getAsString();
		 String end = itemObject.get("end").getAsString();
		 String onDate = itemObject.get("onDate").getAsString();
		 
		 
			String output = "";
		    Boolean startOk = false,onDateOk = false,endOk= false;
		
		
		try{
	        LocalTime.parse(start);
	        System.out.println("Valid time string: " + start);
	        startOk = true;
	    }
		catch (DateTimeParseException|NullPointerException e) {
	    	
	        System.out.println("Invalid time string: " + start);
	        output = "INCORRECT TIME FORMAT.PLEASE USE HH:MM FORMAT";
	    }
		
		try{
	        LocalTime.parse(end);
	        System.out.println("Valid time string: " + end);
	        endOk = true;
	        
	    }
		catch (DateTimeParseException|NullPointerException e) {
	    	
	        System.out.println("Invalid time string: " + end);
	        output = "INCORRECT TIME FORMAT.PLEASE USE HH:MM FORMAT";
	    }
		
		
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		    sdfrmt.setLenient(false);
		 
		    /* Create Date object
		     * parse the string into date 
	             */
		    try{  
		    
		        Date javaDate = sdfrmt.parse(onDate); 
		       // System.out.println(start2+" is valid time format");
		        LocalDate localDate = javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		        int year  = localDate.getYear();
		        int month = localDate.getMonthValue();
		        int day   = localDate.getDayOfMonth();
		        System.out.println(year+" " + month+" " + day);
		        System.out.println(onDate+" is valid date format");
		        onDateOk = true;
		       
		    }
		    /* Date format is invalid */
		    catch (ParseException e)
		    {
		        System.out.println(onDate+" is Invalid Date format");
		        output = "INCORRECT DATE FORMAT.PLEASE USE DD/MM/YYYY FORMAT";
		       
		    }
		    try {
		    	Duration timeElapsed = Duration.between(LocalTime.parse(start), LocalTime.parse(end));
		    	System.out.println(timeElapsed.toMinutes());
		    	if(timeElapsed.toMinutes() < 0) {
		    	  	startOk = false;
		    	 	endOk = false;
		    	 	output = "START TIME IS GREATER THAN END TIME";
		    	    }
		    }catch(DateTimeParseException|NullPointerException e) {
		    	
		    	System.out.println();
		    }
		   
		    
		    
		    if(startOk == true && endOk ==true && onDateOk ==true  ) {
		    	Date date = Calendar.getInstance().getTime();  
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
	        	String createdDate = dateFormat.format(date);  
	        	System.out.println(createdDate);  
		    	output = schedule.updateSchedule(ID, location, start, end, onDate,createdDate);
		  	  
		    }
		    
		return output;
		
		}
		
		
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deletetimes(String itemData)
		{
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		//Read the value from the element <itemID>
		 String ID = doc.select("ID").text();
		 String output = schedule.deleteSchedule(ID);
		return output;
		}
	
	    
}
	
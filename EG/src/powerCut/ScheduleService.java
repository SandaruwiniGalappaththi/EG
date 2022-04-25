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
		
		//function to get all power cut schedules
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readSchedule()
   {          
			return schedule.readSchedule();//calling readSchedule function in Schedule class
   }
	    
	
		//function to add new power cut schedules
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
							Boolean startOk = false,onDateOk = false,endOk= false,locationOk=false;
	
							try{//check input start time is valid time
					        	LocalTime.parse(start);
					        	System.out.println("Valid time string: " + start);
					        	startOk = true;
							}catch(DateTimeParseException|NullPointerException e) {
    	                        //if start time is invalid, give error message to use correct format
					        	System.out.println("Invalid time string: " + start);
					        	output = "<html><head><title>Payment Page</title>"
			                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
			                            + "</head><body>"
			                            + "<div class='card'><h4 class='text-center'style='color:red;'>Incorrect time format.Please use HH:MM format</h4></div>"
			                            + "</body></html>";
							}
	
							try{//check input end time is valid time
								LocalTime.parse(end);
								System.out.println("Valid time string: " + end);
								endOk = true;
							}catch(DateTimeParseException|NullPointerException e) {
								//if end time is invalid, give error message to use correct format
					        	System.out.println("Invalid time string: " + end);
					        	output = "<html><head><title>Payment Page</title>"
			                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
			                            + "</head><body>"
			                            + "<div class='card'><h4 class='text-center'style='color:red;'>Incorrect time format.Please use HH:MM format</h4></div>"
			                            + "</body></html>";
							}	
	
	
		    		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");//create date format
		    		sdfrmt.setLenient(false);
		
		    				try{  
	                               //check input date format is correct or not
							        Date javaDate = sdfrmt.parse(onDate); 
							        LocalDate localDate = javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							        int year  = localDate.getYear();//getting year to check
							        int month = localDate.getMonthValue();//getting month to check
							        int day   = localDate.getDayOfMonth();//getting day to check
							        	System.out.println(year+" " + month+" " + day);
							        	System.out.println(onDate+" is valid date format");
							        onDateOk = true;
							        
		    				}catch(ParseException e)
		    				{       
								   // if date format is invalid, give error message to use correct format
							        System.out.println(onDate+" is Invalid Date format");
							        output = "<html><head><title>Payment Page</title>"
				                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
				                            + "</head><body>"
				                            + "<div class='card'><h4 class='text-center'style='color:red;'>Incorrect date format.Please use DD/MM/YYYY format</h4></div>"
				                            + "</body></html>";
	       
		    				}
		    				try {  //getting the time duration
							    	Duration timeElapsed = Duration.between(LocalTime.parse(start), LocalTime.parse(end));
							    	
							    	if(timeElapsed.toMinutes() < 0) {//checking start time is greater than end time
							    	  	startOk = false;
							    	 	endOk = false;
							    	 	output = "<html><head><title>Payment Page</title>"
					                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
					                            + "</head><body>"
					                            + "<div class='card'><h4 class='text-center'style='color:red;'>Start time is greater than end time</h4></div>"
					                            + "</body></html>";
							    		}
		    				}catch(DateTimeParseException|NullPointerException e) {
	    	
		    					System.out.println();
		    				}
	   
	                        
		    				try {//checking input location is available location
		    					 locationOk = schedule.findLocation(location);//find location is a valid one using findLocation function in Schedule class
		    					if(locationOk == false) {
		    						output = "<html><head><title>Payment Page</title>"
				                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
				                            + "</head><body>"
				                            + "<div class='card'><h4 class='text-center'style='color:red;'>Enter valid location</h4></div>"
				                            + "</body></html>";//otherwise give error message
		    					}
		    					
		    				}catch(Exception e) {
		    					
		    					
		    				}
	                        //if only start time,end time and date is in correct format that schedule will enter to database
		    				if(startOk == true && endOk ==true && onDateOk ==true && locationOk ==true ) {
		    					
				    		Date date = Calendar.getInstance().getTime();  //getting the current time
				        	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  //set format
				        	String createdDate = dateFormat.format(date);  
				        	output = schedule.insertschedule(location,start,end,onDate,createdDate);//calling insertSchedule function in Schedule class
	  	  
		    	}
	    
		    return output;
    
	}
	    //function to update schedules
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
							    Boolean startOk = false,onDateOk = false,endOk= false,locationOk=false;
							
				
					 try{//check input start time is valid time
					        LocalTime.parse(start);
					        System.out.println("Valid time string: " + start);
					        startOk = true;
					 }catch (DateTimeParseException|NullPointerException e) {
						  //if start time is invalid, give error message to use correct format
					        System.out.println("Invalid time string: " + start);
					        output = "<html><head><title>Payment Page</title>"
		                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
		                            + "</head><body>"
		                            + "<div class='card'><h4 class='text-center'style='color:red;'>Incorrect time format.Please use HH:MM format</h4></div>"
		                            + "</body></html>";
					 }
				
					try{  //check input end time is valid time
					        LocalTime.parse(end);
					        System.out.println("Valid time string: " + end);
					        endOk = true;
			        
					}catch (DateTimeParseException|NullPointerException e) {
						//if end time is invalid, give error message to use correct format
					        System.out.println("Invalid time string: " + end);
					        output =  "<html><head><title>Payment Page</title>"
		                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
		                            + "</head><body>"
		                            + "<div class='card'><h4 class='text-center'style='color:red;'>Incorrect time format.Please use HH:MM format</h4></div>"
		                            + "</body></html>";
					}
				
				
				    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");//create date format
				    sdfrmt.setLenient(false);
				 
				   
				    try{  
				    	//check input date format is correct or not
						        Date javaDate = sdfrmt.parse(onDate); 
						        LocalDate localDate = javaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						        int year  = localDate.getYear();//getting year to check
						        int month = localDate.getMonthValue();//getting month to check
						        int day   = localDate.getDayOfMonth();//getting day to check
						        System.out.println(year+" " + month+" " + day);
						        System.out.println(onDate+" is valid date format");
						        onDateOk = true;
				   
				    }catch (ParseException e)
				    {           // if date format is invalid, give error message to use correct format
						        System.out.println(onDate+" is Invalid Date format");
						        output =  "<html><head><title>Payment Page</title>"
			                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
			                            + "</head><body>"
			                            + "<div class='card'><h4 class='text-center'style='color:red;'>Incorrect time format.Please use HH:MM format</h4></div>"
			                            + "</body></html>";
				       
				    }
				    try {      //getting the time duration
						    	Duration timeElapsed = Duration.between(LocalTime.parse(start), LocalTime.parse(end));
						    	System.out.println(timeElapsed.toMinutes());
						    	if(timeElapsed.toMinutes() < 0) {//checking start time is greater than end time
						    	  	startOk = false;
						    	 	endOk = false;
						    	 	output = "<html><head><title>Payment Page</title>"
				                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
				                            + "</head><body>"
				                            + "<div class='card'><h4 class='text-center'style='color:red;'>start time is greater than end time</h4></div>"
				                            + "</body></html>";
						    	    }
				    }catch(DateTimeParseException|NullPointerException e) {
				    	
				    			System.out.println();
				    }
				   
				    try {//checking input location is available location
   					 			locationOk = schedule.findLocation(location);//find location is a valid one using findLocation function in Schedule class
   					 			if(locationOk == false) {
   					 				output = "<html><head><title>Payment Page</title>"
				                            + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>"
				                            + "</head><body>"
				                            + "<div class='card'><h4 class='text-center'style='color:red;'>Enter valid location</h4></div>"
				                            + "</body></html>";//otherwise give error message
   					}
   					
				    }catch(Exception e) {
   					
   					
   				}
				    //if only start time,end time and date is in correct format that schedule will enter to database
				    	if(startOk == true && endOk ==true && onDateOk ==true && locationOk ==true ) {
				    		Date date = Calendar.getInstance().getTime();  //getting the current time
				    		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  //setting format
				    		String createdDate = dateFormat.format(date);  
				    		System.out.println(createdDate);  
				    		output = schedule.updateSchedule(ID, location, start, end, onDate,createdDate);//calling updateSchedule function in Schedule class
				  	  
				    		}
				    
				    return output;
				
				}
		
		//function to delete power cut schedules
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteSchedule(String itemData)
		{
					//Convert the input string to an XML document
					 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
					//Read the value from the element <ID>
					 String ID = doc.select("ID").text();
					 String output = schedule.deleteSchedule(ID);//calling the deleteSchedule function in Schedule class
				return output;
		}
	
	    
}
	
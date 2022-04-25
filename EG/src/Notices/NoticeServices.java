package Notices;


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


@Path("/notices")
public class NoticeServices {
	Notice noticeObj = new Notice(); 
	//read data
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML)
	public String readItems() 
	{ 
			
			return noticeObj.readNotices();
	} 
	
	//insert data
			@POST
			@Path("/") 
			@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String insertNotice(@FormParam("noticeType") String noticeType, 
			@FormParam("noticeType") String noticeCode, 
			@FormParam("noticeDate") String noticeDate,
			@FormParam("noticeTopic") String noticeTopic,   
			@FormParam("noticeDesc") String noticeDesc,
			@FormParam("noticePerson") String noticePerson,
			@FormParam("noticeMails") String noticeMails)
			{ 
			 String output = noticeObj.inserNotice(noticeType,noticeCode, noticeDate, noticeTopic, noticeDesc, noticePerson,noticeMails); 
			return output; 
			}
		
		//update data
			@PUT
			@Path("/") 
			@Consumes(MediaType.APPLICATION_JSON) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String updateNotice(String noticeData) { 
			//Convert the input string to a JSON object 
			 JsonObject noticeObject = new JsonParser().parse(noticeData).getAsJsonObject(); 
			//Read the values from the JSON object
			 String noticeID = noticeObject.get("noticeID").getAsString(); 
			 String noticeType = noticeObject.get("noticeType").getAsString(); 
			 String noticeCode = noticeObject.get("noticeType").getAsString(); 
			 String noticeDate = noticeObject.get("noticeDate").getAsString(); 
			 String noticeTopic = noticeObject.get("noticeTopic").getAsString();  
			 String noticeDesc = noticeObject.get("noticeDesc").getAsString();
			 String noticePerson = noticeObject.get("noticePerson").getAsString();  
			 String noticeMails = noticeObject.get("noticeMails").getAsString();  
			 String output = noticeObj.updateNotice(noticeID, noticeType, noticeCode, noticeDate,noticeTopic, noticeDesc, noticePerson,noticeMails); 
			return output; 
			}
			
			//delete data

			@DELETE
			@Path("/") 
			@Consumes(MediaType.APPLICATION_XML) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String deleteItem(String noticeData) { 
				//Convert the input string to an XML document
				Document doc = Jsoup.parse(noticeData, "", Parser.xmlParser()); 
			 
				//Read the value from the element <noticeID>
				String noticeID = doc.select("noticeID").text(); 
				String output = noticeObj.deleteNotice(noticeID); 
				return output; 
			}
			
			@GET
			@Path("/search")
			@Produces(MediaType.TEXT_HTML)
			public String searchNotices(String itemData) {
				Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
				String type = doc.select("type").text(); 
				return noticeObj.searchNotices(type);
			}
			
}

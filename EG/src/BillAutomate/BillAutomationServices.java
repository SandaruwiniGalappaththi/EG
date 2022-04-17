package BillAutomate;

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

@Path("/billautomate")
public class BillAutomationServices {
	BillAutomation bill = new BillAutomation();
	
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML) 
	public String readPerUnit() {		
		return bill.redPerUnit();	
	}
	

	@GET
	@Path("/searchperunit")
	@Produces(MediaType.TEXT_HTML) 
	public String readPerUnitBySearch(String itemData) {		
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <billType>
		String billType = doc.select("billType").text(); 
		String output = bill.readPerUnitBySearch(billType); 
		return output; 	
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
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) { 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
		
		//Read the values from the JSON object
		String billType = itemObject.get("billType").getAsString(); 
		String KWH = itemObject.get("KWH").getAsString(); 
		String Fixed = itemObject.get("Fixed").getAsString(); 
		String Fuel = itemObject.get("Fuel").getAsString(); 
		String Rebate = itemObject.get("Rebate").getAsString(); 
		String Tax = itemObject.get("Tax").getAsString(); 
		String Total = itemObject.get("Total").getAsString();
		String output = bill.updatePerUnit(billType, KWH, Fixed, Fuel, Rebate, Tax, Total); 
		return output; 
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String itemData) { 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <billType>
		String billType = doc.select("billType").text(); 
		String output = bill.deletePerUnit(billType); 
		return output; 
	}
}

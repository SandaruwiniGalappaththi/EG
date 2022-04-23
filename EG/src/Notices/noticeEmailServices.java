package Notices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

@Path("/Noticemail")
public class noticeEmailServices {
	NoticeEmail m = new NoticeEmail();
	
	@GET
	@Path("/all")
	@Produces(MediaType.TEXT_HTML)
	public String sendToUsers(String itemData) {
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
		String ID = doc.select("ID").text(); 
		return m.sendToUsers(ID);
	}
	

}

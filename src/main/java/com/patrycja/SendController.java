package com.patrycja;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Controller
public class SendController {

	private RssRepo rssRepo;
	private EmailRepo emailRepo;

	@Autowired
	public SendController(RssRepo rssRepo, EmailRepo emailRepo) {
		this.rssRepo = rssRepo;
		this.emailRepo = emailRepo;
	}

	@GetMapping("/send")
	public String send(Model model) {
		
		EmailModel emailM = emailRepo.findById(1l).orElse(null);
		if(emailM == null) {
			model.addAttribute("msg", "Nie został podany email");
		}

		List<RssModel> linklist = rssRepo.findAll();
		if(linklist.isEmpty())
			return "redirect:/";
		
		
		String content = "";		
		for(RssModel rss : linklist) {
			try {
				content += prepareSingleNews(rss.getLink());
			} catch (IOException | IllegalArgumentException | FeedException e) {
				e.printStackTrace();
			} 
		}
		System.out.println(content);
		if(!content.isBlank()) {
			content = "<html><body>" + content + "</body></html>";
		
			EmailSender m = new EmailSender(emailM.getEmail());
//			try {
//				m.sendEmail(content);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		rssRepo.deleteAll();
		emailRepo.deleteAll();
		model.addAttribute("emptyemail", true);
		model.addAttribute("content", content);
		model.addAttribute("model", model);
		return "index";
	}


	
	private String prepareSingleNews(String url) throws IOException, IllegalArgumentException, FeedException {

		URL feedUrl;
		String content = "";

		feedUrl = new URL(url);
		SyndFeedInput input = new SyndFeedInput();
		XmlReader reader = new XmlReader(feedUrl);
		SyndFeed feed = input.build(reader);
		
		content += "<hr><br>" +  feed.getTitle() + "\n";
		
		for (SyndEntry entry : feed.getEntries()) {
			
			content += "<p><b>" + entry.getTitle() + "</b></p>\n";
			content += "<p><b>[" + entry.getPublishedDate() + "]</b></p>\n";
			content += "<p>" + entry.getDescription().getValue() + "</p>\n";
			content += "<a href=\"" + entry.getLink() + "\">" + entry.getLink() + "</a>\n\n";
        }
		return content;
	}
	
}

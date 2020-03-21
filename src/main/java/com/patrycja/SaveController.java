package com.patrycja;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SaveController {
	
	private RssRepo rssRepo;
	private EmailRepo emailRepo;

	@Autowired
	public SaveController(RssRepo rssRepo, EmailRepo emailRepo) {
		this.rssRepo = rssRepo;
		this.emailRepo = emailRepo;
	}

	@GetMapping("/")
	public String home(Model model, String msg) {
		List<RssModel> linklist = rssRepo.findAll();
		EmailModel emailM = emailRepo.findById(1l).orElse(null);
		if(emailM == null) {
			model.addAttribute("emptyemail", true);
		}else {
			model.addAttribute("emptyemail", false);
			model.addAttribute("email", emailM.getEmail());
		}
		System.out.println("msg: "+msg);
		model.addAttribute("msg", msg);
		model.addAttribute("linklist", linklist);
		return "index";
	}
	
	@PostMapping("/save")
	public String save(String email, RssModel rssModel, RedirectAttributes ra) {

		EmailModel emailModel = emailRepo.findById(1l).orElse(null);
		
		if(emailModel == null) {
			if(email == null || email.isBlank()) {
				ra.addAttribute("msg", "Musisz podać email");
				return "redirect:/";
			}else {
				emailRepo.save(new EmailModel(1l, email));
				ra.addAttribute("email", email);
			}
		}
		
		if(rssModel.getLink().isBlank()) {
			ra.addAttribute("msg", "Musisz link rss");
			return "redirect:/";
		}
		
		rssRepo.save(rssModel);
		return "redirect:/";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		if(rssRepo.existsById(id)) {
			rssRepo.deleteById(id);
		}
		return "redirect:/";
	}
	
}

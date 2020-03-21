package com.patrycja;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Kontroler {
	@Autowired
	private RssRepo rssRepo;
	
	@PostMapping("/save")
	public String save(@RequestParam("email") String email, Model model, RssModel rssModel) {
		rssRepo.save(rssModel);
		List<RssModel> listaLinkow = rssRepo.findAll();
		model.addAttribute("lista", listaLinkow);
		model.addAttribute("email", email);
		return "index";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		rssRepo.deleteById(id);
		
		return "index";
	}
	
}

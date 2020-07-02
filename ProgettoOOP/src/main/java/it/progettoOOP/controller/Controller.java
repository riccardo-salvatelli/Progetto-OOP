package it.progettoOOP.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.progettoOOP.model.File;

@RestController
public class Controller {
	
	@GetMapping ("/esempio")
	
	public File provaget(@RequestParam(name="param1", defaultValue="World") String param1) {
	return new File ("CIAO");
		// TODO Auto-generated constructor stub
	}
	@PostMapping("/esempio")
	public File exampleMethod2(@RequestBody String body) {
		return new File("ciao");
	
	}
}
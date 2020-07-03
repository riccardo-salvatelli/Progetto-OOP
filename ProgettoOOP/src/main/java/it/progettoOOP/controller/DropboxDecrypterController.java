package it.progettoOOP.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import it.progettoOOP.model.File;

@RestController
public class DropboxDecrypterController {
	
	@GetMapping("/esempio")
	public File esempio(@RequestParam(name="param1")String param1) {
		return new File(param1);
	}
	@GetMapping("/esempio2")
	public File esempio() {
		return new File();
	}
	@PostMapping("/esempio")
	public File exampleMethod2(@RequestBody String body) {
		return new File("ciao");
	
	}
}
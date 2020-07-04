package it.progettoOOP.controller;

import it.progettoOOP.service.ServizioFile;
import it.progettoOOP.service.ServizioFileImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.progettoOOP.model.File;

@ComponentScan({"it.progettoOOP.service"}) //Necessario altrimenti il controller cerca ServizioFileImpl solamente all'interno del package "controller"
@RestController
public class DropboxDecrypterController {

	@Autowired
	ServizioFileImpl servizioFile;
	
	@GetMapping("/esempio")
	public File esempio(@RequestParam(name="param1")String param1) {
		return new File(param1);
	}

	@GetMapping("/getListaFile")
	public ResponseEntity<Object> getListaFile() {
		return new ResponseEntity<>(servizioFile.getListaFile(), HttpStatus.OK);
	}

	//metodo che scarica un file da dropbox
	@GetMapping("/scarica/")
	public ResponseEntity<Object> scarica(@RequestBody String id) {
		System.out.println("Ho scaricato i file criptati");
		return null;
	}
	@GetMapping("/decripta")
	public void decripta() {
		//qui c'è un metodo che passa i file scaricati a decriptazione
		System.out.println("Ho decriptato i file");
	}
}
	
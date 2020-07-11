package it.progettoOOP.controller;

import it.progettoOOP.service.ServizioFile;

import java.nio.channels.NonReadableChannelException;
import java.util.Collection;
import java.util.Collections;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.progettoOOP.exception.ListaLocaleVuotaException;
import it.progettoOOP.model.Credenziali;
import it.progettoOOP.model.File;

@ComponentScan({"it.progettoOOP.service"}) //Necessario altrimenti il controller cerca ServizioFileImpl solamente all'interno del package "controller"
@RestController
public class DropboxDecrypterController {

	@Autowired
	ServizioFile servizioFile;
	
	@GetMapping("/esempio")
	public File esempio(@RequestParam(name="param1")String param1) {
		return new File(param1);
	}

	@GetMapping("/getListaFile")
	public ResponseEntity<Object> getListaFile() {
		return new ResponseEntity<>(servizioFile.getListaFile(), HttpStatus.OK);
	}

	//metodo che scarica un file da dropbox
	@PostMapping("/scarica")
	public String scarica(@RequestBody Credenziali cre) {
		servizioFile.scaricaFile(cre.getId(),cre.getChiavi());
		
		return "Ho scaricato il file criptato " + servizioFile.getInformazioniFile(cre.getId()).getNome();
	}
	@GetMapping("/listaLocale")
	public Collection<File> getListaLocale() throws ListaLocaleVuotaException {
		return servizioFile.getFiles();
		}
	//restituisce il numero di file di testo scaricati
	@GetMapping("/numeroTxt")
	public ResponseEntity<Object> getTxt(){
		return new ResponseEntity<>(servizioFile.numeroTxt(), HttpStatus.OK);
	}
	
	@GetMapping("/decripta")
	public void decripta() {
		//qui c'è un metodo che passa i file scaricati a decriptazione
		System.out.println("Ho decriptato i file");
	}
}
	
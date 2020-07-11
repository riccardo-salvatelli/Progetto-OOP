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
		return new ResponseEntity<>("Il numero di file di testo (.txt) è: " + servizioFile.numeroTxt(), HttpStatus.OK);
	}
	@GetMapping("/numeroImm")
	public ResponseEntity<Object> getImm(){
		return new ResponseEntity<>("Il numero di immagini (.jpeg .png .jpg) è " + servizioFile.numeroImm(), HttpStatus.OK);
	}
	@GetMapping("/mediaParole")
	public ResponseEntity<Object> getMediaNumeroParole(){
		return new ResponseEntity<>("La media di parole trovate nei testi è " + servizioFile.mediaNumeroParole(), HttpStatus.OK);
	}
	@GetMapping("/mediaFrasi")
	public ResponseEntity<Object> getMediaNumeroFrasi(){
		return new ResponseEntity<>("La media di frasi trovate nei testi è " + servizioFile.mediaNumeroFrasi(), HttpStatus.OK);
	}
	@GetMapping("/mediaPixel")
	public ResponseEntity<Object> getMediaNumeroPixel(){
		return new ResponseEntity<>("La media di pixel trovate nei testi è " + servizioFile.mediaNumeroParole(), HttpStatus.OK);
	}
	@GetMapping("/mediaDimImm")
	public ResponseEntity<Object> getMediaDimensionImmagini(){
		double[] mediaDimImm = servizioFile.mediaDimensioniImmagini();
		return new ResponseEntity<>("La media delle dimensioni delle immagini è " + mediaDimImm[0] + "x" + mediaDimImm[1], HttpStatus.OK);
	}
	@GetMapping("/statAutori")
	public ResponseEntity<Object> getStatAutori(){
		return new ResponseEntity<>("La percentuale di caricamento degli autori è" + servizioFile.statAutori(), HttpStatus.OK);
	}
}
	
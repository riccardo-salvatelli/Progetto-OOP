package it.progettoOOP.service;
import it.progettoOOP.model.*;
import java.util.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

public class ServizioFileImpl implements ServizioFile {
	private static Map <String,File> fileRepo = new HashMap <>(); // l'indicie è di tipo String e non int perchè usiamo l'id del file che
																// di tipo string
	
	public void aggiungiFile(File file) {
		if(fileRepo.containsKey(file.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Questo file è gia presente ..");
		} 
		fileRepo.put(file.getId(), file); 	//aggiungo un file all'hashmap 
	}
	
	public boolean cancellaFile(String id) {			
		if(fileRepo.remove(id)==null)			// se non rimuove il file, ritorna false perchè non lo ha trovato
			return false;
		return true;							// se lo ha trovato lo rimuove e ritorna true
	}
	
	public boolean aggiornaFile(String id,File file) {
		if(cancellaFile(id)==false) return false;			// vede se il file è stato cancellato; se si va avanti e aggiorna il file
		file.setId(id);										// se non lo ha cancellato termina
		fileRepo.put(id, file);
		return true;
	}
	

public Collection<File> getFiles (){				// ottengo la lista dei files
	return fileRepo.values();
}
	
	public ServizioFileImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
}


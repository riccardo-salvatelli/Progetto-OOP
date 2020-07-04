package it.progettoOOP.service;
import it.progettoOOP.model.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

public class ServizioFileImpl implements ServizioFile {
	private static Map <String,File> fileRepo = new HashMap <>(); // l'indicie è di tipo String e non int perchè usiamo l'id del file che
																// di tipo string

	public ServizioFileImpl() {
		// TODO Auto-generated constructor stub
	}

	public void aggiungiFile(File file) {
		if(fileRepo.containsKey(file.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Questo file è gia presente ..");
		} 
		fileRepo.put(file.getId(), file); 	//aggiungo un file all'hashmap 
	}
	
	public boolean cancellaFile(String id) {
		// se non rimuove il file, ritorna false perchè non lo ha trovato
		// se lo ha trovato lo rimuove e ritorna true
		return fileRepo.remove(id) != null;
	}
	
	public boolean aggiornaFile(String id,File file) {
		if(!cancellaFile(id)) return false;			// vede se il file è stato cancellato; se si va avanti e aggiorna il file
		file.setId(id);										// se non lo ha cancellato termina
		fileRepo.put(id, file);
		return true;
	}
	

	public Collection<File> getFiles (){				// ottengo la lista dei files
	return fileRepo.values();
}


	//questo metodo esegue una request list_folder ottenendo l'elenco di tutti i file contenuti
	// nella cartella principale. Ritorna due array string contenenti id e rispettivo nome file.
	public String[][] getListaFile(){
		JSONObject obj;
		String token = "Cxab77MLmfQAAAAAAAAA7G6NSiHlqSFE77z5AGgtFgBcRgsdp3eAxCrfe7yfULMV";
		String url = "https://api.dropboxapi.com/2/files/list_folder";
		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);

		RestTemplate restTemplate = new RestTemplate();

		String requestJson = "{\"path\": \"\",\"recursive\": false,\"include_media_info\": false,\"include_deleted\": false,\"include_has_explicit_shared_members\": false,\"include_mounted_folders\": true,\"include_non_downloadable_files\": true}";

		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers); //genera qualcosa del tipo {requestJson}[headers]
		obj = new JSONObject(restTemplate.postForObject(url, entity, String.class)); //dal response della request definisco l'obj (json)
		JSONArray array = obj.getJSONArray("entries");
		String[] idsArray = new String[array.length()];
		String[] nameArray = new String[array.length()];
		for(int i = 0; i < array.length(); i++){
			idsArray[i] = array.getJSONObject(i).getString("id");
			nameArray[i] = array.getJSONObject(i).getString("name");
		}
		return new String[][] {idsArray, nameArray};
	}
	
	
	
	
	
	
	
}


package it.progettoOOP.service;
import it.progettoOOP.model.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


@Service
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
	public String[] getListaFile(){
		JSONObject obj;
		String token = "token";
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
		for(int i = 0; i < array.length(); i++){
			idsArray[i] = array.getJSONObject(i).getString("name") + " ---> " + array.getJSONObject(i).getString("id");
		}
		return idsArray;
	}


	//Con questo metodo, a partire dall'id del file, si ricavano tutti gli attributi di esso.
	//Viene prima fatta una request di tipo get_metadata, e poi list_file_members per ottenere
	//l'utente proprietario del file. Ritorna un oggetto di tipo File completo di tutti gli attributi
	//ricavati.
	public File getInformazioniFile(String id){
		JSONObject obj;
		String token = "TOKEN";
		String url = "https://api.dropboxapi.com/2/files/get_metadata";
		String nomeFile;
		String autoreFile;
		int dimensioneFile;
		Date dataModifica;
		String jsonString = "{\"path\": \""+ id +"\"}";
		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<String> entity = new HttpEntity<>(jsonString, headers); //genera qualcosa del tipo {requestJson}[headers]

		RestTemplate restTemplate = new RestTemplate();
		obj = new JSONObject(restTemplate.postForObject(url, entity, String.class)); //dal response della request definisco l'obj (json)
		nomeFile = obj.getString("name");
		dimensioneFile = obj.getInt("size");
		String appoggioData = obj.getString("client_modified");
		dataModifica = new Date(Integer.parseInt(appoggioData.split("-")[0]), Integer.parseInt(appoggioData.split("-")[1]),Integer.parseInt(appoggioData.split("-")[2].split("T")[0]));

		url = "https://api.dropboxapi.com/2/sharing/list_file_members";
		jsonString = "{\"file\": \"" + id + "\"}";
		entity = new HttpEntity<>(jsonString, headers);
		obj = new JSONObject(restTemplate.postForObject(url,entity,String.class));
		JSONArray usersArray = obj.getJSONArray("users");
		int indice = 0;
		for(int i = 0; i < usersArray.length(); i++){
			if(usersArray.getJSONObject(i).getJSONObject("access_type").getString(".tag").equals("owner")){
				indice = i;
				break;
			}
		}
		autoreFile = usersArray.getJSONObject(indice).getJSONObject("user").getString("display_name");

		return new File(nomeFile,"percorso",id,dimensioneFile,autoreFile,dataModifica);
	}

//	public File scaricaFile(String id){
//		JSONObject obj;
//		String jsonString = "{\"path\": \""+ id +"\"}";
//		String token = "TOKEN";
//		String url = "https://content.dropboxapi.com/2/files/download";
//		RestTemplateBuilder restTemplate = null;
//		BufferedOutputStream bufferedOutputStream = null;
//		HttpHeaders headers = new HttpHeaders();
//
//		headers.set("Authorization", "Bearer " + token);
//		headers.set("Dropbox-API-Arg", jsonString );
//
//
//
//		HttpEntity<String> entity = new HttpEntity<>(jsonString, headers); //genera qualcosa del tipo {requestJson}[headers]
//		ResponseEntity<byte[]> response = restTemplate.build().exchange(url, HttpMethod.GET, entity, byte[].class);
//
//		bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(nomeFile));
//		return true;
//	}

	
	
	
	
}


package it.progettoOOP.service;
import it.progettoOOP.model.*;
import it.progettoOOP.exception.ListaLocaleVuotaException;
import it.progettoOOP.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServizioFileImpl implements ServizioFile {
	private static Map<String,File> fileRepo = new HashMap<>();		// l'indice è di tipo String e non int perchè usiamo
																	// Costruisce l'iteratore con il metodo dedicato
	Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();	
	private String token = "Cxab77MLmfQAAAAAAAAA8iYDNVxZt_dcpt1cuy0NsxmQTwnlNij74FZ5PJNobJeg";	// l'id del file che è di tipo String
																						// Definisco l'iteratore per la lista dei file scaricati
															

	private Boolean aggiungiFile(File file) {
		if (fileRepo.containsKey(file.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questo file è gia presente ..");
		}
		if (file.getTipoFile().equals("Testo")){
			file = new Testo(file.getNome(),file.getPercorso(),file.getId(),file.getDimensione(),file.getAutore(),file.getDataUltimaModifica());
		}
		else if(file.getTipoFile().equals("Immagine")){
			file = new Immagine(file.getNome(),file.getPercorso(),file.getId(),file.getDimensione(),file.getAutore(),file.getDataUltimaModifica());
		}
		fileRepo.put(file.getId(), file); // aggiungo un file all'hashmap
		return true;
	}

	public boolean cancellaFile(String id) {
		// se non rimuove il file, ritorna false perchè non lo ha trovato
		// se lo ha trovato lo rimuove e ritorna true
		return fileRepo.remove(id) != null;
	}

	public boolean aggiornaFile(String id, File file) {
		if (!cancellaFile(id))
			return false; // vede se il file è stato cancellato; se si va avanti e aggiorna il file
		file.setId(id); // se non lo ha cancellato termina
		fileRepo.put(id, file);
		return true;
	}

	public Collection<File> getFiles() throws ListaLocaleVuotaException { // ottengo la lista dei files
		if(fileRepo.isEmpty()) {
			throw new ListaLocaleVuotaException();
		}
		else {
			return fileRepo.values();
		}
	}

	// questo metodo esegue una request list_folder ottenendo l'elenco di tutti i
	// file contenuti
	// nella cartella principale. Ritorna due array string contenenti id e
	// rispettivo nome file.
	public String[] getListaFile() {
		JSONObject obj;
		 
		String url = "https://api.dropboxapi.com/2/files/list_folder";
		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);

		RestTemplate restTemplate = new RestTemplate();

		String requestJson = "{\"path\": \"\",\"recursive\": false,\"include_media_info\": false,\"include_deleted\": false,\"include_has_explicit_shared_members\": false,\"include_mounted_folders\": true,\"include_non_downloadable_files\": true}";

		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers); // genera qualcosa del tipo
																			// {requestJson}[headers]
		obj = new JSONObject(restTemplate.postForObject(url, entity, String.class)); // dal response della request
																						// definisco l'obj (json)
		JSONArray array = obj.getJSONArray("entries");
		String[] idsArray = new String[array.length()];
		for (int i = 0; i < array.length(); i++) {
			idsArray[i] = array.getJSONObject(i).getString("name") + " ---> " + array.getJSONObject(i).getString("id");
		}
		return idsArray;
	}


	// Con questo metodo, a partire dall'id del file, si ricavano tutti gli
	// attributi di esso.
	// Viene prima fatta una request di tipo get_metadata, e poi list_file_members
	// per ottenere
	// l'utente proprietario del file. Ritorna un oggetto di tipo File completo di
	// tutti gli attributi
	// ricavati.
	public File getInformazioniFile(String id) {
		JSONObject obj;
		String url = "https://api.dropboxapi.com/2/files/get_metadata";
		String nomeFile;
		String autoreFile;
		int dimensioneFile;
		LocalDateTime dataUltimaModifica;
		String jsonString = "{\"path\": \"" + id + "\"}";
		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<String> entity = new HttpEntity<>(jsonString, headers); // genera qualcosa del tipo
																			// {requestJson}[headers]
		RestTemplate restTemplate = new RestTemplate();
		obj = new JSONObject(restTemplate.postForObject(url, entity, String.class)); // dal response della request
																						// definisco l'obj (json)
		nomeFile = obj.getString("name");
		dimensioneFile = obj.getInt("size");
		dataUltimaModifica = LocalDateTime.parse(obj.getString("client_modified").split("Z")[0]);
		url = "https://api.dropboxapi.com/2/sharing/list_file_members";
		jsonString = "{\"file\": \"" + id + "\"}";
		entity = new HttpEntity<>(jsonString, headers);
		obj = new JSONObject(restTemplate.postForObject(url, entity, String.class));
		JSONArray usersArray = obj.getJSONArray("users");
		int indice = 0;
		for (int i = 0; i < usersArray.length(); i++) {
			if (usersArray.getJSONObject(i).getJSONObject("access_type").getString(".tag").equals("owner")) {
				indice = i;
				break;
			}
		}
		autoreFile = usersArray.getJSONObject(indice).getJSONObject("user").getString("display_name");
		return new File(nomeFile, System.getProperty("user.dir") + "/fileScaricati/", id, dimensioneFile, autoreFile, dataUltimaModifica);

	}

	public Boolean scaricaFile(String id, double [] chiavi) {
		File file = getInformazioniFile(id); // prendo le informazioni di un oggetto file per avere poi il nome del file
												// sotto
		String jsonString = "{\"path\": \"" + id + "\"}";
		String url = "https://content.dropboxapi.com/2/files/download";
		RestTemplateBuilder restTemplate = new RestTemplateBuilder();
		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", "Bearer " + token);
		headers.set("Dropbox-API-Arg", jsonString);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<byte[]> response = restTemplate.build().exchange(url, HttpMethod.GET, entity, byte[].class);
		decriptaFile(chiavi,file, response.getBody());

		return aggiungiFile(file);

	}
	
	public void decriptaFile (double [] chiavi, File file,byte [] by ) {
		Decriptazione decr = new Decriptazione(chiavi);
		
		int [] sequenza = decr.calcoloSequenza(file.getDimensione());
		decr.chaosXOR(by, sequenza, file.getNome());
	}
	
	//Questo metodo serve per contare quanti file di tipo Testo sono presenti
	//nell'Hashmap. Utilizza il metodo getTipoFile per confrontare se effettivamente 
	//si tratta di un file di testo
	//la condizione del while è bool e cicla fino a quando c'è un altro elemento
	//Uso l'iteratore
	public int numeroTxt() {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroTxt=0;			
		while(it.hasNext()) {
			Map.Entry<String,File>entry = it.next();
			if(entry.getValue().getTipoFile().equals("Testo")) {
				numeroTxt += 1;
			}
		}return numeroTxt;
	}
	
	
	//Questo metodo serve per contare quanti file di tipo Immagine sono presenti
		//nell'Hashmap. Utilizza il metodo getTipoFile per confrontare se effettivamente 
		//si tratta di un file di Immagine.
		//la condizione del while è bool e cicla fino a quando c'è un altro elemento
		//Uso l'iteratore
	
	public int numeroImm() {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroImm=0;			
		while(it.hasNext()) {
			Map.Entry<String,File>entry = it.next();
			if(entry.getValue().getTipoFile().equals("Immagine")) {
				numeroImm += 1;
			}
		}return numeroImm;
	}
	
	//Con questo metodo si calcola la media delle parole contenute in tutti i file di tipo Testo 
	//presenti nell'hashmap. Definisco un iteratore interno al metodo.
	//
	
	public double medianumeroParole() {		
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroParole=0;
		double media;
		while(it.hasNext()) {
			Map.Entry<String,File>entry = it.next();
			if(entry.getValue().getTipoFile().equals("Testo")) {
				numeroParole += ((Testo)entry.getValue()).conteggioNumeroParole();
			}
		}media = numeroParole/numeroTxt();
		return media;
	}
	
	public double medianumeroFrasi() {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroFrasi=0;
		double media;// uso l'iteratore per scorrere la lista dei file scaricati
		while(it.hasNext()) {
			Map.Entry<String,File>entry = it.next();
			if(entry.getValue().getTipoFile().equals("Testo")) {
				numeroFrasi += ((Testo)entry.getValue()).conteggioNumeroFrasi();
			}
		}media = numeroFrasi/numeroTxt();
		return media;
	}
	
	public double medianumeroCaratteri() {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroCaratteri=0;
		double media;
		while(it.hasNext()) {
			Map.Entry<String,File>entry = it.next();
			if(entry.getValue().getTipoFile().equals("Testo")) {
				numeroCaratteri += ((Testo)entry.getValue()).conteggioNumeroCaratteri();
			}
		}media = numeroCaratteri/numeroTxt();
		return media;
	}
	
	public double mediaNumeroPixel() {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numPixel=0;
		double media; 
		while(it.hasNext()) {
			Map.Entry<String,File>entry = it.next();
			if(entry.getValue().getTipoFile().equals("Immagine")) {
				numPixel += ((Immagine)entry.getValue()).getNumPixel();
		}
	}media = numPixel/numeroImm();
	return media;
	}
}

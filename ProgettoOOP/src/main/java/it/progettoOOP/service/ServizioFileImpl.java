package it.progettoOOP.service;

import it.progettoOOP.model.*;
import it.progettoOOP.exception.ChiaviErrateException;
import it.progettoOOP.exception.ChiaviNullException;
import it.progettoOOP.exception.divZeroException;

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

/**
 * <b>ServizioFileImpl</b> definisce tutti i metodi utilizzabili all'interno del
 * controller.
 */
@Service
public class ServizioFileImpl implements ServizioFile {
	/**
	 * fileRepo utilizza come chiave l'id di DropBox e come valore degli oggetti di
	 * tipo {@link File} L'indice dell'hashmap è di tipo <code>String</code> perchè
	 * usiamo l'id del file che è di tipo <code>String</code>
	 */
	private static Map<String, File> fileRepo = new HashMap<>();

	private String token = "Cxab77MLmfQAAAAAAAAA8iYDNVxZt_dcpt1cuy0NsxmQTwnlNij74FZ5PJNobJeg";

	/**
	 * Aggiunge un oggetto di tipo <code>File</code> o <code>Testo</code> o
	 * <code>Immagine</code> all'hashmap in base al tipo del file.
	 * 
	 * @param file Rappresenta l'oggeto di tipo <code>File</code> che viene aggiunto
	 *             all'hashmap
	 * @return <code>boolean</code> il ritorno è <code>true</code> se il file viene
	 *         aggiunto all'hashmap.
	 * @throws ChiaviErrateException Viene lanciata quando le chiavi per decriptare
	 *                             sono sbagliate.
	 */
	public boolean aggiungiFile(File file) throws ChiaviErrateException {
		if (fileRepo.containsKey(file.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questo file è gia presente ..");
		}
		if (file.getTipoFile().equals("Testo")) {
			file = new Testo(file.getNome(), file.getPercorso(), file.getId(), file.getDimensione(), file.getAutore(),
					file.getDataUltimaModifica());
		} else if (file.getTipoFile().equals("Immagine")) {
			file = new Immagine(file.getNome(), file.getPercorso(), file.getId(), file.getDimensione(),
					file.getAutore(), file.getDataUltimaModifica());
		}
		fileRepo.put(file.getId(), file); // aggiungo un file all'hashmap
		return true;
	}

	
	public boolean cancellaFile(String id) {
		// se non rimuove il file, ritorna false perchè non lo ha trovato
		// se lo ha trovato lo rimuove e ritorna true
		return fileRepo.remove(id) != null;
	}

	/**
	 * Aggiorna il file corrispondente all'id , andandolo a sovrascrivere.
	 * 
	 * @param id   è l'id del file dropbox.
	 * @param file è il file che viene aggiornato.
	 * @return <code>boolean</code> ritorna <code>true</code> se il file è stato
	 *         aggiornato. ritorna <code>false</code> se il file non è presente
	 *         nell'hashmap.
	 */
	public boolean aggiornaFile(String id, File file) {
		if (!cancellaFile(id))
			return false; // vede se il file è stato cancellato; se si va avanti e aggiorna il file
		file.setId(id); // se non lo ha cancellato termina
		fileRepo.put(id, file);
		return true;
	}

	public Collection<File> getFiles() { // ottengo la lista dei files
		return fileRepo.values();
	}

	/**
	 * Esegue una richiesta ottenendo l'elenco di tutti i file contenuti nella
	 * cartella.
	 * 
	 * 
	 * @return <code>String []</code> Contiene l'id del file e il nome del file
	 */
	public String[] getListaFile() {
		JSONObject obj;

		String url = "https://api.dropboxapi.com/2/files/list_folder";
		HttpHeaders headers = new HttpHeaders();

		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "Bearer " + token);

		RestTemplate restTemplate = new RestTemplate();

		String requestJson = "{\"path\": \"/file condivisi\",\"recursive\": false,\"include_media_info\": false,\"include_deleted\": false,\"include_has_explicit_shared_members\": false,\"include_mounted_folders\": true,\"include_non_downloadable_files\": true}";

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

	/**
	 * A partire dall'id del file, ricava tutti gli attributi dell'oggetto
	 * <code>File</code>.
	 * 
	 * @param id è l'id del file dropbox.
	 * @return <code>File</code> contenente le informazioni ricavate.
	 */
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
		autoreFile = obj.getJSONObject("sharing_info").getString("modified_by"); // Contiene l'id dell'utente

		// Ricavo il parametro display_name con il metodo get_account
		url = "https://api.dropboxapi.com/2/users/get_account";
		jsonString = "{\"account_id\": \"" + autoreFile + "\"}";
		entity = new HttpEntity<>(jsonString, headers);
		obj = new JSONObject(restTemplate.postForObject(url, entity, String.class));
		autoreFile = obj.getJSONObject("name").getString("display_name");

		return new File(nomeFile, System.getProperty("user.dir") + "/fileScaricati/", id, dimensioneFile, autoreFile,
				dataUltimaModifica);

	}

	/**
	 * Scarica il file che viene identificato dall'id e decriptato utilizzano le
	 * chiavi
	 * 
	 * @param cre contiene l'id e le chiavi relative al file da scaricare
	 * @throws ChiaviErrateException viene lanciata nel momento in cui le chiavi sono
	 *                             sbagliate
	 * @throws ChiaviNullException viene lanciata quando le chiavi sono <code>null</code>
	 * @throws ChiaviErrateException viene lanciata quando le chiavi sono sbagliate 
	 */
	public void scaricaFile(Credenziali cre) throws ChiaviErrateException, ChiaviNullException {
		File file = getInformazioniFile(cre.getId()); // prendo le informazioni di un oggetto file per avere poi il nome
														// del file
		// sotto
		String jsonString = "{\"path\": \"" + cre.getId() + "\"}";
		String url = "https://content.dropboxapi.com/2/files/download";
		RestTemplateBuilder restTemplate = new RestTemplateBuilder();
		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", "Bearer " + token);
		headers.set("Dropbox-API-Arg", jsonString);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<byte[]> response = restTemplate.build().exchange(url, HttpMethod.GET, entity, byte[].class);

		decriptaFile(cre.getChiavi(), file, response.getBody());

		aggiungiFile(file);

	}

	/**
	 * Decripta il file, calcolando prima la sequenza pseudo-randomica e facendo
	 * successivamente lo XOR tra la sequenza e i byte
	 * 
	 * @param chiavi per decriptare il file
	 * @param file   indica quale file viene decriptato
	 * @param by     array di byte criptati
	 */
	public void decriptaFile(double[] chiavi, File file, byte[] by) {
		Decriptazione decr = new Decriptazione(chiavi);

		int[] sequenza = decr.calcoloSequenza(file.getDimensione());
		decr.chaosXOR(by, sequenza, file.getNome());
	}

	/**
	 * Calcola quanti file di testo sono presenti nell'hashmap
	 * 
	 * @return il numero di file di testo
	 */
	public int numeroTxt() {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroTxt = 0;
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			if (entry.getValue().getTipoFile().equals("Testo")) {
				numeroTxt += 1;
			}
		}
		return numeroTxt;
	}

	/**
	 * Calcola quante immagini sono presenti nell'hashmap
	 * 
	 * @return il numero di immagini
	 */
	public int numeroImm() {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroImm = 0;
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			if (entry.getValue().getTipoFile().equals("Immagine")) {
				numeroImm += 1;
			}
		}
		return numeroImm;
	}

	/**
	 * Calcola la media delle parole all'interno dell'hashmap.
	 * 
	 * @return <code>double</code> la media del numero di parole complessive
	 *         presenti in tutti i file di testo
	 * @throws divZeroException viene lanciata quando non ci sono file di testo
	 *                          nell'hashmap
	 */
	public double mediaNumeroParole() throws divZeroException {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroParole = 0;
		int numeroTxt = numeroTxt();
		if (numeroTxt == 0) {
			throw new divZeroException("Non ci sono testi.");
		}
		double media;
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			if (entry.getValue().getTipoFile().equals("Testo")) {
				numeroParole += ((Testo) entry.getValue()).conteggioNumeroParole();
			}
		}
		media = (double) numeroParole / numeroTxt;
		return media;
	}

	/**
	 * Calcola la media delle frasi all'interno dell'hashmap.
	 * 
	 * @return <code>double</code> la media del numero di frasi complessive presenti
	 *         in tutti i file di testo
	 * @throws divZeroException viene lanciata quando non ci sono file di testo
	 *                          nell'hashmap
	 */
	public double mediaNumeroFrasi() throws divZeroException {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroFrasi = 0;
		int numeroTxt = numeroTxt();
		if (numeroTxt == 0) {
			throw new divZeroException("Non ci sono testi.");
		}
		double media;// uso l'iteratore per scorrere la lista dei file scaricati
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			if (entry.getValue().getTipoFile().equals("Testo")) {
				numeroFrasi += ((Testo) entry.getValue()).conteggioNumeroFrasi();
			}
		}
		media = (double) numeroFrasi / numeroTxt;
		return media;
	}

	/**
	 * Calcola la media dei caratteri all'interno dell'hashmap.
	 * 
	 * @return <code>double</code> la media del numero di caratteri complessivi
	 *         presenti in tutti i file di testo
	 * @throws divZeroException viene lanciata quando non ci sono file di testo
	 *                          nell'hashmap
	 */
	public double mediaNumeroCaratteri() throws divZeroException {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numeroCaratteri = 0;
		int numeroTxt = numeroTxt();
		if (numeroTxt == 0) {
			throw new divZeroException("Non ci sono testi.");
		}

		double media;
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			if (entry.getValue().getTipoFile().equals("Testo")) {
				numeroCaratteri += ((Testo) entry.getValue()).conteggioNumeroCaratteri();
			}
		}
		media = (double) numeroCaratteri / numeroTxt;
		return media;
	}

	/**
	 * Calcola la media dei numero di pixel all'interno dell'hashmap.
	 * 
	 * @return <code>double</code> la media del numero di pixel complessivi presenti
	 *         in tutte le immagini
	 * @throws divZeroException viene lanciata quando non ci sono immagini
	 *                          nell'hashmap
	 */
	public double mediaNumeroPixel() throws divZeroException {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		int numPixel = 0;
		int numeroImm = numeroImm();
		if (numeroImm == 0) {
			throw new divZeroException("Non ci sono immagini.");
		}
		double media;
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			if (entry.getValue().getTipoFile().equals("Immagine")) {
				numPixel += ((Immagine) entry.getValue()).getNumPixel();
			}
		}
		media = (double) numPixel / numeroImm;
		return media;
	}

	/**
	 * Calcola la media delle dimensioni delle immagini all'interno dell'hashmap.
	 * 
	 * @return <code>double</code> la media delle dimensioni complessive presenti in
	 *         tutte le immagini
	 * @throws divZeroException viene lanciata quando non ci sono immagini
	 *                          nell'hashmap
	 */
	public double[] mediaDimensioniImmagini() throws divZeroException {
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		double numPixelAlt = 0;
		double numPixelLar = 0;
		int numeroImm = numeroImm();
		if (numeroImm == 0) {
			throw new divZeroException("Non ci sono immagini.");
		}
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			if (entry.getValue().getTipoFile().equals("Immagine")) {
				numPixelLar += ((Immagine) entry.getValue()).getDimImmagine()[0];
				numPixelAlt += ((Immagine) entry.getValue()).getDimImmagine()[1];
			}

		}
		return new double[] { numPixelLar / numeroImm, numPixelAlt / numeroImm };
	}

	/**
	 * Calcola la percentuale dei file caricati da un autore rispetto al totale del
	 * file caricati
	 * 
	 * @return <code>HashMap&lt;String,Double&gt;</code> contiene il nome dell'autore con
	 *         la relativa percentuale
	 * @throws divZeroException viene lanciata quando non ci sono file presenti
	 *                          nell'hashmap
	 */
	public HashMap<String, Double> statAutori() throws divZeroException {
		if (fileRepo.size() == 0) {
			throw new divZeroException("Non ci sono file scaricati.");
		}
		HashMap<String, Double> autori = new HashMap<>();
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			autori.merge(entry.getValue().getAutore(), 1.0, Double::sum);
		}
		for (Entry<String, Double> entry : autori.entrySet()) {
			entry.setValue(Math.floor(entry.getValue() * 100 / fileRepo.size()));
		}
		return autori;
	}

	/**
	 * Mostra quali file sono stati caricati nel periodo compreso tra
	 * <code>dataInizio</code> e <code>dataFine</code>. é anche possibile filtrare i
	 * file senza specificare <code>dataInizio</code> (ponendola uguale a
	 * <code>null</code>) o senza specificare <code>dataFine</code> (ponendola
	 * uguale a <code>null</code>).
	 * 
	 * @param dataInizio rappresenta l'inizio del periodo desiderato da filtrare.
	 * @param dataFine   rappresenta la fine del periodo desiderato da filtrare.
	 * @return <code> Vector&lt;File&gt; </code> che contiene i file che soddisfano il
	 *         fitro.
	 * 
	 */
	public Vector<File> filtraPerData(LocalDateTime dataInizio, LocalDateTime dataFine) {
		if (dataInizio == null)
			dataInizio = LocalDateTime.of(1800, 1, 1, 1, 1);
		if (dataFine == null)
			dataFine = LocalDateTime.now().plusYears(1);
		Vector<File> files = new Vector<>();
		Iterator<Entry<String, File>> it = fileRepo.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, File> entry = it.next();
			if (entry.getValue().getDataUltimaModifica().isAfter(dataInizio)
					&& entry.getValue().getDataUltimaModifica().isBefore(dataFine))
				files.add(entry.getValue());
		}
		return files;
	}
}

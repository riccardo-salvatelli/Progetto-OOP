package it.progettoOOP.service;

import it.progettoOOP.model.*;
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
 * <b>ServizioFileImpl</b> definisce tutti i metodi utilizzabili all'interno del controller.
 */
@Service
public class ServizioFileImpl implements ServizioFile {
    /**
     * fileRepo utilizza come chiave l'id di DropBox e come valore degli oggetti di tipo {@link File}
     */
    private static Map<String, File> fileRepo = new HashMap<>(); // l'indice è di tipo String e non int perchè usiamo
    // Costruisce l'iteratore con il metodo dedicato
    private String token = "Cxab77MLmfQAAAAAAAAA8iYDNVxZt_dcpt1cuy0NsxmQTwnlNij74FZ5PJNobJeg"; // l'id del file che è di
    // tipo String
    // Definisco l'iteratore per la lista dei file scaricati

    private Boolean aggiungiFile(File file) throws ChiaviNullException {
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

    public void scaricaFile(String id, double[] chiavi) throws ChiaviNullException {
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

        decriptaFile(chiavi, file, response.getBody());

        aggiungiFile(file);

    }

    public void decriptaFile(double[] chiavi, File file, byte[] by) {
        Decriptazione decr = new Decriptazione(chiavi);

        int[] sequenza = decr.calcoloSequenza(file.getDimensione());
        decr.chaosXOR(by, sequenza, file.getNome());
    }

    // Questo metodo serve per contare quanti file di tipo Testo sono presenti
    // nell'Hashmap. Utilizza il metodo getTipoFile per confrontare se
    // effettivamente
    // si tratta di un file di testo
    // la condizione del while è bool e cicla fino a quando c'è un altro elemento
    // Uso l'iteratore
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

    // Questo metodo serve per contare quanti file di tipo Immagine sono presenti
    // nell'Hashmap. Utilizza il metodo getTipoFile per confrontare se
    // effettivamente
    // si tratta di un file di Immagine.
    // la condizione del while è bool e cicla fino a quando c'è un altro elemento
    // Uso l'iteratore
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

    // Con questo metodo si calcola la media delle parole contenute in tutti i file
    // di tipo Testo
    // presenti nell'hashmap. Definisco un iteratore interno al metodo.
    //
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
        return new double[]{numPixelLar / numeroImm, numPixelAlt / numeroImm};
    }

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

    // Prende in input due date con le quali filtra i file salvati nell'hasmap.
    // Ritorna il vettore dei file che soddisfano
    // il filtro. Per omettere dataInizio, o dataFine, bisogna valorizzarle a null.
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

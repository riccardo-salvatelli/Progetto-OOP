package it.progettoOOP.controller;

import it.progettoOOP.exception.ChiaviErrateException;
import it.progettoOOP.exception.ChiaviNullException;

import it.progettoOOP.exception.DivZeroException;
import it.progettoOOP.service.ServizioFile;

import java.time.LocalDateTime;
import java.util.Collection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.progettoOOP.model.Credenziali;
import it.progettoOOP.model.File;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

// Necessario altrimenti il controller cerca ServizioFileImpl solamente all'interno del package "controller"

/**
 * Questa classe definisce tutti gli endpoint disponibili.
 */

@RestController
public class DropboxDecrypterController {

    @Autowired
    ServizioFile servizioFile;

    /**
     * Richiede la lista dei file presenti nella cartella di Dropbox
     *
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#getListaFile()}
     */
    @GetMapping("/getListaFile")
    public ResponseEntity<Object> getListaFile() {

        return new ResponseEntity<>(servizioFile.getListaFile(), HttpStatus.OK);
    }

    /**
     * Scarica il file desiderato.
     *
     * @param cre rappresenta l'id e le chiavi per la decriptazione del file da
     *            scaricare
     * @return <code> ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#scaricaFile(Credenziali)}
     */
    @PostMapping("/scarica")
    public ResponseEntity<Object> scarica(@RequestBody Credenziali cre) {
        try {
            servizioFile.scaricaFile(cre);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id errato");
        } catch (ChiaviErrateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMsg());
        } catch (ChiaviNullException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMsg());
        }
        return new ResponseEntity<>(
                "Ho scaricato il file criptato " + servizioFile.getInformazioniFile(cre.getId()).getNome(),
                HttpStatus.OK);
    }

    /**
     * Stampa la lista dei file scaricati
     *
     * @return <code>Collection&lt;File&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#getFiles()}
     */
    @GetMapping("/listaLocale")
    public Collection<File> getListaLocale() {
        return servizioFile.getFiles();
    }

    /**
     * Stampa il numero di file di testo scaricati
     *
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#numeroTxt()}
     */
    @GetMapping("/numeroTxt")
    public ResponseEntity<Object> getTxt() {
        return new ResponseEntity<>("Il numero di file di testo (.txt) è: " + servizioFile.numeroTxt(), HttpStatus.OK);
    }

    /**
     * Stampa il numero di immagini scaricate
     *
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#numeroImm()}
     */
    @GetMapping("/numeroImm")
    public ResponseEntity<Object> getImm() {
        return new ResponseEntity<>("Il numero di immagini (.jpeg .png .jpg) è " + servizioFile.numeroImm(),
                HttpStatus.OK);
    }

    /**
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#mediaNumeroParole()}
     */
    @GetMapping("/mediaParole")
    public ResponseEntity<Object> getMediaNumeroParole() {
        try {
            return new ResponseEntity<>("La media di parole trovate nei testi è " + servizioFile.mediaNumeroParole(),
                    HttpStatus.OK);
        } catch (DivZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }

    /**
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#mediaNumeroFrasi()}
     */
    @GetMapping("/mediaFrasi")
    public ResponseEntity<Object> getMediaNumeroFrasi() {
        try {
            return new ResponseEntity<>("La media di frasi trovate nei testi è " + servizioFile.mediaNumeroFrasi(),
                    HttpStatus.OK);
        } catch (DivZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }
    
    
    /**
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#mediaNumeroCaratteri()}
     */
    @GetMapping("/mediaCaratteri")
    public ResponseEntity<Object> getMediaCaratteri(){
        try {
            return new ResponseEntity<>("La media di frasi trovate nei testi è " + servizioFile.mediaNumeroCaratteri(),
                    HttpStatus.OK);
        } catch (DivZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }
    
    
    
    /**
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#mediaNumeroPixel()}
     */
    @GetMapping("/mediaPixel")
    public ResponseEntity<Object> getMediaNumeroPixel() {
        try {
            return new ResponseEntity<>("La media di pixel trovate nei testi è " + servizioFile.mediaNumeroPixel(),
                    HttpStatus.OK);
        } catch (DivZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }

    /**
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#mediaDimensioniImmagini()}
     */
    @GetMapping("/mediaDimImm")
    public ResponseEntity<Object> getMediaDimensionImmagini() {
        double[] mediaDimImm;
        try {
            mediaDimImm = servizioFile.mediaDimensioniImmagini();
            return new ResponseEntity<>(
                    "La media delle dimensioni delle immagini è " + mediaDimImm[0] + "x" + mediaDimImm[1],
                    HttpStatus.OK);
        } catch (DivZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);

        }

    }

    /**
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#statAutori()}
     */
    @GetMapping("/statAutori")
    public ResponseEntity<Object> getStatAutori() {
        try {
            return new ResponseEntity<>("La percentuale di caricamento degli autori è" + servizioFile.statAutori(),
                    HttpStatus.OK);
        } catch (DivZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }

//     Le date che vanno fornite nel body della richiesta devono avere il formato:
//     gg/mm/AAAA; l'ora: HH:mm:ss. Se si
//     vuole omettere la dataInizio o la dataFine basta settare dataInizio/dataFine
//     a 0 (int)
//     esempio parametri

    /**
     * Le date che vanno fornite nel body della richiesta devono avere il formato:
     * gg/mm/AAAA; l'ora: HH:mm:ss.
     *
     * <pre>
     * {@code
     *    {
     * 		"dataInizio": 0,
     * 		"dataFine": {
     * 			"data": "12/07/2020",
     * 			"ora": "12:00:00"
     *         }
     *    } }
     * </pre>
     *
     * @param jsonRequest deve avere una struttura come specificato sopra
     * @return <code>ResponseEntity&lt;Object&gt;</code> stampa il risultato di
     * {@link it.progettoOOP.service.ServizioFileImpl#mediaNumeroParole()}
     */
    @PostMapping("/filtraData")
    public ResponseEntity<Object> filtraPerData(@RequestBody String jsonRequest) {
        JSONObject json = new JSONObject(jsonRequest);
        LocalDateTime dataInizio = null, dataFine = null;
        String dataI, oraI, dataF, oraF;
        try {
            if (json.getJSONObject("dataInizio").isNull("data") || json.getJSONObject("dataInizio").isNull("ora")
                    || json.getJSONObject("dataFine").isNull("data") || json.getJSONObject("dataFine").isNull("ora")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "il Json non è ben formato");
            }
        } catch (Exception ignored) {
        }
        try {
            dataI = json.getJSONObject("dataInizio").getString("data");
            oraI = json.getJSONObject("dataInizio").getString("ora");
            dataInizio = LocalDateTime.of(Integer.parseInt(dataI.split("/")[2]), Integer.parseInt(dataI.split("/")[1]),
                    Integer.parseInt(dataI.split("/")[0]), Integer.parseInt(oraI.split(":")[0]),
                    Integer.parseInt(oraI.split(":")[1]), Integer.parseInt(oraI.split(":")[2]));
        } catch (Exception ignored) {
        }

        try {
            dataF = json.getJSONObject("dataFine").getString("data");
            oraF = json.getJSONObject("dataFine").getString("ora");
            dataFine = LocalDateTime.of(Integer.parseInt(dataF.split("/")[2]), Integer.parseInt(dataF.split("/")[1]),
                    Integer.parseInt(dataF.split("/")[0]), Integer.parseInt(oraF.split(":")[0]),
                    Integer.parseInt(oraF.split(":")[1]), Integer.parseInt(oraF.split(":")[2]));
        } catch (Exception ignored) {
        }

        return new ResponseEntity<>(servizioFile.filtraPerData(dataInizio, dataFine).toString(), HttpStatus.OK);
    }

    /**
     * @param id L'id del file Dropbox
     * @return <code>ResponseEntity&lt;Object&gt;</code> descrizione dell'esito del metodo
     */
    @GetMapping("/cancellaFile")
    public ResponseEntity<Object> cancellaFile(@RequestParam String id) {
        if (servizioFile.cancellaFile(id))
            return new ResponseEntity<>("Il file " + servizioFile.getInformazioniFile(id).getNome() + "è stato cancellato dalla memoria locale", HttpStatus.OK);
        return new ResponseEntity<>("Il file non è stato trovato", HttpStatus.OK);
    }
}

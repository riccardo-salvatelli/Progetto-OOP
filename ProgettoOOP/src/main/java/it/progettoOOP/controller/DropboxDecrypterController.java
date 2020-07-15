package it.progettoOOP.controller;

import it.progettoOOP.exception.ChiaviNullException;
import it.progettoOOP.exception.dataMancanteException;
import it.progettoOOP.exception.divZeroException;
import it.progettoOOP.service.ServizioFile;

import java.time.LocalDateTime;
import java.util.Collection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.progettoOOP.model.Credenziali;
import it.progettoOOP.model.File;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

// Necessario altrimenti il controller cerca ServizioFileImpl solamente all'interno del package "controller"
@ComponentScan({"it.progettoOOP.service"})
@RestController
public class DropboxDecrypterController {

    @Autowired
    ServizioFile servizioFile;

    @GetMapping("/getListaFile")
    public ResponseEntity<Object> getListaFile() {

        return new ResponseEntity<>(servizioFile.getListaFile(), HttpStatus.OK);
    }

    @PostMapping("/scarica")
    public ResponseEntity<Object> scarica(@RequestBody Credenziali cre) {
        try {
            servizioFile.scaricaFile(cre.getId(), cre.getChiavi());
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id errato");
        } catch (ChiaviNullException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMsg());
        }
        return new ResponseEntity<>("Ho scaricato il file criptato " + servizioFile.getInformazioniFile(cre.getId()).getNome(), HttpStatus.OK);
    }

    @GetMapping("/listaLocale")
    public Collection<File> getListaLocale() {
        return servizioFile.getFiles();
    }

    // restituisce il numero di file di testo scaricati
    @GetMapping("/numeroTxt")
    public ResponseEntity<Object> getTxt() {
        return new ResponseEntity<>("Il numero di file di testo (.txt) è: " + servizioFile.numeroTxt(), HttpStatus.OK);
    }

    @GetMapping("/numeroImm")
    public ResponseEntity<Object> getImm() {
        return new ResponseEntity<>("Il numero di immagini (.jpeg .png .jpg) è " + servizioFile.numeroImm(),
                HttpStatus.OK);
    }

    @GetMapping("/mediaParole")
    public ResponseEntity<Object> getMediaNumeroParole() {
        try {
            return new ResponseEntity<>("La media di parole trovate nei testi è " + servizioFile.mediaNumeroParole(),
                    HttpStatus.OK);
        } catch (divZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }

    @GetMapping("/mediaFrasi")
    public ResponseEntity<Object> getMediaNumeroFrasi() {
        try {
            return new ResponseEntity<>("La media di frasi trovate nei testi è " + servizioFile.mediaNumeroFrasi(),
                    HttpStatus.OK);
        } catch (divZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }

    @GetMapping("/mediaPixel")
    public ResponseEntity<Object> getMediaNumeroPixel() {
        try {
            return new ResponseEntity<>("La media di pixel trovate nei testi è " + servizioFile.mediaNumeroParole(),
                    HttpStatus.OK);
        } catch (divZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }

    @GetMapping("/mediaDimImm")
    public ResponseEntity<Object> getMediaDimensionImmagini() {
        double[] mediaDimImm;
        try {
            mediaDimImm = servizioFile.mediaDimensioniImmagini();
            return new ResponseEntity<>(
                    "La media delle dimensioni delle immagini è " + mediaDimImm[0] + "x" + mediaDimImm[1],
                    HttpStatus.OK);
        } catch (divZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);

        }

    }

    @GetMapping("/statAutori")
    public ResponseEntity<Object> getStatAutori() {
        try {
            return new ResponseEntity<>("La percentuale di caricamento degli autori è" + servizioFile.statAutori(),
                    HttpStatus.OK);
        } catch (divZeroException e) {
            return new ResponseEntity<>(e.getMsg(), HttpStatus.OK);
        }
    }

    // Le date che vanno fornite nel body della richiesta devono avere il formato:
    // gg/mm/AAAA; l'ora: HH:mm:ss. Se si
    // vuole omettere la dataInizio o la dataFine basta settare dataInizio/dataFine
    // a 0 (int)
    // esempio parametri
//		{
//			"dataInizio": 0,
//			"dataFine": {
//				"data": "12/07/2020",
//				"ora": "12:00:00"
//			}
//		}
    @PostMapping("/filtraData")
    public ResponseEntity<Object> filtraPerData(@RequestBody String jsonRequest) {
        JSONObject json = new JSONObject(jsonRequest);
        LocalDateTime dataInizio = null, dataFine = null;
        String dataI, oraI, dataF, oraF;
        try {
            if (json.getJSONObject("dataInizio").isNull("data") || json.getJSONObject("dataInizio").isNull("ora")
                    || json.getJSONObject("dataFine").isNull("data") || json.getJSONObject("dataFine").isNull("ora")) {
                throw new dataMancanteException();
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
}

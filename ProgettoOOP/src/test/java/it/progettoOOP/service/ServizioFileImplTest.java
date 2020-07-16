package it.progettoOOP.service;

import it.progettoOOP.exception.ChiaviErrateException;
import it.progettoOOP.exception.ChiaviNullException;
import it.progettoOOP.exception.DivZeroException;
import it.progettoOOP.model.Credenziali;
import it.progettoOOP.model.File;
import org.junit.jupiter.api.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ServizioFileImplTest {
    private ServizioFileImpl servizio;
    private Exception exception;

    @BeforeEach
    void setUp() {
        servizio = new ServizioFileImpl();
    }

    @AfterEach
    void tearDown() {}

    @Test
    @DisplayName("Aggiunta file generico corretto")
    void aggiungiFile1() {
        File file = new File("fileCheNonEsiste", "path/inesistente", "id:udSkk_E_5_sAAAAAAAAAw", 1, "autore", LocalDateTime.now());
        try {
            assertTrue(servizio.aggiungiFile(file));
        } catch (ChiaviErrateException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Aggiunta 2 file uguali")
    void aggiungiFile2() {
        File file = new File("fileCheNonEsiste", "path/inesistente", "id:udSkk_E_5_sAAAAAAAAAw", 1, "autore", LocalDateTime.now());
        exception = assertThrows(ResponseStatusException.class, () -> servizio.aggiungiFile(file));
        assertEquals("400 BAD_REQUEST \"Questo file è gia presente ..\"",
                exception.getMessage());
    }

    @Test
    @DisplayName("getInformazioni id errato")
    void getInformazioniFile() {
        String idErrato = "idErratoCasuale";
        exception = assertThrows(HttpClientErrorException.class, () -> servizio.getInformazioniFile(idErrato));
        assertEquals("400 Bad Request: [Error in call to API function \"files/get_metadata\": request body: path: '" + idErrato + "' did not match pattern '(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)']", exception.getMessage());
    }

    @DisplayName("Scarica immagine corrotta")
    @Test
    void scaricaFile1() {
        double[] chiavi = new double[]{3, 3}; //chiavi errate
        String idImmagine = "id:udSkk_E_5_sAAAAAAAAAvg";
        Credenziali cre = new Credenziali(idImmagine, chiavi);
        exception = assertThrows(ChiaviErrateException.class, () -> servizio.scaricaFile(cre));
        assertEquals("Chiavi errate, l'immagine è corrotta", ((ChiaviErrateException) exception).getMsg());
    }

    @DisplayName("Scarica testo corrotto")
    @Test
    void scaricaFile2() {
        double[] chiavi = new double[]{3, 3}; //chiavi errate
        String idTesto = "id:udSkk_E_5_sAAAAAAAAAwQ";
        Credenziali cre = new Credenziali(idTesto, chiavi);
        exception = assertThrows(ChiaviErrateException.class, () -> servizio.scaricaFile(cre));
        assertEquals("Chiavi errate, file di testo corrotto", ((ChiaviErrateException) exception).getMsg());
    }

    @DisplayName("Scarica con chiave null")
    @Test
    void scaricaFile3() {
        String idGenerico = "id:udSkk_E_5_sAAAAAAAAAxg";
        Credenziali cre = new Credenziali(idGenerico, null);
        exception = assertThrows(ChiaviNullException.class, () -> servizio.scaricaFile(cre));
        assertEquals("Chiavi mancanti", ((ChiaviNullException) exception).getMsg());
    }

    @DisplayName("statAutori con 0 file")
    @Test
    void statAutori() {
        exception = assertThrows(DivZeroException.class, () -> servizio.statAutori());
        assertEquals("Non ci sono file scaricati.", ((DivZeroException) exception).getMsg());
    }

    @DisplayName("test filtra date")
    @Test
    void filtraPerData() throws ChiaviErrateException {
        File file1 = new File("nomeFile1", "percorsoFile1", "id:udSkk_E_5_sAAAAAAAAAvQ", 12, "Riccardo", LocalDateTime.of(2020, 11, 15, 15, 15));
        servizio.aggiungiFile(file1);
        File file2 = new File("nomeFile2", "percorsoFile2", "id:udSkk_E_5_sAAAAAAAAAmq", 15, "Alessandro", LocalDateTime.of(2020, 11, 20, 15, 15));
        servizio.aggiungiFile(file2);
        assertEquals(file2, servizio.filtraPerData(LocalDateTime.of(2020, 11, 15, 15, 16), null).firstElement());
    }
}
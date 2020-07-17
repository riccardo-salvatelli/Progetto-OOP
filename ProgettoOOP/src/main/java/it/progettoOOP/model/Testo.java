package it.progettoOOP.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import it.progettoOOP.exception.ChiaviErrateException;


/**
 * <b>Testo</b> Introduce alcuni parametri caratteristici dei file di testo (.txt).
 */
public class Testo extends File {

    /**
     * Indica il numero di caratteri presenti all'interno del testo
     */
    private int numCaratteri;
    /**
     * Indica il numero di parole presenti all'interno del testo
     */
    private int numParole;
    /**
     * Indica il numero di frasi presenti all'interno del testo
     */
    private int numFrasi;

    /**
     * @param nome               Nome del file
     * @param percorso           Percorso assoluto del file
     * @param id                 ID di DropBox associato al file
     * @param dimensione         Dimensione del file indicata in byte
     * @param autore             Nome e cognome dell'utente che ha caricato il file
     * @param dataUltimaModifica L'ultima data di modifica registrata da DropBox
     * @throws ChiaviErrateException Viene invocata quando {@link Testo#isTesto(String, String)} risulta falso.
     */
    public Testo(String nome, String percorso, String id, int dimensione, String autore,
                 LocalDateTime dataUltimaModifica) throws ChiaviErrateException {
        super(nome, percorso, id, dimensione, autore, dataUltimaModifica);
        if (!isTesto(percorso, nome)) {
        	java.io.File file = new java.io.File(percorso + "/" + nome);
        	file.delete();
        	throw new ChiaviErrateException("Chiavi errate, file di testo corrotto");}
        this.numFrasi = this.conteggioNumeroFrasi();
        this.numParole = this.conteggioNumeroParole();
        this.numCaratteri = this.conteggioNumeroCaratteri();
    }

    public void setNumFrasi(int numFrasi) {
        this.numFrasi = numFrasi;
    }

    public int getNumCaratteri() {
            return numCaratteri;
        
    }

    public void setNumCaratteri(int numCaratteri) {
        this.numCaratteri = numCaratteri;
    }

    public int getNumParole() {
        return numParole;
    }

    public void setNumParole(int numParole) {
        this.numParole = numParole;
    }

    public int getNumFrasi() {
        return this.numFrasi;
    }

    /**
     * Viene chiamato dal costruttore per calcolare il numero di frasi.*
     * @return Numero di frasi contate
     */
    public int conteggioNumeroFrasi() {
        int i = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.getPercorso() + "/" + this.getNome()));
            int next = reader.read();
            if (next != -1) {
                do {

                    if ((char) next == '.')
                        i++; // conto quanti punti incontro così da sapere le frasi
                    next = reader.read();
                } while (next != -1);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Viene chiamato dal costruttore per calcolare il numero di parole.
     * @return Numero di parole contate
     */
    public int conteggioNumeroParole() {
        int i = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.getPercorso() + "/" + this.getNome()));
            int next = reader.read();
            if (next != -1) {
                do {

                    if ((char) next == ' ')
                        i++; // conto quanti spazi incontro così da sapere le parole
                    next = reader.read();
                } while (next != -1);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Viene chiamato dal costruttore per calcolare il numero di caratteri.
     * @return Numero di caratteri contati
     */
    public int conteggioNumeroCaratteri() {
        int i = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.getPercorso() + "/" + this.getNome()));
            int next = reader.read();
            if (next != -1) {
                do {

                    next = reader.read();
                    i++;
                } while (next != -1);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    //Questo metodo controlla se un file di testo è stato scaricato correttamente verificando il tipo di codifica.
    //ritorna true se è stato decriptato correttamente, false se c'è stato un errore nella decriptazione.
    // il false si verifica nel caso in cui le chiavi usate siano sbagliate

    /**
     * Controlla se il file di testo che è stato scaricato è leggibile verificando che sia scritto
     * secondo la codifica <code>ISO 8859-1</code>
     *
     * @param percorso Il percorso assoluto del file
     * @param nome     Il nome del file
     * @return <code>boolean</code> che rappresenta lo stato del testo
     */
    public boolean isTesto(String percorso, String nome) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(percorso + "/" + nome));
            String next;
            next = reader.readLine();
            while (next != null) {
                if (!StandardCharsets.ISO_8859_1.newEncoder().canEncode(next)) {
                    reader.close();
                    return false;
                }
                next = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
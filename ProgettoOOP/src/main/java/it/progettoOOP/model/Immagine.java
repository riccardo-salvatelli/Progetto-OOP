package it.progettoOOP.model;

import javax.imageio.ImageIO;

import it.progettoOOP.exception.ChiaviErrateException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.File;

/**
 * <b>Immagine</b> racchiude gli attributi e i metodi per raccogliere dati riguardo ogni singola immagine.
 * Nello specifico le dimensioni dell'immagine, quindi il numero dei pixel.
 */
public class Immagine extends it.progettoOOP.model.File {
	/**
	 * Il numero di pixel viene determinato in fase di istanza dell'oggetto, all'interno del costruttore,
	 * a seguito dell'esecuzione di trovaRisoluzione.
	 */
	private int numPixel;
	private int[] dimImmagine = new int[2];

	/**
	 *
	 * @param nome               Nome del file
	 * @param percorso           Percorso assoluto del file
	 * @param id                 ID di DropBox associato al file
	 * @param dimensione         Dimensione del file indicata in byte
	 * @param autore             Nome e cognome dell'utente che ha caricato il file
	 * @param dataUltimaModifica L'ultima data di modifica registrata da DropBox
	 * @throws ChiaviErrateException Viene invocata quando non si riesce ad ottenere la risoluzione dell'immagine,
	 * cioè quando l'immagine è corrotta.
	 */
	public Immagine(String nome, String percorso, String id, int dimensione, String autore,
			LocalDateTime dataUltimaModifica) throws ChiaviErrateException {
		super(nome, percorso, id, dimensione, autore, dataUltimaModifica);
		this.dimImmagine = trovaRisoluzione();
		this.numPixel = this.dimImmagine[0] * this.dimImmagine[1];
	}

	/**
	 * trovaRisoluzione sfrutta l'immagine salvata per estrapolare la risoluzione della stessa.
	 * @return <code>int[]</code> il primo valore rappresenta la larghezza e il secondo l'altezza.
	 * @throws ChiaviErrateException Viene invocata quando l'immagine è danneggiata per cui non si riesce a estrapolarle le dimensioni.
	 */
	public int[] trovaRisoluzione() throws ChiaviErrateException {
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(new File(this.getPercorso() + "/" + this.getNome()));
			if (bimg == null) {
				throw new ChiaviErrateException("Chiavi errate, l'immagine è corrotta");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new int[] { bimg.getWidth(), bimg.getHeight() };
	}

	public int getNumPixel() {
		return numPixel;
	}

	public void setNumPixel(int numPixel) {
		this.numPixel = numPixel;
	}

	public int[] getDimImmagine() {
		return dimImmagine;
	}

	public void setDimImmagine(int[] dimImmagine) {
		this.dimImmagine = dimImmagine;
	}

}
package it.progettoOOP.model;

import javax.imageio.ImageIO;

import it.progettoOOP.exception.ChiaviNullException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.File;

public class Immagine extends it.progettoOOP.model.File {
	private int numPixel;
	private int[] dimImmagine = new int[2];

	public Immagine(String nome, String percorso, String id, int dimensione, String autore,
			LocalDateTime dataUltimaModifica) throws ChiaviNullException {
		super(nome, percorso, id, dimensione, autore, dataUltimaModifica);

		try {
			this.dimImmagine = trovaRisoluzione();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.numPixel = this.dimImmagine[0] * this.dimImmagine[1];
	}

	public int[] trovaRisoluzione() throws IOException, ChiaviNullException {
		try {
			BufferedImage bimg = ImageIO.read(new File(this.getPercorso() + "/" + this.getNome()));
			if (bimg == null) {
				throw new ChiaviNullException("Chiavi errate, l'immagine è corrotta");
			}
			return new int[] { bimg.getWidth(), bimg.getHeight() };
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}

//		catch (NullPointerException e) {
//			e.printStackTrace();
//			new ChiaviNullException("Chiave di decriptazione errata, immagine corrotta");
//		}
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
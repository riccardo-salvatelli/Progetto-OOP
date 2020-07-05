package it.progettoOOP.model;

import it.progettoOOP.exception.imageWidthHeightException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.io.File;

public class Immagine extends it.progettoOOP.model.File {
	private int numPixel;
	private int[] dimImmagine = new int[2];

	public Immagine(String nome,
					String percorso,
					String id,
					int dimensione,
					String autore,
					Date dataUltimaModifica) {
		super(nome, percorso, id, dimensione, autore, dataUltimaModifica);

		try {
			this.dimImmagine = trovaRisoluzione();
		} catch (imageWidthHeightException e) {
			e.printStackTrace();
		}

		this.numPixel = this.dimImmagine[0] * this.dimImmagine[1];
	}

	public int [] trovaRisoluzione() throws imageWidthHeightException {
		try {
			BufferedImage bimg = ImageIO.read(new File(this.getNome()));
			return new int[] {bimg.getWidth(), bimg.getHeight()};
		} catch (IOException e) {
			e.printStackTrace();
			throw new imageWidthHeightException();
		}
	}

	public int getNumPixel(){
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

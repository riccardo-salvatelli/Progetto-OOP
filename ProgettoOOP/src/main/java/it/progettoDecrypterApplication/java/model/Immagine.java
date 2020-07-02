package it.progettoDecrypterApplication.java.model;

import it.progettoOOP.DropboxDecrypter.exception.NumPixelException;

public class Immagine extends File {

	private int numPixel;
	private int[] dimImmagine = new int[2];

	public Immagine(int numPixel, int[] dimImmagine) {
		super();
		this.numPixel = numPixel;
		this.dimImmagine = dimImmagine;
	}

	public Immagine() {
		super();
		this.numPixel = -1;
	}

	public int getNumPixel() throws NumPixelException {
		if (numPixel >= 0)
			return numPixel;
		else
			throw new NumPixelException();
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

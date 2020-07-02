package it.progettoDecrypterApplication.java.model;
import it.progettoOOP.DropboxDecrypter.exception.NumPixelException;

public class Immagine extends File {
	
	private int numPixel;

	public Immagine(int numPixel) {
		super();
		this.numPixel = numPixel;
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
	
	

}

package it.progettoDecrypterApplication.java.model;

import it.progettoOOP.DropboxDecrypter.exception.NumCaratteriException;

public class Testo extends File {
	
	private int numCaratteri;
	public Testo(int numCaratteri) {
		super();
		this.numCaratteri=numCaratteri;
		// TODO Auto-generated constructor stub
	}
	public Testo() {
		super();
		this.numCaratteri = -1;
	}
	public int getNumCaratteri() throws NumCaratteriException {
		if (numCaratteri>=0)
			return numCaratteri;
		else 
			throw new NumCaratteriException();
	}
	public void setNumCaratteri(int numCaratteri) {
		this.numCaratteri = numCaratteri;
	}

	
}

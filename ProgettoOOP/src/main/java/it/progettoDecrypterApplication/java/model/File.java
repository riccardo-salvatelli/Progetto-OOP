package it.progettoDecrypterApplication.java.model;


public class File {
	private int dimensione;

	public int getDimensione() {
		return dimensione;
	}

	public void setDimensione(int dim) {
		this.dimensione = dim;
	}

	public File(int dimensione) {
		this.dimensione = dimensione;
	}
	public File() {
		this.dimensione = 0;
	}
	
}

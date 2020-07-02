package it.progettoOOP.model;

import it.progettoOOP.exception.NumCaratteriException;

public class Testo extends File {

	private int numCaratteri;
	private int numParole;
	private int numFrasi;

	public Testo(int numCaratteri, int numParole, int numFrasi) {
		super();
		this.numCaratteri = numCaratteri;
		this.numParole = numParole;
		this.numFrasi = numFrasi;
		// TODO Auto-generated constructor stub
	}

	public Testo() {
		super();
		this.numCaratteri = -1;
	}

	public int getNumCaratteri() throws NumCaratteriException {
		if (numCaratteri >= 0)
			return numCaratteri;
		else
			throw new NumCaratteriException();
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
		return numFrasi;
	}

	public void setNumFrasi(int numFrasi) {
		this.numFrasi = numFrasi;
	}

}

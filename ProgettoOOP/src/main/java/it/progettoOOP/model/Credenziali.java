package it.progettoOOP.model;

import it.progettoOOP.exception.ChiaviNullException;

public class Credenziali {
	private String id;
	private double[] chiavi;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double[] getChiavi() throws ChiaviNullException {
		if (chiavi == null)
			throw new ChiaviNullException("Chiavi mancanti");
		return chiavi;
	}

	public void setChiavi(double[] chiavi) {
		this.chiavi = chiavi;
	}

	public Credenziali(String id, double[] chiavi) {
		this.id = id;
		this.chiavi = chiavi;
	}

}

package it.progettoOOP.model;

public class Credenziali {
	private String id;
	private double [] chiavi;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double[] getChiavi() {
		return chiavi;
	}

	public void setChiavi(double[] chiavi) {
		this.chiavi = chiavi;
	}

	public Credenziali(String id, double[] chiavi) {
		super();
		this.id = id;
		this.chiavi = chiavi;
	}

}

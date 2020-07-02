package it.progettoDecrypterApplication.java.model;

import java.util.Date;

public class File {

	private String nome;
	private String percorso;
	private int id;
	private int dimensione;
	private String autore;
	private Date dataCreazione;
	private Date dataUltimaModifica;
	
	
	public File() {
		this.nome = null;
		this.percorso = null;
		this.id = -1;
		this.dimensione = -1;
		this.autore = null;
		this.dataCreazione = null;
		this.dataUltimaModifica = null;
	}


	public File(String nome, String percorso, int id, int dimensione, String autore, Date dataCreazione,
			Date dataUltimaModifica) {
		this.nome = nome;
		this.percorso = percorso;
		this.id = id;
		this.dimensione = dimensione;
		this.autore = autore;
		this.dataCreazione = dataCreazione;
		this.dataUltimaModifica = dataUltimaModifica;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getPercorso() {
		return percorso;
	}


	public void setPercorso(String percorso) {
		this.percorso = percorso;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getDimensione() {
		return dimensione;
	}


	public void setDimensione(int dimensione) {
		this.dimensione = dimensione;
	}


	public String getAutore() {
		return autore;
	}


	public void setAutore(String autore) {
		this.autore = autore;
	}


	public Date getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}


	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

}
	

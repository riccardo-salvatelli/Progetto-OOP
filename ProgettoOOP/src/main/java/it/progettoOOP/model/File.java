package it.progettoOOP.model;

import java.time.LocalDateTime;

public class File {

	private String nome;
	private String percorso;
	private String id;	
	private int dimensione;
	private String autore;
	private LocalDateTime dataUltimaModifica;
	
	
	public File() {
		this.nome = null;
		this.percorso = null;
		this.id = null;
		this.dimensione = -1;
		this.autore = null;
		this.dataUltimaModifica = null;
		
	}

	public File(String nome) {
		this.nome = nome;
	}
	public File(String nome, String percorso, String id, int dimensione, String autore,
			LocalDateTime dataUltimaModifica) {
		this.nome = nome;
		this.percorso = percorso;
		this.id = id;
		this.dimensione = dimensione;
		this.autore = autore;
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
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


	public LocalDateTime getDataUltimaModifica() {
		return dataUltimaModifica;
	}


	public void setDataUltimaModifica(LocalDateTime dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	@Override
	public String toString() {
		return "File{" +
				"nome='" + nome + '\'' +
				", percorso='" + percorso + '\'' +
				", id='" + id + '\'' +
				", dimensione=" + dimensione +
				", autore='" + autore + '\'' +
				", dataUltimaModifica=" + dataUltimaModifica +
				'}';
	}

}
	

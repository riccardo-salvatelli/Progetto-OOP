package it.progettoOOP.model;

import java.time.LocalDateTime;

public class File {

	private String nome;
	private String percorso;
	private String id;
	private int dimensione;
	private String autore;
	private LocalDateTime dataUltimaModifica;
	private String tipoFile;

	public File() {
		this.nome = null;
		this.percorso = null;
		this.id = null;
		this.dimensione = -1;
		this.autore = null;
		this.dataUltimaModifica = null;
		this.tipoFile = null;
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
		this.tipoFile = tipoFile(nome);
	}


	//questo metodo ritorna una stringa per dire se Ã¨ un file
	// di testo o immagine. Se non Ã¨ nessuna delle due ritorna null
	public String tipoFile (String nomeFile) {
		String estensione = nomeFile.split("\\.")[nomeFile.split("\\.").length - 1];
		if(estensione.equals("txt")) {
			return "Testo";
		}else if (estensione.equals("jpeg") || estensione.equals("png") || estensione.equals("jpg")){
			return "Immagine";
		}
		return "File";
	}

	public String getTipoFile() {
		return tipoFile;
	}



	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
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
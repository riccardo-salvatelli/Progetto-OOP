package it.progettoOOP.exception;

public class scaricaFileException extends Exception {

	public scaricaFileException() {
		super();
		System.out.println("Errore nel download del file");
	}

}

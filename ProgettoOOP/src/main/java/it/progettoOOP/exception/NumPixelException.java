package it.progettoOOP.exception;

public class NumPixelException extends Exception {

	public NumPixelException() {
		super();
		System.out.println("Il numero di pixel di questa immagine non è valido.");
		// TODO Auto-generated constructor stub
	}
	public NumPixelException(String msg) {
		super();
		System.out.println("Il numero di pixel di questa immagine non è valido.");
		System.out.println(msg);
		// TODO Auto-generated constructor stub
	}

}

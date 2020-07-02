package it.progettoOOP.exception;

public class NumCaratteriException extends Exception {

	public NumCaratteriException() {
		super();
		System.out.println("Il numero di caratteri di questo file di testo non è valido.");
		// TODO Auto-generated constructor stub
	}
	public NumCaratteriException(String msg) {
		super();
		System.out.println("Il numero di caratteri di questo file di testo non è valido.");
		System.out.print(msg);
	}
}

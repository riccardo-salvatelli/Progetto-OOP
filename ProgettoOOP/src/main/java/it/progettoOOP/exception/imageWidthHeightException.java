package it.progettoOOP.exception;

public class imageWidthHeightException extends Exception {

	public imageWidthHeightException() {
		super();
		System.out.println("Non è stato possibile ottenere le dimensioni dell'immagine");
	}
	public imageWidthHeightException(String msg) {
		super();
		System.out.println("Non è stato possibile ottenere le dimensioni dell'immagine");
		System.out.println(msg);
	}

}

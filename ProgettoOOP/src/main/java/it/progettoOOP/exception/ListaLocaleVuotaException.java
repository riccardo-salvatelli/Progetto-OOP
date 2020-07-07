package it.progettoOOP.exception;

public class ListaLocaleVuotaException extends Exception {

	public ListaLocaleVuotaException() {
		super();
		System.out.println("Non ci sono file scaricati localmente");
	}

}

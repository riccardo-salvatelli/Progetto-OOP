package it.progettoOOP.exception;

public class ChiaviNullException extends Exception {
	String msg;

	public ChiaviNullException(String msg) {
		super();
		this.msg = msg;
	}
	/**
	 * Restituisce un messaggio di errore passato dal costruttore quando le chiavi di decriptazione sono <code>null</code>.
	 * @return <code>String</code> è il messaggio d'errore che viene stampato
	 */
	public String getMsg() {
		return msg;
	}
}

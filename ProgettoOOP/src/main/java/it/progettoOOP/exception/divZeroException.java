package it.progettoOOP.exception;

public class divZeroException extends Exception {
	String msg;

	public divZeroException(String msg) {
		super();
		this.msg = msg;
	}
	/**
	 *
	 * Restituisce un messaggio di errore passato dal costruttore quando avviene una divisione per zero.
	 * @return <code>String</code> è il messaggio d'errore che viene stampato
	 */
	public String getMsg() {
		return msg;
	}

}

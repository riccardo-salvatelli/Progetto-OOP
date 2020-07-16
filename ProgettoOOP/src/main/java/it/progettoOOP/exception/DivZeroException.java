package it.progettoOOP.exception;

public class DivZeroException extends Exception {
	String msg;

	public DivZeroException(String msg) {
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

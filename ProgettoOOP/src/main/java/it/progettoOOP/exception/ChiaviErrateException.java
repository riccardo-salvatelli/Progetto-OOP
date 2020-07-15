package it.progettoOOP.exception;

public class ChiaviErrateException extends Exception{
	String msg;
	
	public ChiaviErrateException(String msg) {
		super();
		this.msg = msg;
	}
	
	/**
	 * Restituisce un messaggio di errore passato dal costruttore quando le chiavi di decriptazione sono sbagliate
	 * @return <code>String</code> è il messaggio d'errore che viene stampato
	 */
	public String getMsg() {
		return msg;
	}

}

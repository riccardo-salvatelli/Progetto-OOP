package it.progettoOOP.exception;

public class ChiaviNullException extends Exception{
	String msg;

	public ChiaviNullException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}

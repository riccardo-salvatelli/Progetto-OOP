package it.progettoOOP.exception;

public class divZeroException extends Exception {
	String msg;

	public divZeroException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}

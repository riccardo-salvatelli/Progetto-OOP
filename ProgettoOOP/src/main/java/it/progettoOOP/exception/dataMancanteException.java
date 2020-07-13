package it.progettoOOP.exception;

public class dataMancanteException extends Exception{
    public dataMancanteException(){
        super();
        System.out.println("Data mancante nel body della richiesta");
    }
}

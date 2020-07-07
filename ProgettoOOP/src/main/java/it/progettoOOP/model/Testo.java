package it.progettoOOP.model;
import java.io.*;
import java.time.LocalDateTime;
import it.progettoOOP.exception.NumCaratteriException;

public class Testo extends File {

	private int numCaratteri;
	private int numParole;
	private int numFrasi;

	public Testo(String nome, String percorso, String id, int dimensione, String autore,
			LocalDateTime dataUltimaModifica) {
		super(nome, percorso, id, dimensione,autore, dataUltimaModifica);
		this.numFrasi = this.conteggioNumeroFrasi();
		this.numParole = this.conteggioNumeroParole();
		this.numCaratteri = this.conteggioNumeroCaratteri();
	}

	public void setNumFrasi(int numFrasi) {
		this.numFrasi = numFrasi;
	}
	public int getNumCaratteri() throws NumCaratteriException {
		if (numCaratteri >= 0)
			return numCaratteri;
		else
			throw new NumCaratteriException();
	}

	public void setNumCaratteri(int numCaratteri) {
		this.numCaratteri = numCaratteri;
	}

	public int getNumParole() {
		return numParole;
	}

	public void setNumParole(int numParole) {
		this.numParole = numParole;
	} 

	public int getNumFrasi() {	
		 return this.numFrasi;
	}

	
	public int conteggioNumeroFrasi () {
		int i = 0;
		 try {
		      BufferedReader reader = new BufferedReader(new FileReader(this.getPercorso()));
		      int next = reader.read();
		      if(next !=-1) {
		        do {
		          if ((char)next == '.') i++;		//conto quanti punti incontro così da sapere le frasi
		          next = reader.read();
		        }while(next != -1);
		      reader.close();
		      }
		      return i;
		    }
		    catch(IOException e) {
		      System.out.println("C'è stato un ERRORE!");
		      System.out.println(e);
		      return -1;
		    }
	  }
	
	public int conteggioNumeroParole() {
		int i = 0;
		 try {
		      BufferedReader reader = new BufferedReader(new FileReader(this.getPercorso()));
		      int next = reader.read();
		      if(next !=-1) {
		        do {
		          if ((char)next == ' ') i++;		//conto quanti spazi incontro così da sapere le parole
		          next = reader.read();
		        }while(next != -1);
		      reader.close();
		      }										
		      return i;
		    }
		    catch(IOException e) {
		      System.out.println("C'è stato un ERRORE!");
		      System.out.println(e);			// se entra nel catch c'è stato un errore
		      return -1;
		    }	
	}
	
	public int conteggioNumeroCaratteri() {
		int i = 0;
		 try {
		      BufferedReader reader = new BufferedReader(new FileReader(this.getPercorso()));
		      int next = reader.read();
		      if(next !=-1) {
		        do {		//conto quanti spazi incontro così da sapere i caratteri
		          next = reader.read();
		          i++;
		        }while(next != -1);
		      reader.close();
		      }										
		      return i;
		    }
		    catch(IOException e) {
		      System.out.println("C'è stato un ERRORE!");
		      System.out.println(e);			// se entra nel catch c'è stato un errore 
		      return -1;
		    }	
	}
}


package module;

import java.io.*;

public class Decriptazione {
	private int[] chiave = new int[2];
	private String url;

	//Questa funzione prende in input un file (file) e un array di valori interi (sequenza). legge il file byte per
	// byte e per ognuno di essi ne esegue lo xor rispetto al corrispondente i-esimo valore della sequenza. Salva il
	// risultato di questa operazione in un BufferedOutputStream che restituisce al termine del metodo.
	public BufferedOutputStream chaosXOR(File file, int[] sequenza){
		BufferedOutputStream bufferedOutputStream = null;
		BufferedInputStream reader;
		int i;
		int unByte;

		try {
			reader = new BufferedInputStream(new FileInputStream(file));
			for(i=0; i<sequenza.length; i++) {
				unByte = reader.read();				//legge byte per byte del file
				bufferedOutputStream.write(unByte^sequenza[i]); 	//esegue lo xor tra i due valori e lo mette nel bufferedOutputStream
			}
			reader.close();							//chiude il flusso di input


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) { //gestisce l'eccezione di reader.read()
			e.printStackTrace();
		}
		return bufferedOutputStream;
	}
}

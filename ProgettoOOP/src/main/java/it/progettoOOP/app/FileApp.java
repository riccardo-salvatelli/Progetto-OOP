package it.progettoOOP.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.progettoOOP.model.File; 

public class FileApp {

	public FileApp() {
		// TODO Auto-generated constructor stub
	}
	public void popolaFile (ObjectMapper obj, String jSonOut) {
		try{
			File file1 = obj.readValue(jSonOut, File.class);
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}

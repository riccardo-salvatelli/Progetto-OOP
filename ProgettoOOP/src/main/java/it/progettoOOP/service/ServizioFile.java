package it.progettoOOP.service;

import java.util.Collection;
import java.util.HashMap;

import it.progettoOOP.exception.ListaLocaleVuotaException;
import it.progettoOOP.model.File;

public interface ServizioFile {
	public abstract boolean cancellaFile(String id);
	public abstract boolean aggiornaFile(String id, File file);
	public abstract String[] getListaFile();
	public abstract File getInformazioniFile(String id);
	public abstract Collection<File> getFiles() throws ListaLocaleVuotaException;
	public abstract Boolean scaricaFile(String id, double [] chiavi);
	public abstract int numeroTxt();
	public abstract int numeroImm();
	public abstract double mediaNumeroParole();
	public abstract double mediaNumeroFrasi();
	public abstract double mediaNumeroCaratteri();
	public abstract double mediaNumeroPixel();
	public abstract double[] mediaDimensioniImmagini();
	public abstract HashMap<String, Double> statAutori();
}

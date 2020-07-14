package it.progettoOOP.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import it.progettoOOP.exception.ChiaviNullException;
import it.progettoOOP.exception.divZeroException;
import it.progettoOOP.model.File;

public interface ServizioFile {
	public abstract boolean cancellaFile(String id);
	public abstract boolean aggiornaFile(String id, File file);
	public abstract String[] getListaFile();
	public abstract File getInformazioniFile(String id);
	public abstract Collection<File> getFiles();
	public abstract void scaricaFile(String id, double [] chiavi) throws ChiaviNullException;
	public abstract int numeroTxt();
	public abstract int numeroImm();
	public abstract double mediaNumeroParole() throws divZeroException;
	public abstract double mediaNumeroFrasi() throws divZeroException;
	public abstract double mediaNumeroCaratteri() throws divZeroException;
	public abstract double mediaNumeroPixel() throws divZeroException;
	public abstract double[] mediaDimensioniImmagini() throws divZeroException;
	public abstract HashMap<String, Double> statAutori() throws divZeroException;
	public abstract Vector<File> filtraPerData(LocalDateTime dataInizio, LocalDateTime dataFine);
	}

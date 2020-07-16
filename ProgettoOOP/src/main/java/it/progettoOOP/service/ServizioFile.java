package it.progettoOOP.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import it.progettoOOP.exception.ChiaviErrateException;
import it.progettoOOP.exception.ChiaviNullException;
import it.progettoOOP.exception.DivZeroException;
import it.progettoOOP.model.Credenziali;
import it.progettoOOP.model.File;

public interface ServizioFile {
	public abstract boolean aggiungiFile(File file) throws ChiaviErrateException;
	public abstract boolean cancellaFile(String id);
	public abstract boolean aggiornaFile(String id, File file);
	public abstract String[] getListaFile();
	public abstract File getInformazioniFile(String id);
	public abstract Collection<File> getFiles();
	public abstract void scaricaFile(Credenziali cre) throws ChiaviErrateException, ChiaviNullException;
	public abstract int numeroTxt();
	public abstract int numeroImm();
	public abstract double mediaNumeroParole() throws DivZeroException;
	public abstract double mediaNumeroFrasi() throws DivZeroException;
	public abstract double mediaNumeroCaratteri() throws DivZeroException;
	public abstract double mediaNumeroPixel() throws DivZeroException;
	public abstract double[] mediaDimensioniImmagini() throws DivZeroException;
	public abstract HashMap<String, Double> statAutori() throws DivZeroException;
	public abstract Vector<File> filtraPerData(LocalDateTime dataInizio, LocalDateTime dataFine);
	}

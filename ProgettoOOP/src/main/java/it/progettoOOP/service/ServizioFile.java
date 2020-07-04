package it.progettoOOP.service;

import java.util.Collection;

import it.progettoOOP.model.File;

public interface ServizioFile {
	public abstract void aggiungiFile(File file);
	public abstract boolean cancellaFile(String id);
	public abstract boolean aggiornaFile(String id, File file);
	public abstract Collection<File> getFiles();
	
}

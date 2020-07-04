package it.progettoOOP.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ServizioDownload {
	public  post() {
	
	URL url = new URL("https://api.dropboxapi.com/2/files/list_folder");
	URLConnection con = url.openConnection();
	HttpURLConnection http = (HttpURLConnection)con;
	http.setRequestMethod("POST"); // PUT is another valid option
	http.setDoOutput(true);
}
}

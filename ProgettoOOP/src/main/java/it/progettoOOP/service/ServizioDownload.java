package it.progettoOOP.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest.Headers;

public class ServizioDownload {
	
	public void download() {
	RestTemplate restTemplate = new RestTemplate();

	String token =  "TOKEN";
	String url = "https://content.dropboxapi.com/2/files/download";
	String requestJson = "{\"path\":\"\"}";
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.set("Authorization", token);
	headers.set("Dropbox-API-Arg", "{\"path\": \"/Homework/math/Prime_Numbers.txt\"");
	
	HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
	String answer = restTemplate.postForObject(url, entity, String.class);
	System.out.println(answer);
	
	
	/*/ send request and parse result
	ResponseEntity<String> loginResponse = restTemplate
			  .exchange(urlString, HttpMethod.POST, entity, String.class);
			if (loginResponse.getStatusCode() == HttpStatus.OK) {
			  JSONObject userJson = new JSONObject(loginResponse.getBody());
			} else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			  // nono... bad credentials
			}
	*/
	}
	

			
			
		
		
		
		
		
		
	}
}

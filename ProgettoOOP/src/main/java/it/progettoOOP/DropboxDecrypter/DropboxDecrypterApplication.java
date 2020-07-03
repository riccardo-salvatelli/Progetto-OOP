package it.progettoOOP.DropboxDecrypter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import it.progettoOOP.controller.DropboxDecrypterController;

@SpringBootApplication
@ComponentScan(basePackageClasses = DropboxDecrypterController.class)
public class DropboxDecrypterApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(DropboxDecrypterApplication.class, args);
		}
		

}

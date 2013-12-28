package com.example.googletutorial2;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

@Entity
public class Notifica {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key keyN;
	
	@Persistent
	private String mittenteN;
	
	@Persistent
	private String destinatarioN;
	
	//statoLettura può essere s/n
	@Persistent
	private char statoLettura;
	
	//il tipo può essere richiesta/risposta
	@Persistent
	private String tipo;
	
	public Notifica(){}
	
	public Notifica(String mitt, String dest, String tipo){
		//TODO: default di statoLettura = n ?
		this.mittenteN = mitt;
		this.destinatarioN = dest;
		this.tipo = tipo;
	}
	
	public Key getKeyN(){
		return keyN;
	}
	
	public String getMittenteN(){
		return mittenteN;
	}
	
	public String getDestinatarioN(){
		return destinatarioN;
	}

	public char getStatoLett(){
		return statoLettura;	
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setStatoLett(char c){
		this.statoLettura = c;
	}
}

package com.example.googletutorial2;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@Entity
public class Amicizia {
	@PrimaryKey
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key keyA;
	
	@Persistent
	private String mittenteA;
	
	@Persistent
	private String destinatarioA;
	
	@Persistent
	private Date dataRichiesta;
	
	//costruttori
	public Amicizia() { }
    public Amicizia(String mitt, String dest, Date data) {
        this.mittenteA = mitt; 
        this.destinatarioA = dest;
        this.dataRichiesta = data;
    }
	
	public Key getKeyA() {
		   return keyA;
		 }
		
	//TODO: necessario?
	void clearKey() {
		   keyA = null;
		 }
	
//solo metodi GET perchè non è modificabile
	public String getMittente(){
		return mittenteA;
	}
		 
	public String getDestinatario(){
		return destinatarioA;
	}

	public Date getDataRich(){
		return dataRichiesta;
	}
	
}

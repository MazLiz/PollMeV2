package com.example.googletutorial2;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

//questa entity indica l'immagine scelta x ogni poll e x ogni utente
@Entity
public class ImgScelta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key keyIS;
	
	@Persistent
	private Key keyPoll;
	
	@Persistent
	private Key keyImg;
	
	@Persistent
	private String utente;
	
	public ImgScelta(){}
	
	public ImgScelta(Key keyP, Key keyI, String user){
		this.keyPoll = keyP;
		this.keyImg = keyI;
		this.utente = user;
	}
	
	public Key getKeyIS(){
		return keyIS;
	}
	
	//TODO: necessario?
	void clearKey() {
		   keyIS = null;
		 }
	
	public Key getKeyPoll(){
		return keyPoll;
	} 
	
	public Key getKeyImg(){
		return keyImg;
	}
	
	public String getUtente(){
		return utente;
	}
	
	//possibilità di cambiare la propria scelta
	public void setImmagine(Key newImg){
		this.keyImg = newImg;
	}
}

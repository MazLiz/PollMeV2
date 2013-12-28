package com.example.googletutorial2;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

//memorizza le immagini utilizzate nei polls
@Entity
public class PollImage {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key keyPI;
	
	//La prende da MyImage
	@Persistent
	private Key keyImage;
	
	//La prende da Poll
	@Persistent
	private Key keyPoll;
	
	@Persistent
	private String descrizione;
	
	public PollImage(){}
	
	public PollImage(Key img, Key poll){
		this.keyImage = img;
		this.keyPoll = poll;
	}
	
	public Key getKeyPI(){
		return keyPI;
	}
	
	//TODO: necessario?
	void clearKeyPI() {
		   keyPI = null;
		 }
	
	public Key getKeyImg(){
		return keyImage;
	}
	
	public Key getKeyPoll(){
		return keyPoll;
	}
	
	public String getDescrizione(){
		return descrizione;
	}
	
	public void setDescrizione(String descr){
		this.descrizione = descr;
	}
	
}

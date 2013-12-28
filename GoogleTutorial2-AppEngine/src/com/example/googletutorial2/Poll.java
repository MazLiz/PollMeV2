package com.example.googletutorial2;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.google.appengine.api.datastore.Key;

@Entity
@PersistenceCapable(detachable="true")
public class Poll {
	
//TODO: introduzione in ordine di data!
	
	@PrimaryKey
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key keyPoll;
	
	@Persistent
	private String titolo;
	
	@Persistent
	private String creatore;
	
	@Persistent
	private Date dataCreazione;
	
	@Persistent
	private char chiuso;
	
	public Poll(){}
	
	public Poll(String titolo, String creatore, Date dataC){
		this.titolo = titolo;
		this.creatore = creatore;
		this.dataCreazione = dataC;
	}
	
	public Key getKeyPoll(){
		return keyPoll;
	}
	
	void clearKey(){
		keyPoll = null;
	}
	
	public String getTitolo(){
		return titolo;
	}
	public String getCreatore(){
		return creatore;
	}

	public Date getDataCr(){
		return dataCreazione;
	}
	
	public char getChiuso(){
		return chiuso;
	}
	
	public void setTitolo(String titolo){
		this.titolo = titolo;
	}
	
	public void setChiuso (char siNo){
		this.chiuso = siNo;
	}
}

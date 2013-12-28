package com.example.googletutorial2;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

@Entity
public class PollSharing {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key keyPS;
	
	@Persistent
	private Key keyPoll;
	
	@Persistent
	private String mittenteInvito;
	
	@Persistent
	private String destInvito;
	
	@Persistent
	private Date dataInvito;
	
	//il poll è stato letto dall'utente? s/n
	@Persistent
	private char letto;
	//TODO: il creatore è da mettere?
	
	public PollSharing(){}
	
	public PollSharing (Key keyP, String mitt, String dest, Date data){
		this.keyPoll = keyP;
		this.mittenteInvito = mitt;
		this.destInvito = dest;
		this.dataInvito = data;
		//TODO: default di letto a n?
	}
	
	public Key getKeyPS(){
		return keyPS;
	}
	
	//TODO: necessario?
	void clearKey() {
		   keyPS = null;
		 }
	
	public Key getPoll(){
		return keyPoll;
	}
	
	public String getMittInvito(){
		return mittenteInvito;
	}
	
	public String getDestInvito(){
		return destInvito;
	}
	
	public Date getDataInv(){
		return dataInvito;
	}
	
	public char getLetto(){
		return letto;
	}
	
	public void setLetto(char l){
		this.letto = l;
	}
}

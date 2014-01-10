package com.example.googletutorial2;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;



@Entity
@PersistenceCapable(detachable="true")
public class Utente {
	//TODO: cambiata primary key?
	/*@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;*/

	@Id
    @Persistent
    private String email;

    @Persistent
    private String pwd;
    
    @Persistent
    private Key keyImg;
    
    @Persistent
    private String nome;
    
    @Persistent
    private String cognome;
    
    @Persistent
    private Date dataNasc;
    
    @Persistent
    private char sesso;
    
    @Persistent
    private String citta;
    
    @Persistent
    private String provincia;
    
    @Persistent
    private String cap;
    
    @Persistent
    private String nazione;
    
    @Persistent
    private String statoCivile;
    
    @Persistent
    private String istruzione;
    
        
//costruttori
    public Utente(String email) {
    	this.email = email;
    }
    
    public Utente(String email, String password) {
        this.email = email; 
        this.pwd = password;
    }

//metodi GET
    public String getEmail() {
    	   return email;
    	 }
    	 
    
    public String getPwd() {
 	   return pwd;
 	 }
    
    public Key getImg(){ 
    	return keyImg; 
    	}
    
    public String getNome() {
 	   return nome;
 	 }
    
    public String getCognome() {
 	   return cognome;
 	 }
    
    public Date getData() {
 	   return dataNasc;
 	 }
    
    public char getSesso() {
 	   return sesso;
 	 }
    
    public String getCitta() {
 	   return citta;
 	 }
    
    public String getProvincia() {
 	   return provincia;
 	 }
    
    public String getCAP() {
 	   return cap;
 	 }
    
    public String getNazione() {
 	   return nazione;
 	 }
    
    public String getStatoCiv() {
 	   return statoCivile;
 	 }
    
    public String getIstruzione() {
 	   return istruzione;
 	 }
    
//metodi SET    
    public void setPwd(String password) {
    	   this.pwd = password;
    	 }
       
    public void setImage(Key keyImage)    { 
    	this.keyImg = keyImage; 
    	}
    
    public void setNome(String nome) {
  	   this.nome = nome;
  	 }
     
     public void setCognome(String cognome) {
  	   this.cognome = cognome;
  	 }
     
     public void setData(Date data) {
  	   this.dataNasc = data;
  	 }
     
     public void setSesso(char sex) {
  	   this.sesso = sex;
  	 }
     
     public void setCitta(String citta) {
  	   this.citta = citta;
  	 }
     
     public void setProvincia(String provincia) {
  	   this.provincia = provincia;
  	 }
     
     public void setCAP(String cap) {
  	   this.cap = cap;
  	 }
     
     public void setNazione(String nazione) {
  	   this.nazione = nazione;
  	 }
     
     public void setStatoCiv(String sc) {
  	   this.statoCivile = sc;
  	 }
     
     public void setIstruzione(String istr) {
  	   this.istruzione = istr;
  	 }
}




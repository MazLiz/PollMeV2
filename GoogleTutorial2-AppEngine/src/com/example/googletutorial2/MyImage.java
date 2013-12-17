package com.example.googletutorial2;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;



@Entity
public class MyImage {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    Blob image;

    public MyImage() { }
    public MyImage(String name, Blob image) {
        this.name = name; 
        this.image = image;
    }

    // JPA getters and setters and empty contructor
    // ...
    public Key getKey() {
    	   return key;
    	 }
    	 
    	 void clearKey() {
    	   key = null;
    	 }
    
    public String getName() {
 	   return name;
 	 }
    
    public void setName(String name) {
    	   this.name = name;
    	 }
    
    
    public Blob getImage()              { return image; }
    public void setImage(Blob image)    { this.image = image; }
}


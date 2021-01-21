package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class Artist_Request implements Serializable {
	
	private static final long serialVersionUID = -8850740904859933967L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int artistrequestid;
	
	public String artistname;
	public String image;
	
	public String description;

	public String userid;

	public int getArtistrequestid() {
		return artistrequestid;
	}

	public void setArtistrequestid(int artistrequestid) {
		this.artistrequestid = artistrequestid;
	}

	public String getUserid() {
		return userid;
	}

	public String getArtistname() {
		return artistname;
	}

	public void setArtistname(String artistname) {
		this.artistname = artistname;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUserid(String userid) { this.userid = userid;}

	public String getUserId(){return userid;}
	

	
}

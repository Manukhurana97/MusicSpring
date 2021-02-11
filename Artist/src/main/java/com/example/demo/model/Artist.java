package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Artist implements Serializable {
	
	private static final long serialVersionUID = -8850740904859933967L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int artistid;
	
	public String artistname;
	@Column(unique = true)
	public String artistemail;
	public String image;
	
	public String description;
	public int monthlylistening;
		
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "artist")
	@JsonIgnore
    private List<Followers> following;


	public int getArtistid() {
		return artistid;
	}

	public String getArtistname() {
		return artistname;
	}

	public void setArtistname(String artistname) {
		this.artistname = artistname;
	}

	public String getArtistemail() {
		return artistemail;
	}

	public void setArtistemail(String artistemail) {
		this.artistemail = artistemail;
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

	public int getMonthlylistening() {
		return monthlylistening;
	}

	public void setMonthlylistening(int monthlylistening) {
		this.monthlylistening = monthlylistening;
	}

	public List<Followers> getFollowing() {
		return following;
	}

	public void setFollowing(List<Followers> following) {
		this.following = following;
	}
	
	

	
}

package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int userdetailid;
	
	@Column(columnDefinition = "boolean default false")
	public boolean isArtist;
	
	public int ArtistId;
	
	
	public String userCountry;
	
	@OneToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name="username", nullable = false)
	public Users users;
	
	

	public int getUserdetailid() {
		return userdetailid;
	}

	

	public boolean isArtist() {
		return isArtist;
	}

	public void setArtist(boolean isArtist) {
		this.isArtist = isArtist;
	}

	public int getArtistId() {
		return ArtistId;
	}

	public void setArtistId(int artistId) {
		ArtistId = artistId;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
	

}

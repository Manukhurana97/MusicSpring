package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PlaylistFollowers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public String username;

	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name="playlistid", nullable=false)
	public Playlist playlist;

	public int getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
}

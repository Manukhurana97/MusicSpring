package com.example.demo.Response;

import java.util.List;

import com.example.demo.Model.Playlist;
import com.example.demo.Model.PlaylistSongs;

public class PlaylistResponse {

	public int status;
	public String mgs;
	public List<Playlist> lstplst;
	public List<PlaylistSongs> lstsongs;


	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMgs() {
		return mgs;
	}
	public void setMgs(String mgs) {
		this.mgs = mgs;
	}

	public List<Playlist> getLstplst() {
		return lstplst;
	}

	public void setLstplst(List<Playlist> lstplst) {
		this.lstplst = lstplst;
	}

	public List<PlaylistSongs> getLstsongs() {
		return lstsongs;
	}

	public void setLstsongs(List<PlaylistSongs> lstsongs) {
		this.lstsongs = lstsongs;
	}
}

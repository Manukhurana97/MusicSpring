package com.example.demo.Response;

import com.example.demo.model.Artist;

import java.util.List;

public class ArtistResponse {
	

	public String message;
	public List<Artist> list;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public List<Artist> getList() {
		return list;
	}

	public void setList(List<Artist> list) {
		this.list = list;
	}
}

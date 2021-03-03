package com.example.demo.Response;

import com.example.demo.model.Artist;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;


@JsonInclude(value = Include.NON_NULL)
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

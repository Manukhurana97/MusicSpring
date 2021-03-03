package com.example.demo.Response;

import java.util.List;

import com.example.demo.model.Artist;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ArtistApiCallResponse {
	
	public List<Artist> lst_of_artist;


	public List<Artist> getLst_of_artist() {
		return lst_of_artist;
	}

	public void setLst_of_artist(List<Artist> lst_of_artist) {
		this.lst_of_artist = lst_of_artist;
	}
	
	

}

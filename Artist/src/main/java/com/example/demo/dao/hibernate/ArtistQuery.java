package com.example.demo.dao.hibernate;

import java.util.List;

import com.example.demo.model.Artist;

public interface ArtistQuery {

	public List<Artist> findAllArtistsWithCriteriaQuery(List<Artist> lst);
	
	public void deleteByFollower(int id);
}

package com.example.demo.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.model.Artist;

@Component
@EnableTransactionManagement
public interface ArtistDao extends JpaRepository<Artist, Integer>{

    public Artist findArtistByArtistname(String artistname);
    public Artist findArtistByArtistid(int artistid);
	public boolean existsDistinctByArtistemail(String email);


}

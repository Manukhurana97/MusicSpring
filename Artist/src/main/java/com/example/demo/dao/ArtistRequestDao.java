package com.example.demo.dao;

import com.example.demo.model.Artist;
import com.example.demo.model.Artist_Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@EnableTransactionManagement
public interface ArtistRequestDao extends JpaRepository<Artist_Request, Integer>{
    public Artist_Request findByArtistrequestid(int id);

}

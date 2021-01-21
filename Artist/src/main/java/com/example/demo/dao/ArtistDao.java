package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.model.Artist;

@Component
@EnableTransactionManagement
public interface ArtistDao extends JpaRepository<Artist, Integer>{

    public Artist findArtistByArtistname(String artistname);
    public Artist findArtistByArtistid(int artistid);


}

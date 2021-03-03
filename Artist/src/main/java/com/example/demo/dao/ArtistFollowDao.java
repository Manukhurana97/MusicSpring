package com.example.demo.dao;


import com.example.demo.model.Followers;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public interface ArtistFollowDao extends JpaRepository<Followers, Integer> {
    public Followers findByUsernameAndAndArtist_artistid(String username, int artistid);
    @Transactional
    void deleteByArtist_ArtistidAndAndUsername(int artistid, String username);
}

package com.example.music.Dao;

import com.example.music.Model.Music;
import com.example.music.Model.SongFollowers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@EnableTransactionManagement
public interface Followdao extends JpaRepository<SongFollowers, Integer> {
    public SongFollowers findByUsernameAndMusic(String username, Music music);
}

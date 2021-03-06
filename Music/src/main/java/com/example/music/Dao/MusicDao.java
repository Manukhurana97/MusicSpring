package com.example.music.Dao;

import com.example.music.Model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public interface MusicDao extends JpaRepository<Music, Integer> {
    public Music findMusicByMusicid(int musicid);
}

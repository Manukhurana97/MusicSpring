package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Playlist;
import com.example.demo.Model.PlaylistSongs;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@EnableTransactionManagement
public interface Playlistdao extends JpaRepository<Playlist, Integer> {
    Playlist findByPlaylistidAndPlaylistcreater(int playlistid, String user);
    void deleteByPlaylistidAndPlaylistcreater(int playlistid, String user);
    public List<Playlist> findAllByPlaylistcreater(String creater);

}

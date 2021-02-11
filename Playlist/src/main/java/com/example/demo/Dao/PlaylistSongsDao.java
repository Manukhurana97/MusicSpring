package com.example.demo.Dao;

import com.example.demo.Model.Playlist;
import com.example.demo.Model.PlaylistSongs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Component
@EnableTransactionManagement
public interface PlaylistSongsDao extends JpaRepository<PlaylistSongs, Integer> {
    void deleteByPlaylist_PlaylistidAndSongid(int playlistid, int songid);
    void deleteByPlaylistAndSongid(Playlist playlist, int songid);
    void deleteAllByPlaylist(Playlist playlist);
    PlaylistSongs findByPlaylistAndSongid(Playlist playlist, int id);
    List<PlaylistSongs> findPlaylistSongsByPlaylist_Playlistid(int id);
    
}

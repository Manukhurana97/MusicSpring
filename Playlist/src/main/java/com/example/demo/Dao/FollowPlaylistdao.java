package com.example.demo.Dao;

import com.example.demo.Model.Playlist;
import com.example.demo.Model.PlaylistFollowers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@EnableTransactionManagement
public interface FollowPlaylistdao extends JpaRepository<PlaylistFollowers, Integer> {
    public PlaylistFollowers findByUsernameAndPlaylist(String username , Playlist playlist);

}

	package com.example.demo.Model;

import javax.persistence.*;

@Entity
public class PlaylistSongs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public int songid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playlistid", nullable = false)
    public Playlist playlist;

    public int getId() {
        return id;
    }

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}

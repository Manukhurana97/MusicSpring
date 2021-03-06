package com.example.music.Model;

import javax.persistence.*;

@Entity
public class SongFollowers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int Followid;

    public String username;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "musicid", nullable = false)
    public Music music;

    public int getFollowid() {
        return Followid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}

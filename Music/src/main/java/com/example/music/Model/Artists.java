package com.example.music.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Artists implements Serializable {
    private static final long serialVersionUID = 2612578813518671670L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public int artistid;
    public String artistname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="musicid", nullable = false)
    private Music music;



    public int getArtistid() {
        return artistid;
    }

    public void setArtistid(int artistid) {
        this.artistid = artistid;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}

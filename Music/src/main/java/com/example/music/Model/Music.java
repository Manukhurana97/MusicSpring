package com.example.music.Model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Music implements Serializable {

    private static final long serialVersionUID = 6447416794596398975L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int musicid;
    public String songname;
    public String coverurl;
    @NotNull
    public String fileurl;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull()
    public Date releasedata;
    public String type;
    public String Language;
    public int Listencount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "music")
    public List<Artists> artists;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "music")
    public List<SongFollowers> followers;

    public int getMusicid() {
        return musicid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public Date getReleasedata() {
        return releasedata;
    }

    public void setReleasedata(Date releasedata) {
        this.releasedata = releasedata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public int getListencount() {
        return Listencount;
    }

    public void setListencount(int listencount) {
        Listencount = listencount;
    }

    public List<Artists> getArtists() {
        return artists;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }

    public List<SongFollowers> getFollowers() {
        return followers;
    }

    public void setFollowers(List<SongFollowers> followers) {
        this.followers = followers;
    }
}

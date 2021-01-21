package com.example.music.Model;

import java.util.Date;
import java.util.List;

public class IncommingMusicdata {
    public int Musicid;
    public String songname;
    public String coverurl;
    public String fileurl;
    public String releasedata;
    public String type;
    public String Language;
    public List<Artists> artists;



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

    public String getReleasedata() {
        return releasedata;
    }

    public void setReleasedata(String releasedata) {
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

    public List<Artists> getArtists() {
        return artists;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }






    public int getMusicid() {
        return Musicid;
    }

    public void setMusicid(int musicid) {
        Musicid = musicid;
    }
}

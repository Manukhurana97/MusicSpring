package com.example.demo.Controller;

import com.example.demo.Incommingdata.PlaylistIncommingdata;
import com.google.gson.Gson;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    void createPlaylist() throws Exception {
        PlaylistIncommingdata data = new PlaylistIncommingdata();
        data.setPlaylistname("pl");
        data.setPlaylistCoverUrl("a.jpg");
        data.setPlaylistdescription("abc");
        Gson gson = new Gson();
        String json = gson.toJson(data);

        mockMvc.perform(MockMvcRequestBuilders.post("/Playlist/CreatePlaylist")
                .header("Authorization",
                        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJhbHZAZ21haWwuY29tIiwiZXhwIjoxNjEwNjk5NjYxLCJpYXQiOjE2MTA2MTMyNjF9.a9oFlylL3oZCobgD5dNmTT1Ft-i5FSL_qDz22fCytSI")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'message':'email associated with different account'}"))
                .andExpect(status().is2xxSuccessful())
        .andReturn();
    }


    @Test
    void publicPlaylist() {

    }

    @Test
    void deletePlaylist() {

    }
}
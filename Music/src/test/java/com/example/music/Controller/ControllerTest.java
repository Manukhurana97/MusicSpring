package com.example.music.Controller;

import com.example.music.Model.Artists;
import com.example.music.Model.IncommingMusicdata;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    void addSongs() throws Exception {
        IncommingMusicdata data = new IncommingMusicdata();
        data.setSongname("abc");
        data.setLanguage("hindi");
        data.setCoverurl("abc.jpeg");
        data.setFileurl("abc.mp3");
        data.setReleasedata("10/10/10");
        data.setType("qqq");
        Gson gson = new Gson();
        String jsonform = gson.toJson(data);
        mockMvc.perform(MockMvcRequestBuilders.post("/AddSongs")
                .header("Authentication", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJtYW5udWtodXJhbmExMDM5N0BnbWFpbC5jb20iLCJleHAiOjE2MTA2MTE0MjMsImlhdCI6MTYxMDAwNjYyM30.dK7KW92R6924NaZ7Gmbx2b3tLOhGRxOdenwD9mQwlI8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonform)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'message':'Password change successfully'}"))
                .andExpect(status().isCreated())
                .andReturn();


    }

    @Test
    void likeSongs() {
    }

    @Test
    void checksongs() {
    }
}
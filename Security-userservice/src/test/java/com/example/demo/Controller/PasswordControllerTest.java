//package com.example.demo.Controller;
//
//import com.example.demo.model.RegisterForm;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class PasswordControllerTest {
//
//    @Autowired
//    private MockMvc mockmvc;
//
//    @Test
//    void changepassword() throws Exception {
//        RegisterForm form = new RegisterForm();
//        form.setPassword("Manu@121");
//        Gson gson = new Gson();
//        String jsonform = gson.toJson(form);
//        mockmvc.perform(MockMvcRequestBuilders.post("/password/change-password")
//                .header("Authentication", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJtYW5udWtodXJhbmExMDM5N0BnbWFpbC5jb20iLCJleHAiOjE2MTA2MTE0MjMsImlhdCI6MTYxMDAwNjYyM30.dK7KW92R6924NaZ7Gmbx2b3tLOhGRxOdenwD9mQwlI8")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonform)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{'message':'Password change successfully'}"))
//                .andExpect(status().isCreated())
//                .andReturn();
//    }
//
//
//    @Test
//    void forgotpassword() throws Exception {
//        RegisterForm form = new RegisterForm();
//        form.setUsername("mannukhurana10397@gmail.com");
//        Gson gson = new Gson();
//        String json = gson.toJson(form);
//        mockmvc.perform(MockMvcRequestBuilders.post("/password/forgot-password")
//            .header("Authentication", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJtYW5udWtodXJhbmExMDM5N0BnbWFpbC5jb20iLCJleHAiOjE2MTA2MTE0MjMsImlhdCI6MTYxMDAwNjYyM30.dK7KW92R6924NaZ7Gmbx2b3tLOhGRxOdenwD9mQwlI8")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{'message':'Mail send successfully'}"))
//                .andExpect(status().isUnprocessableEntity())
//        .andReturn();
//    }
//
//    @Test
//    void checkresettokenverifyExpire() throws Exception {
//
//        mockmvc.perform(MockMvcRequestBuilders.post("/password/reset-password/{resettoken}", "28305aaa-3dc8-4cc4-85e2-47b051a3eb27")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{'message':'Link is invalid or already used'}"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//    @Test
//    void checkresettokenverify() throws Exception {
//        mockmvc.perform(MockMvcRequestBuilders.post("/password/reset-password/{resettoken}", "76b960d7-9cf6-4bac-8b30-a9a8dc7b5373")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{'message':'token is valid'}"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//    @Test
//    void resetpasswordyconfirm() throws Exception {
//        RegisterForm form = new RegisterForm();
//        form.setPassword("Manu@123");
//        Gson gson = new Gson();
//        String json = gson.toJson(gson);
//        mockmvc.perform(MockMvcRequestBuilders.post("/password/reset-password-confirm/{resettoken}", "76b960d7-9cf6-4bac-8b30-a9a8dc7b5373")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{'message':'password change successful'}"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//}
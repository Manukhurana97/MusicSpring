package com.example.demo.Controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.LoginForm;
import com.example.demo.model.RegisterForm;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {
	
	
	@Autowired
	private MockMvc mockmvc;
	
	@Test
	void testRegister() throws Exception {
		RegisterForm form = new RegisterForm();
		form.setUsername("manu@gmail.com");
		form.setPassword("Manu@123");
		form.setRole("ROLE_ADMIN");
		Gson gson = new Gson();
		String jsonform = gson.toJson(form);
		mockmvc.perform(MockMvcRequestBuilders.post("/Account/Register")
				.header("Authentication", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJtYW5udWtodXJhbmExMDM5N0BnbWFpbC5jb20iLCJleHAiOjE2MTA2MTE0MjMsImlhdCI6MTYxMDAwNjYyM30.dK7KW92R6924NaZ7Gmbx2b3tLOhGRxOdenwD9mQwlI8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonform)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{'message':'email associated with different account'}"))
		.andExpect(status().is4xxClientError())
		.andReturn();
				
	}

	@Test
	void testRegisterPasswordFormat() throws Exception {
		RegisterForm form = new RegisterForm();
		form.setUsername("abc@gmail.com");
		form.setPassword("Manu123");
		form.setRole("ROLE_ADMIN");
		Gson gson = new Gson();
		String jsonform = gson.toJson(form);

		mockmvc.perform(MockMvcRequestBuilders.post("/Account/Register")
				.header("Authentication", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJtYW5udWtodXJhbmExMDM5N0BnbWFpbC5jb20iLCJleHAiOjE2MTA2MTE0MjMsImlhdCI6MTYxMDAwNjYyM30.dK7KW92R6924NaZ7Gmbx2b3tLOhGRxOdenwD9mQwlI8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonform)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{'message':'password format is invalid'}"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}


	@Test
	void testLogin() throws Exception {
		LoginForm form = new LoginForm();
		form.setUsername("mannukhurana@gmail.com");
		form.setPassword("Alpha123@");
		Gson gson = new Gson();
		String jsonform = gson.toJson(form);

		mockmvc.perform(MockMvcRequestBuilders.post("/Account/Login")
				.header("Authentication", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJtYW5udWtodXJhbmExMDM5N0BnbWFpbC5jb20iLCJleHAiOjE2MTA2MTE0MjMsImlhdCI6MTYxMDAwNjYyM30.dK7KW92R6924NaZ7Gmbx2b3tLOhGRxOdenwD9mQwlI8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonform)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful())
		.andReturn();
	}

	@Test
	void testLoginBadCredentail() throws Exception {
		LoginForm form = new LoginForm();
		form.setUsername("manu@gmail.com");
		form.setPassword("Manu123");
		Gson gson = new Gson();
		String jsonform = gson.toJson(form);

		mockmvc.perform(MockMvcRequestBuilders.post("/Account/Login")
				.header("Authentication", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJtYW5udWtodXJhbmExMDM5N0BnbWFpbC5jb20iLCJleHAiOjE2MTA2MTE0MjMsImlhdCI6MTYxMDAwNjYyM30.dK7KW92R6924NaZ7Gmbx2b3tLOhGRxOdenwD9mQwlI8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonform)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{'message':'Bad Credential'}"))
				.andExpect(status().is4xxClientError())
				.andReturn();
	}

	@Test
	void check() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("mannukhurana@gmail.com", "ROLE_ADMIN");
		mockmvc.perform(MockMvcRequestBuilders.post("/Account/CheckToken")
				.header("Authentication", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTRVNTX1NVQiIsIlNFU1NfVVNFUk5BTUUiOiJtYW5udWtodXJhbmExMDM5N0BnbWFpbC5jb20iLCJleHAiOjE2MTA2MTE0MjMsImlhdCI6MTYxMDAwNjYyM30.dK7KW92R6924NaZ7Gmbx2b3tLOhGRxOdenwD9mQwlI8")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(map.toString())
				.andReturn();

	}


}

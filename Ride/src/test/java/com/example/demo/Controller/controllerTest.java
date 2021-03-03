package com.example.demo.Controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class controllerTest {


	@Autowired
	private MockMvc mockmvc;

	@Test
	public void testGetallRides() throws Exception {
		mockmvc.perform(MockMvcRequestBuilders.get("/ride")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void testGetRidesbyId() throws Exception {
		mockmvc.perform(MockMvcRequestBuilders.get("/ride/31")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void testCreateride() throws Exception {
		String newrider = "{\"name\":\"Monorail\",\"description\":\"Sedate travelling ride.\",\"thrillFactor\":2,\"vomitFactor\":1}";
		mockmvc.perform(MockMvcRequestBuilders.post("/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newrider)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andReturn();
	}

}

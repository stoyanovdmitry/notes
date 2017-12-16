package com.notes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.config.DataConfiguration;
import com.notes.config.RestConfig;
import com.notes.config.WebApp;
import com.notes.entity.User;
import com.notes.service.UserService;
import org.ajbrown.namemachine.Name;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		RestConfig.class,
		WebApp.class,
		DataConfiguration.class
})
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserService userService;

	private int id;
	private String username;

	private User testUser;
	private String jsonTestUser;

	@Before
	public void setUp() {
		id = 1;
		username = "lindastaley";

		testUser = new User("test", "pass", "test@email.com");
		try {
			jsonTestUser = new ObjectMapper().writeValueAsString(testUser);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getUserById() throws Exception {
		mockMvc.perform(get("/users/" + id).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username").value(username));
	}

	@Test
	public void getUserById404() throws Exception {
		mockMvc.perform(get("/users/69").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getUserByUsername() throws Exception {
		mockMvc.perform(get("/users/" + username).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id").value(id));
	}

	@Test
	public void getUserByUsername404() throws Exception {
		mockMvc.perform(get("/users/username").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

	@Test
	public void addUser() throws Exception {
		mockMvc.perform(post("/users")
				                .content(jsonTestUser)
				                .contentType(MediaType.APPLICATION_JSON_UTF8)
				                .accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
//		userService.delete(testUser);
	}
}

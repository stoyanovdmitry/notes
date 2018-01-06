package com.notes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.config.DataConfiguration;
import com.notes.config.RestConfig;
import com.notes.config.WebApp;
import com.notes.entity.Note;
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

import java.sql.SQLException;

import java.util.Arrays;

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
	private final String username = "username1";

	private User testUser;

	@Before
	public void setUp() {

		User user;

		try {
			user = userService.getByUsername(username);
			id = user.getId();
		} catch (Exception ignore) {

			user = new User(username, "pass", "mail@mail.me");
			Note note1 = new Note("text1", user);
			Note note2 = new Note("text2", user);
			user.setNotes(Arrays.asList(note1, note2));

			userService.save(user);

			user = userService.getByUsername(username);
			id = user.getId();
		}

		testUser = new User("test", "pass", "test@email.com");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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

		String jsonUser = objectToJson(testUser);

		mockMvc.perform(post("/users")
				                .content(jsonUser)
				                .contentType(MediaType.APPLICATION_JSON_UTF8)
				                .accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void updateUser() throws Exception {

		String name = testUser.getUsername();

		User user = userService.getByUsername(name);
		user.setEmail("updated@mail.com");

		String jsonUser = objectToJson(user);
		testUser = user;

		mockMvc.perform(put("/users/" + name)
				                .content(jsonUser)
				                .contentType(MediaType.APPLICATION_JSON_UTF8)
				                .accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteUser() throws Exception {

		String name = testUser.getUsername();

		mockMvc.perform(delete("/users/" + name)
				                .contentType(MediaType.APPLICATION_JSON_UTF8)
				                .accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	public String objectToJson(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}

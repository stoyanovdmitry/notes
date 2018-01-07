package com.notes;

import com.notes.config.DataConfiguration;
import com.notes.config.RestConfig;
import com.notes.config.TestConfig;
import com.notes.config.WebApp;
import com.notes.entity.User;
import com.notes.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		RestConfig.class,
		WebApp.class,
		TestConfig.class,
		DataConfiguration.class
})
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mvc;

	@Autowired
	private UserService userServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {

//		Mockito.reset(userServiceMock);

		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
							 .build();
	}

	@Test
	public void getUserIsOk() throws Exception {

		User user = new User("user1", "pass", "email");
		user.setId(1);

		when(userServiceMock.getByUsername("user1")).thenReturn(user);

		mvc.perform(get("/users/user1").accept(MediaType.APPLICATION_JSON_UTF8))
		   .andExpect(jsonPath("$.id").value(1))
		   .andExpect(jsonPath("$.username").value("user1"))
		   .andExpect(status().isOk());

		verify(userServiceMock, times(1)).getByUsername("user1");
	}
}

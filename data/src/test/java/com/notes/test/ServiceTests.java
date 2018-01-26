package com.notes.test;

import com.notes.config.DataConfiguration;
import com.notes.config.TestDataConfiguration;
import com.notes.entity.Note;
import com.notes.entity.User;
import com.notes.service.NoteService;
import com.notes.service.UserService;
import com.notes.util.TestDataUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		DataConfiguration.class,
		TestDataConfiguration.class,
})
public class ServiceTests {

	@Autowired
	private UserService userService;

	@Autowired
	private NoteService noteService;

	@Test
	public void saveTest() {

		User user = TestDataUtil.createRandomUser(2);
		userService.save(user);

		User returnedUser = userService.getByUsername(user.getUsername());

		Assert.assertEquals(user, returnedUser);

		Note note = new Note("text", user);
		noteService.save(note);

		Note returnedNote = noteService.getAllByUsername(user.getUsername()).get(2);

		Assert.assertEquals(note, returnedNote);
	}

	@Test
	public void saveTestWithoutNotes() {

		User user = TestDataUtil.createRandomUser();
		userService.save(user);

		User returnedUser = userService.getByUsername(user.getUsername());

		Assert.assertEquals(user, returnedUser);
	}

	@Test(expected = PersistenceException.class)
	public void saveTestNonUnique() {

		User user = TestDataUtil.createRandomUser();
		userService.save(user);

		userService.save(user);
	}

	@Test
	public void updateTest() {

		User user = TestDataUtil.createRandomUser(2);
		userService.save(user);

		List<Note> notes = user.getNotes();
		Note note0 = notes.get(0);
		note0.setText("text");

		user.setEmail("updated@mail.com");

		userService.update(user);
		noteService.update(note0);

		User returnedUser = userService
				.getByUsername(user.getUsername());
		Note returnedNote = noteService
				.getAllByUsername(user.getUsername())
				.get(0);

		Assert.assertEquals(user, returnedUser);
		Assert.assertEquals(note0, returnedNote);
	}

	@Test(expected = NoResultException.class)
	public void deleteTest() {

		User user = TestDataUtil.createRandomUser(2);
		userService.save(user);

		userService.delete(user);

		Assert.assertTrue(noteService
								  .getAllByUsername(user.getUsername())
								  .isEmpty());

		userService.getByUsername(user.getUsername());
	}
}

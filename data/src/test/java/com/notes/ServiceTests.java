package com.notes;

import com.notes.config.DataConfiguration;
import com.notes.entity.Note;
import com.notes.entity.User;
import com.notes.service.UserService;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfiguration.class)
public class ServiceTests {

	@Autowired
	private UserService userService;

	private static NameGenerator generator;

	@BeforeClass
	public static void preTesting() {
		generator = new NameGenerator();
	}

	@Ignore
	@Test
	public void userAddTest() {

		User user = getRandomUser();

		List<Note> notes = new ArrayList<>();
		notes.add(new Note("firstNote of " + user.getUsername(), user));
		notes.add(new Note("secondNote of " + user.getUsername(), user));

		user.setNotes(notes);
		userService.save(user);
	}

	private User getRandomUser() {
		Name name = generator.generateName();
		String stringName = name.toString();
		String email = stringName.replaceAll("\\s+", "").toLowerCase() + "@gmail.com";

		return new User(stringName, "pass", email);
	}

	@Test
	public void findUsers() {

		User user1 = userService.get(2);
		User user2 = userService.getByUsername(user1.getUsername());

		List<User> users = userService.getAll();

		Assert.assertEquals(user1, user2);
		Assert.assertEquals(user1, users.get(1));
	}

	@Test
	public void updateDeleteTest() {

		User user1 = getRandomUser();
		userService.save(user1);

		user1.setPassword("new");
		userService.update(user1);

		User user2 = userService.getByUsername(user1.getUsername());
		Assert.assertEquals(user1, user2);

		userService.delete(user2);

		try {
			userService.getByUsername(user2.getUsername());
			Assert.assertTrue(false);
		} catch (NoResultException e) {
			Assert.assertTrue(true);
		}
	}

	@Ignore
	@Test
	public void testNameGenerator() {
		List<Name> names = new NameGenerator().generateNames(100);
		names.forEach(System.out::println);
	}

	@Ignore
	@Test
	public void addSomeUsers() {
		for (int i = 0; i < 10; i++) {
			userAddTest();
		}
	}
}

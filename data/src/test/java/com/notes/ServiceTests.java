package com.notes;

import com.notes.config.DataConfiguration;
import com.notes.entity.Note;
import com.notes.entity.User;
import com.notes.service.AbstractService;
import com.notes.service.ServiceInterface;
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
	private ServiceInterface<User> userService;

	private static NameGenerator generator;

	@BeforeClass
	public static void preTesting() {
		generator = new NameGenerator();
	}

	@Ignore
	@Test
	public void userAddTest() {

		Name name = generator.generateName();
		String stringName = name.toString();
		String email = stringName.replaceAll("\\s+", "").toLowerCase() + "@gmail.com";

		User user = new User(stringName, "pass", email);

		List<Note> notes = new ArrayList<>();
		notes.add(new Note("firstNote of " + stringName, user));
		notes.add(new Note("secondNote of " + stringName, user));

		user.setNotes(notes);
		userService.save(user);
	}

	@Test
	public void findUsers() {

		User user1 = userService.getById(2);
		User user2 = userService.getByName("Cabron");

		List<User> users = userService.getAll();

		Assert.assertEquals(user1, user2);
		Assert.assertEquals(user1, users.get(1));
	}

	@Test
	public void updateDeleteTest() {

		User user = new User("tester", "test", "post@m.c");
		userService.save(user);

		user.setPassword("cr");
		userService.update(user);
		User user1 = userService.getByName("tester");
		Assert.assertEquals(user, user1);

		userService.delete(user);

		try {
			user = userService.getByName("tester");
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

package com.notes.util;

import com.notes.entity.Note;
import com.notes.entity.User;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

	private static NameGenerator generator;

	static {
		generator = new NameGenerator();
	}

	public static User createRandomUser() {

		Name name = generator.generateName();
		String stringName = name.toString();
		String username = stringName.replaceAll("\\s+", "").toLowerCase();
		String email = username + "@mail.com";

		return new User(username, "pass", email);
	}

	public static User createRandomUser(int notesCount) {

		User user = createRandomUser();
		List<Note> notes = new ArrayList<>();

		for (int i = 0; i < notesCount; i++) {
			String text = String.format("%s's note, %d", user.getUsername(), i);
			notes.add(new Note(text, user));
		}

		user.setNotes(notes);
		return user;
	}

	public static List<User> getSomeUsers(int n) {

		List<User> users = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			users.add(createRandomUser());
		}

		return users;
	}
}

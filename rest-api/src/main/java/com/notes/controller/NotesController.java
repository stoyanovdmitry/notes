package com.notes.controller;

import com.notes.entity.Note;
import com.notes.entity.User;
import com.notes.exception.NoAccessException;
import com.notes.service.NoteService;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{username}/notes")
public class NotesController {

	private final UserService userService;
	private final NoteService noteService;

	@Autowired
	public NotesController(UserService userService, NoteService noteService) {
		this.userService = userService;
		this.noteService = noteService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addNote(@RequestBody Note note,
						@PathVariable String username) {

		User user = userService.getByUsername(username);
		note.setUser(user);
		noteService.save(note);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteNote(@PathVariable int id,
						   @PathVariable String username) {

		Note note = validateAndGetNote(id, username);
		noteService.delete(note);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateNote(@RequestBody Note note,
						   @PathVariable int id,
						   @PathVariable String username) {

		Note existNote = validateAndGetNote(id, username);

		note.setId(id);
		note.setUser(existNote.getUser());

		noteService.update(note);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Note> getAllByUsername(@PathVariable String username) {

		return noteService.getAllByUsername(username);
	}

	private Note validateAndGetNote(int id, String username) {

		Note note = noteService.get(id);
		String usernameFromNote = note.getUser().getUsername();

		if(!usernameFromNote.equals(username))
			throw new NoAccessException();

		return note;
	}
}

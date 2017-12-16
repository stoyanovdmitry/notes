package com.notes.controller;

import com.notes.entity.Note;
import com.notes.entity.User;
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
	public void addNote(@PathVariable String username,
	                    @RequestBody Note note) {
		User user = userService.getByUsername(username);
		note.setUser(user);
		noteService.save(note);
	}

	@RequestMapping(name = "/{id}", method = RequestMethod.DELETE)
	public void deleteNode(@PathVariable int id) {
		Note note = noteService.get(id);
		noteService.delete(note);
	}

	@RequestMapping(name = "/{id}", method = RequestMethod.PUT)
	public void updateNote(@RequestBody Note note) {
		noteService.update(note);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Note> getAllByUsername(@PathVariable String username) {

		validateUser(username);
		return noteService.getAllByUsername(username);
	}

	private void validateUser(String username) {

		User user = userService.getByUsername(username);
		if (user == null) throw new RuntimeException("user not found");
	}
}

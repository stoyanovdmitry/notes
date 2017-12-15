package com.notes.service;

import com.notes.dao.NoteDao;
import com.notes.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

	private final NoteDao dao;

	@Autowired
	public NoteService(NoteDao noteDao) {
		this.dao = noteDao;
	}

	public Note get(int id) {
		return dao.get(id);
	}

	public List<Note> getAll() {
		return dao.getAll();
	}

	public List<Note> getAllByUsername(String username) {
		return dao.getAllByUsername(username);
	}

	public void save(Note note) {
		dao.save(note);
	}

	public void delete(Note note) {
		dao.delete(note);
	}

	public void update(Note note) {
		dao.update(note);
	}
}

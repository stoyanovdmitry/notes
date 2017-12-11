package com.notes.service;

import com.notes.dao.Dao;
import com.notes.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoteService extends AbstractService<Note> {

	@Autowired
	public NoteService(Dao<Note> noteDao) {
		super(noteDao);
	}
}

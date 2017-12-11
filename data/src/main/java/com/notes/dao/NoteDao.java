package com.notes.dao;

import com.notes.entity.Note;
import org.hibernate.QueryException;

public class NoteDao extends AbstractDao<Note> {

	public NoteDao() {
		super.setClassType(Note.class);
	}

	@Override
	public Note findByName(String name) {
		throw new QueryException("note haven't name");
	}
}

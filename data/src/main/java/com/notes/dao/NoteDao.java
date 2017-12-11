package com.notes.dao;

import com.notes.entity.Note;
import org.hibernate.QueryException;
import org.springframework.stereotype.Repository;

@Repository
public class NoteDao extends AbstractDao<Note> {

	public NoteDao() {
		super.setClassType(Note.class);
	}

	@Override
	public Note findByName(String name) {
		throw new QueryException("Note has no name");
	}
}

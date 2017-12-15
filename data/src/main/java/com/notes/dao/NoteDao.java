package com.notes.dao;

import com.notes.entity.Note;

import java.util.List;

public interface NoteDao extends Dao<Note> {

	List<Note> getAllByUsername(String username);
}

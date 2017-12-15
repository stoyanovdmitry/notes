package com.notes.dao;

import com.notes.entity.Note;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NoteDaoImpl implements NoteDao {

	private final SessionFactory sessionFactory;

	@Autowired
	public NoteDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Note> getAllByUsername(String username) {
		return sessionFactory.getCurrentSession()
				.createQuery("from Note where User.username =: name", Note.class)
				.setParameter("name", username)
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Note get(int id) {
		return sessionFactory.getCurrentSession()
				.get(Note.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Note> getAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Note", Note.class)
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Note note) {
		sessionFactory.getCurrentSession()
				.save(note);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Note note) {
		sessionFactory.getCurrentSession()
				.delete(note);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Note note) {
		sessionFactory.getCurrentSession()
				.update(note);
	}
}

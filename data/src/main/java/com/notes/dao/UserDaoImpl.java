package com.notes.dao;

import com.notes.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

	private final SessionFactory sessionFactory;

	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User getByUsername(String username) {
		return sessionFactory.getCurrentSession()
				.createQuery("from User where username =: name", User.class)
				.setParameter("name", username)
				.getSingleResult();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User get(int id) {
		return sessionFactory.getCurrentSession()
				.get(User.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> getAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from User", User.class)
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(User user) {
		sessionFactory.getCurrentSession()
				.persist(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(User user) {
		sessionFactory.getCurrentSession()
				.delete(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(User user) {
		sessionFactory.getCurrentSession()
				.update(user);
	}
}

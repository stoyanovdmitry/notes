package com.notes.dao;

import com.notes.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao extends AbstractDao<User> {

	public UserDao() {
		super.setClassType(User.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User findByName(String name) {
		return getSessionFactory().getCurrentSession()
				.createQuery("from User where username = :name", User.class)
				.setParameter("name", name)
				.getSingleResult();
	}
}

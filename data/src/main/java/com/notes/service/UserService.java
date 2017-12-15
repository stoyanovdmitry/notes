package com.notes.service;

import com.notes.dao.UserDao;
import com.notes.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	private final UserDao dao;

	@Autowired
	public UserService(UserDao userDao) {
		this.dao = userDao;
	}

	public User get(int id) {
		return dao.get(id);
	}

	public User getByUsername(String username) {
		return dao.getByUsername(username);
	}

	public List<User> getAll() {
		return dao.getAll();
	}

	public void save(User user) {
		dao.save(user);
	}

	public void delete(User user) {
		dao.delete(user);
	}

	public void update(User user) {
		dao.update(user);
	}
}

package com.notes.service;

import com.notes.dao.Dao;
import com.notes.dao.UserDao;
import com.notes.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	private UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getById(int id) {
		return userDao.findById(id);
	}

	public User getByName(String name) {
		return userDao.findByName(name);
	}

	public List<User> getAll() {
		return userDao.getAll();
	}

	public boolean save(User user) {
		try {
			userDao.save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(User user) {
		try {
			userDao.update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(User user) {
		try {
			userDao.delete(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

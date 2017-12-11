package com.notes.service;

import com.notes.dao.Dao;
import com.notes.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserService extends AbstractService<User> {

	@Autowired
	public UserService(Dao<User> userDao) {
		super(userDao);
	}
}

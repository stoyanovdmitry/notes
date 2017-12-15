package com.notes.dao;

import com.notes.entity.User;

public interface UserDao extends Dao<User> {

	User getByUsername(String username);
}

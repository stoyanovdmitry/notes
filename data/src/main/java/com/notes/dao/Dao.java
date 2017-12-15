package com.notes.dao;

import java.util.List;

interface Dao<T> {

	T get(int id);
	List<T> getAll();

	void save(T entity);
	void delete(T entity);
	void update(T entity);
}

package com.notes.dao;

import java.util.List;

public interface Dao<T> {

	T findById(int id);

	T findByName(String name);

	void save(T entity);

	void delete(T entity);

	void update(T entity);

	List<T> getAll();
}

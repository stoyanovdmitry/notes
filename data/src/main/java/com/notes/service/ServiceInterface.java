package com.notes.service;

import com.notes.entity.User;

import java.util.List;

public interface ServiceInterface<T> {

	T getById(int id);
	T getByName(String name);
	List<T> getAll();
	boolean save(T entity);
	boolean update(T entity);
	boolean delete(T entity);
}

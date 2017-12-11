package com.notes.service;

import com.notes.dao.Dao;
import com.notes.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractService<T extends Serializable> implements ServiceInterface<T> {

	private Dao<T> dao;

	public AbstractService(Dao<T> dao) {
		this.dao = dao;
	}

	public T getById(int id) {
		return dao.findById(id);
	}

	public T getByName(String name) {
		return dao.findByName(name);
	}

	public List<T> getAll() {
		return dao.getAll();
	}

	public boolean save(T entity) {
		try {
			dao.save(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(T entity) {
		try {
			dao.update(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(T entity) {
		try {
			dao.delete(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

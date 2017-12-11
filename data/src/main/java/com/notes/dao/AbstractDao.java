package com.notes.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
public abstract class AbstractDao<T extends Serializable> {

	private Class<T> classType;

	private SessionFactory sessionFactory;

	@Transactional(propagation = Propagation.REQUIRED)
	public T findById(int id) {
		return sessionFactory.getCurrentSession()
				.get(classType, id);
	}

	public abstract T findByName(String name);

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(T entity) {
		sessionFactory.getCurrentSession()
				.persist(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T entity) {
		sessionFactory.getCurrentSession()
				.delete(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T entity) {
		sessionFactory.getCurrentSession()
				.merge(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<T> getAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from " + classType.getName(), classType)
				.getResultList();
	}

	public Class<T> getClassType() {
		return classType;
	}

	public void setClassType(Class<T> classType) {
		this.classType = classType;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

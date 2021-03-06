package com.buschmais.jqassistant.test.javaee6.backend.user.model.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.buschmais.jqassistant.test.javaee6.backend.user.model.api.UserDAO;
import com.buschmais.jqassistant.test.javaee6.backend.user.model.api.model.User;

/**
 * Created with IntelliJ IDEA. User: dirk.mahler Date: 24.06.13 Time: 14:09 To
 * change this template use File | Settings | File Templates.
 */
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(User user) {
		entityManager.persist(user);
	}

	@Override
	public User update(User user) {
		return entityManager.merge(user);
	}

	@Override
	public void delete(User user) {
		entityManager.remove(user);
	}

	@Override
	public List<User> findAll() {
		return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
	}
}

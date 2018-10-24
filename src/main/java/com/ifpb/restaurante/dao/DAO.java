package com.ifpb.restaurante.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;


public abstract class DAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3751820538534091526L;
	
	@Inject
	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
}

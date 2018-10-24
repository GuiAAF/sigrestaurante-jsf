package com.ifpb.restaurante.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.filters.UserFilter;

public class UserDAO extends DAO {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7689865039758102412L;

	public void save(User obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		try {
			em.persist(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar salvar o usuário.", pe);
		}
	}

	public User update(User obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		User resultado = obj;
		try {
			resultado = em.merge(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar atualizar o usuário.", pe);
		}
		return resultado;
	}

	public void delete(User obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		try {
			obj = em.find(User.class, obj.getId());
			em.remove(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar remover o usuário.", pe);
		}
	}

	public User getByID(int objId) throws PersistenciaException {
		EntityManager em = getEntityManager();
		User resultado = null;
		try {
			resultado = em.find(User.class, objId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar recuperar o usuário com base no ID.", pe);
		}

		return resultado;
	}

	public List<User> getAll() throws PersistenciaException {
		return findBy(null);
	}

	public List<User> findBy(UserFilter filter) throws PersistenciaException {

		EntityManager em = getEntityManager();
		List<User> resultado;

		try {

			String jpql = "SELECT u FROM User u WHERE 1 = 1 ";

			// Primeiro nome
			if (notEmpty(filter.getNome())) {
				jpql += "AND u.nome LIKE :nome ";
			}
			
			if (notEmpty(filter.getEdital())) {
				jpql += "AND u.editalVigente = :edital ";
			}

			//Login
			if (notEmpty(filter.getLogin())) {
				jpql += "AND u.login LIKE :login ";
			}

			// grupo
			if (notEmpty(filter.getGrupo())) {
				jpql += "AND u.grupo = :grupo ";
			}

			TypedQuery<User> query = em.createQuery(jpql, User.class);
			
			if (notEmpty(filter.getEdital())) {
				query.setParameter("edital", filter.getEdital());
			}

			// Primeiro nome
			if (notEmpty(filter.getNome())) {
				query.setParameter("nome", "%" + filter.getNome());
			}

			// login
			if (notEmpty(filter.getLogin())) {
				query.setParameter("login", "%" + filter.getLogin());
			}

			//grupo
			if (notEmpty(filter.getGrupo())) {
				query.setParameter("grupo", filter.getGrupo());
			}

			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar recuperar os usuários.", pe);
		}
		return resultado;
	}
	
	public User getByLogin(String login) {
		EntityManager em = getEntityManager();
		
		String jpql = "SELECT u FROM User u WHERE u.login = :login ";
		
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		
		query.setParameter("login", login);
		
		return query.getSingleResult();
	}

	public boolean existeUsuarioComLogin(User user) {
		if (user == null || !notEmpty(user.getLogin())) {
			return false;
		}

		// Usar estratégia de contabilizar quantos usuários existem com o dado login, e que não seja ele mesmo.
		// Existe algum usuário com o login caso a contagem seja diferente de zero.
		// Usar COUNT(*), já que cláusula EXISTS não pode ser usada no SELECT pela BNF do JPQL:
		// https://docs.oracle.com/html/E13946_01/ejb3_langref.html#ejb3_langref_bnf

		EntityManager em = getEntityManager();

		String jpql = "SELECT COUNT(*) FROM User u WHERE u.login = :login ";
		if (user.getId() != null) {
			jpql += "AND u != :user ";
		}

		TypedQuery<Long> query = em.createQuery(jpql, Long.class);

		query.setParameter("login", user.getLogin());
		if (user.getId() != null) {
			query.setParameter("user", user);
		}

		Long count = query.getSingleResult();
		return count > 0;

	}

	private boolean notEmpty(Object obj) {
		return obj != null;
	}

	private boolean notEmpty(String obj) {
		return obj != null && !obj.trim().isEmpty();
	}

}

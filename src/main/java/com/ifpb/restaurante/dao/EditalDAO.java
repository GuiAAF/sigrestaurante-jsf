package com.ifpb.restaurante.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.ifpb.restaurante.Entities.Edital;
import com.ifpb.restaurante.filters.EditalFilter;

public class EditalDAO extends DAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -695439050469573588L;
	
	public void save(Edital obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		try {
			em.persist(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar salvar o edital.", pe);
		}
	}

	public Edital update(Edital obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		Edital resultado = obj;
		try {
			resultado = em.merge(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar atualizar o edital.", pe);
		}
		return resultado;
	}

	public void delete(Edital obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		try {
			obj = em.find(Edital.class, obj.getId());
			em.remove(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar remover o edital.", pe);
		}
	}

	public Edital getByID(int objId) throws PersistenciaException {
		EntityManager em = getEntityManager();
		Edital resultado = null;
		try {
			resultado = em.find(Edital.class, objId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar recuperar o usuário com base no ID.", pe);
		}

		return resultado;
	}

	public List<Edital> getAll() throws PersistenciaException {
		return findBy(new EditalFilter());
	}

	public List<Edital> findBy(EditalFilter	 filter) throws PersistenciaException {

		EntityManager em = getEntityManager();
		List<Edital> resultado;

		try {
			
			String jpql = "SELECT e FROM Edital e WHERE 1 = 1 ";
			
			// numero
			if (notEmpty(filter.getNumero())) {
				jpql += "AND e.numero = :numero ";
			}

			// Data inicial
			if (notEmpty(filter.getDataIntervaloInicial())) {
				jpql += "AND e.dataInicio >= :dataIntervaloInicial ";
			}

			// Birthday begin
			if (notEmpty(filter.getDataIntervaloFinal())) {
				jpql += "AND e.dataTermino <= :dataIntervaloFinal ";
			}
			
			if (notEmpty(filter.getVigente())) {
				jpql += "AND e.vigente = :vigente ";
			}

			TypedQuery<Edital> query = em.createQuery(jpql, Edital.class);
			
			// First name
			if (notEmpty(filter.getNumero())) {
				query.setParameter("numero", "%" + filter.getNumero() + "%");
			}

			// Birthday begin
			if (notEmpty(filter.getDataIntervaloInicial())) {
				query.setParameter("dataIntervaloInicial", filter.getDataIntervaloInicial());
			}

			// Birthday end
			if (notEmpty(filter.getDataIntervaloFinal())) {
				query.setParameter("dataIntervaloFinal", filter.getDataIntervaloFinal());
			}
			if (notEmpty(filter.getVigente())) {
				query.setParameter("vigente", filter.getVigente());
			}
			
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar recuperar os editais.", pe);
		}
		return resultado;
	}

	public boolean existeEditalComNumero(Edital edital) {
		if (edital == null || !notEmpty(edital.getNumero())) {
			return false;
		}
		
		// Usar estratégia de contabilizar quantos editais existem com o dado numero, e que não seja ele mesmo.
		// Existe algum edital com o numero caso a contagem seja diferente de zero.
		// Usar COUNT(*), já que cláusula EXISTS não pode ser usada no SELECT pela BNF do JPQL:
		// https://docs.oracle.com/html/E13946_01/ejb3_langref.html#ejb3_langref_bnf
		
		EntityManager em = getEntityManager();

		String jpql = "SELECT COUNT(*) FROM Edital e WHERE e.numero = :numero ";
		if (edital.getId() != null) {
			jpql += "AND e != :edital ";
		}

		TypedQuery<Long> query = em.createQuery(jpql, Long.class);

		query.setParameter("numero", edital.getNumero());
		if (edital.getId() != null) {
			query.setParameter("edital", edital);
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

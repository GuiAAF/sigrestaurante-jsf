package com.ifpb.restaurante.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import com.ifpb.restaurante.Entities.Oferta;
import com.ifpb.restaurante.filters.OfertaFilter;

public class OfertaDAO extends DAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -695439050469573588L;
	
	public void save(Oferta obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		try {
			em.persist(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar salvar a oferta.", pe);
		}
	}

	public Oferta update(Oferta obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		Oferta resultado = obj;
		try {
			resultado = em.merge(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar atualizar a oferta.", pe);
		}
		return resultado;
	}

	public void delete(Oferta obj) throws PersistenciaException {
		EntityManager em = getEntityManager();
		try {
			obj = em.find(Oferta.class, obj.getId());
			em.remove(obj);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar remover a oferta.", pe);
		}
	}

	public Oferta getByID(int objId) throws PersistenciaException {
		EntityManager em = getEntityManager();
		Oferta resultado = null;
		try {
			resultado = em.find(Oferta.class, objId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar recuperar a oferta com base no ID.", pe);
		}

		return resultado;
	}

	public List<Oferta> getAll() throws PersistenciaException {
		return findBy(null);
	}

	public List<Oferta> findBy(OfertaFilter	 filter) throws PersistenciaException {

		EntityManager em = getEntityManager();
		List<Oferta> resultado;

		try {
			
			String jpql = "SELECT e FROM Oferta e WHERE 1 = 1 ";

			if (notEmpty(filter.getMatricula())) {
				jpql += "AND e.aluno.login = :matricula ";
			}
			
			if (notEmpty(filter.getDataDoDia())) {
				jpql += "AND e.dataOferta = :dataDoDia ";
			}
			
			if (notEmpty(filter.getDataInicial())) {
				jpql += "AND e.dataOferta > :dataInicial ";
			}
			
			if(notEmpty(filter.getSolicitada())) {
				jpql +="AND e.solicitada = :solicitada ";
			}
			
			if(notEmpty(filter.getOfertada())) {
				jpql +="AND e.ofertada = :ofertada ";
			}
			
			// Data inicial
			if (notEmpty(filter.getDataIntervaloInicial())) {
				jpql += "AND e.dataOferta >= :dataIntervaloInicial ";
			}

			// Birthday begin
			if (notEmpty(filter.getDataIntervaloFinal())) {
				jpql += "AND e.dataOferta <= :dataIntervaloFinal ";
			}
			
			if (notEmpty(filter.getAluno())) {
				jpql += "AND e.aluno = :aluno ";
			}
			
			jpql += "ORDER BY e.dataOferta";
			
			TypedQuery<Oferta> query = em.createQuery(jpql, Oferta.class);

			if (notEmpty(filter.getMatricula())) {
				query.setParameter("matricula", filter.getMatricula());
			}
			
			if (notEmpty(filter.getDataDoDia())) {
				query.setParameter("dataDoDia", filter.getDataDoDia());
			}
			
			if (notEmpty(filter.getDataInicial())) {
				query.setParameter("dataInicial", filter.getDataInicial());
			}
			
			if(notEmpty(filter.getSolicitada())) {
				query.setParameter("solicitada", filter.getSolicitada());
			}
			
			if(notEmpty(filter.getOfertada())) {
				query.setParameter("ofertada", filter.getOfertada());
			}
			
			if (notEmpty(filter.getDataIntervaloInicial())) {
				query.setParameter("dataIntervaloInicial", filter.getDataIntervaloInicial());
			}

			if (notEmpty(filter.getDataIntervaloFinal())) {
				query.setParameter("dataIntervaloFinal", filter.getDataIntervaloFinal());
			}
			
			if (notEmpty(filter.getAluno())) {
				query.setParameter("aluno", filter.getAluno());
			}

			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar recuperar as ofertas.", pe);
		}
		return resultado;
	
	}
	
	public boolean jaExisteOfertaNestaData(Oferta oferta) {
		
		EntityManager em = getEntityManager();

		String jpql = "SELECT COUNT(*) FROM Oferta o WHERE o.dataOferta = :dataOferta ";
		if (oferta.getId() != null) {
			jpql += "AND o != :oferta ";
		}

		TypedQuery<Long> query = em.createQuery(jpql, Long.class);

		query.setParameter("dataOferta", oferta.getDataOferta());
		if (oferta.getId() != null) {
			query.setParameter("oferta", oferta);
		}
		
		Long count = query.getSingleResult();
		return count > 0;
		
	}
	
	private boolean notEmpty(Object obj) {
		return obj != null;
	}

}

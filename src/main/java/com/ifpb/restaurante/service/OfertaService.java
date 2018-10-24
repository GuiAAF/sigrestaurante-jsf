package com.ifpb.restaurante.service;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.ifpb.restaurante.Entities.Edital;
import com.ifpb.restaurante.Entities.Oferta;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.dao.EditalDAO;
import com.ifpb.restaurante.dao.OfertaDAO;
import com.ifpb.restaurante.dao.PersistenciaException;
import com.ifpb.restaurante.dao.UserDAO;
import com.ifpb.restaurante.filters.EditalFilter;
import com.ifpb.restaurante.filters.OfertaFilter;
import com.ifpb.restaurante.util.TransacionalCdi;

public class OfertaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7803325791425670859L;

	@Inject
	private OfertaDAO ofertaDAO;

	@Inject
	private EditalDAO editalDAO;
	
	@Inject
	private UserDAO userDAO;


	@TransacionalCdi
	public void save(Oferta oferta) throws ServiceDacaException  {

		try {
			existeOfertaNesteDia(oferta);
			validarData(oferta);
			submeterOfertaParaAlunos(oferta);
		} catch (ServiceDacaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	@TransacionalCdi
	public void update(Oferta oferta) throws ServiceDacaException {

		try {

			validarData(oferta);
			ofertaDAO.update(oferta);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	@TransacionalCdi
	public void delete(Oferta oferta) throws ServiceDacaException {
		try {
			ofertaDAO.delete(oferta);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	public Oferta getByID(int id) throws ServiceDacaException {
		try {
			return ofertaDAO.getByID(id);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	public List<Oferta> getAll() throws ServiceDacaException {
		try {
			return ofertaDAO.getAll();
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	public List<Oferta> findBy(OfertaFilter filter) throws ServiceDacaException {
		try {
			return ofertaDAO.findBy(filter);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	private void validarData(Oferta oferta) throws ServiceDacaException {
		Date dataHoje = new Date();
		if(oferta.getDataOferta().before(dataHoje)){
			throw new ServiceDacaException("Não pode ser criada uma oferta anterior a data de hoje!!");
		}
	}
	

	public void existeOfertaNesteDia(Oferta oferta) throws ServiceDacaException {
		boolean jahExiste = ofertaDAO.jaExisteOfertaNestaData(oferta);
		if (jahExiste) {
			throw new ServiceDacaException("Já existe oferta nesta data: "); 
		}
	}

	private void submeterOfertaParaAlunos(Oferta oferta) throws ServiceDacaException {	
		EditalFilter filter = new EditalFilter();
		filter.setVigente(true);
		try {
			List<Edital> editaisValidos= editalDAO.findBy(filter);
			if(!editaisValidos.isEmpty()) {
				for (Edital e: editaisValidos) {
					for (User u : e.getAlunos()) {
						Oferta nova = new Oferta();
						nova.setDataOferta(oferta.getDataOferta());;
						nova.setOfertada(oferta.isOfertada());;
						nova.setMsgExplicacao(oferta.getMsgExplicacao());;
						nova.setEdital(e);
						nova.setAluno(u);
						nova.setAlmocoConcretizado(false);
						nova.setJantaConcretizada(false);
						nova.setSolicitada(false);
						ofertaDAO.save(nova);
					}

				}
			}else {
				throw new ServiceDacaException("Não há editais cadastrados para que as submissões sejam concluídas!! "); 
			}
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}
	
	private Integer numRefeicoes(Oferta oferta){
		Integer cont = 0;
		if(oferta.getAlmoco() && oferta.getJanta()) {
			cont =+ 2;
		}else if(oferta.getAlmoco() || oferta.getJanta()) {
			cont =+ 1;
		}
		
		return cont;
	}
	
	public void reduzirCredito(Oferta oferta)throws PersistenciaException {
		Integer cont = numRefeicoes(oferta);
		User us = oferta.getAluno();
	
		if(cont > us.getCreditos()) {
			us.setCreditos(0);
			throw new PersistenciaException("Limite de refeições mensais atingido !!");
		}else {
			us.setCreditos(us.getCreditos()-cont);
		}
		
		userDAO.update(us);
	}

}

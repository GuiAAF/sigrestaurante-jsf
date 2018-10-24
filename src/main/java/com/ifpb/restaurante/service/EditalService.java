package com.ifpb.restaurante.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import com.ifpb.restaurante.Entities.Edital;
import com.ifpb.restaurante.dao.EditalDAO;
import com.ifpb.restaurante.dao.PersistenciaException;
import com.ifpb.restaurante.filters.EditalFilter;
import com.ifpb.restaurante.util.TransacionalCdi;

public class EditalService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1764694201125528654L;


	@Inject
	private EditalDAO editalDAO;

	@TransacionalCdi
	public void save(Edital edital) throws ServiceDacaException {
		try {
			edital.setVigente(setarVigencia(edital));
			validarNumero(edital);
			validarData(edital);
			editalDAO.save(edital);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	@TransacionalCdi
	public void update(Edital edital) throws ServiceDacaException {

		try {
			// Verificar se login já existe
			edital.setVigente(setarVigencia(edital));
			validarNumero(edital);
			validarData(edital);
			editalDAO.update(edital);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	@TransacionalCdi
	public void delete(Edital edital) throws ServiceDacaException {

		try {
			editalDAO.delete(edital);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	public Edital getByID(int editalId) throws ServiceDacaException {
		try {
			return this.editalDAO.getByID(editalId);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	public List<Edital> getAll() throws ServiceDacaException {
		try {
			return this.editalDAO.getAll();
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	private void validarNumero(Edital edital) throws ServiceDacaException {
		boolean jahExiste = editalDAO.existeEditalComNumero(edital);
		if (jahExiste) {
			throw new ServiceDacaException("Já existe edital com este nuemro: " + edital.getNumero()); 
		}
	}
	
	private void validarData(Edital edital) throws ServiceDacaException {
		Date dataHoje = new Date();
		if(edital.getDataTermino().before(dataHoje)) {
			throw new ServiceDacaException("Não pode ser criado edital com data de termino anteriores a de hoje!!");
		}
		if(edital.getDataTermino().before(edital.getDataInicio())) {
			throw new ServiceDacaException("Não pode ser criado edital com datas de termino menor que a data de início!!");
		}
		if(edital.getDataInicio().equals(edital.getDataTermino())) {
			throw new ServiceDacaException("Não pode ser criado edital com datas de termino e início iguais!!");
		}
	}
	
	private boolean setarVigencia(Edital edital) {
		Date dataHoje = new Date();
		if(edital.getDataTermino().before(dataHoje) || edital.getDataInicio().after(dataHoje)) {
			return false;
		}else {
			return true;
		}
	}

	public List<Edital> findBy(EditalFilter filter) throws ServiceDacaException {
		try {
			return editalDAO.findBy(filter);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}
}

package com.ifpb.restaurante.bean;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.dao.PersistenciaException;
import com.ifpb.restaurante.dao.UserDAO;

@Named
@RequestScoped
public class IndexBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2132897271787815405L;
	
	@Inject
	private UserDAO userDAO;
	
	private User userAtivo;

	@PostConstruct
	public void init() {
		userAtivo = userLogado();
	}
	
	public User userLogado() {

		return userDAO.getByLogin(this.getUserLogin());

	}
	
	public void verificarDataCredito() {
		Date suaData = new Date();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(suaData);
		int dia = calendario.get(Calendar.DAY_OF_MONTH);
		User usLog = this.userLogado();
		if(dia == 1) {
			usLog.setCreditos(20);
			try {
				userDAO.update(usLog);
			} catch (PersistenciaException e) {
				reportarMensagemDeErro(e.getMessage());
			}
		}
	}

	public User getUserAtivo() {
		return userAtivo;
	}

	public void setUserAtivo(User userAtivo) {
		this.userAtivo = userAtivo;
	}

}

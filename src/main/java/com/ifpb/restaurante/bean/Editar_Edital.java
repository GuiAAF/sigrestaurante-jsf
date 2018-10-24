package com.ifpb.restaurante.bean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import javax.faces.view.ViewScoped;

import com.ifpb.restaurante.Entities.Edital;
import com.ifpb.restaurante.service.EditalService;
import com.ifpb.restaurante.service.ServiceDacaException;


@Named
@ViewScoped
public class Editar_Edital extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7591271829371238865L;

	private Edital edital;

	@Inject
	private EditalService editalService;

	@PostConstruct
	public void init() {
		if(edital == null) {
			edital = new Edital();
		}
	}

	public String saveEdital() {
		try {
			editalService.update(edital);

		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Edital n° " + edital.getNumero() + ", atualizado!!");
		return "/paginas/protegido/admin/Lista_Editais.xhtm?faces-redirect=true";
	}


	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}


}

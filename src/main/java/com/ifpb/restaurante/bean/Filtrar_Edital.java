package com.ifpb.restaurante.bean;

import java.util.Collection;
import javax.annotation.PostConstruct;

import javax.inject.Inject;
import javax.inject.Named;

import javax.faces.view.ViewScoped;

import com.ifpb.restaurante.Entities.Edital;
import com.ifpb.restaurante.filters.EditalFilter;
import com.ifpb.restaurante.service.EditalService;
import com.ifpb.restaurante.service.ServiceDacaException;

@Named
@ViewScoped
public class Filtrar_Edital extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7975815893107412334L;

	Collection<Edital> editais;

	private EditalFilter editalFilter;
	
	@Inject
	EditalService editalService;


	@PostConstruct
	public void init() {
		limpar();
		filtrar();
	}

	public String filtrar() {
		try {
			validarDatar();
			editais = editalService.findBy(getEditalFilter());
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return null;
	}
	
	private void validarDatar() throws ServiceDacaException{
		if(getEditalFilter().getDataIntervaloFinal() != null && getEditalFilter().getDataIntervaloInicial() != null) {
			if(getEditalFilter().getDataIntervaloFinal().before(getEditalFilter().getDataIntervaloInicial())) {
				throw new ServiceDacaException("Data final do intervalo anterior a data inicial!!");
			}
		}
	}

	public String limpar() {
		editalFilter = new EditalFilter();
		return null;
	}
	
	public int quantidade() {
		return editais.size();
	}

	public String update(Edital edital) {
		try {
			editalService.update(edital);
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("O edital n° " + edital.getNumero() + " atualizado!!");
		return "/paginas/protegido/admin/Lista_Editais.xhtm?faces-redirect=true";
	}

	public EditalFilter getEditalFilter() {
		return editalFilter;
	}

	public void setEditalFilter(EditalFilter editalFilter) {
		this.editalFilter = editalFilter;
	}


	public Collection<Edital> getEditais() {
		return editais;
	}

	public void setEditais(Collection<Edital> editais) {
		this.editais = editais;
	}
}

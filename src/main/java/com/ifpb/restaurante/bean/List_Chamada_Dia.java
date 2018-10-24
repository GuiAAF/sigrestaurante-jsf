package com.ifpb.restaurante.bean;

import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.ifpb.restaurante.Entities.Oferta;
import com.ifpb.restaurante.filters.OfertaFilter;
import com.ifpb.restaurante.service.OfertaService;
import com.ifpb.restaurante.service.ServiceDacaException;

@Named
@ViewScoped
public class List_Chamada_Dia extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7975815893107412334L;

	private Collection<Oferta> ofertas;

	private OfertaFilter ofertaFilter;

	@Inject
	private OfertaService ofertaService;

	@PostConstruct
	public void init() {
		limpar();
		filtrar();
	}

	public String filtrar() {
		try {
			ofertaFilter.setSolicitada(true);
			ofertaFilter.setDataDoDia(new Date());
			ofertas = ofertaService.findBy(getOfertaFilter());
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return null;
	}

	public String limpar() {
		ofertaFilter = new OfertaFilter();
		return null;
	}

	public int quantidade() {
		return ofertas.size();
	}

	public String update(Oferta oferta) {
		try {
			ofertaService.update(oferta);
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Refeição confirmada!!");
		return "/paginas/protegido/funcionario/Lista_Chamada_Dia.xhtml?faces-redirect=true";
	}

	public Collection<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(Collection<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public OfertaFilter getOfertaFilter() {
		return ofertaFilter;
	}

	public void setOfertaFilter(OfertaFilter ofertaFilter) {
		this.ofertaFilter = ofertaFilter;
	}
	
}

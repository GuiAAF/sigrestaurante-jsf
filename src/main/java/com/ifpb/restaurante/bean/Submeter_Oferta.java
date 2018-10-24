package com.ifpb.restaurante.bean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.ifpb.restaurante.Entities.Oferta;
import com.ifpb.restaurante.service.OfertaService;
import com.ifpb.restaurante.service.ServiceDacaException;


@Named
@ViewScoped
public class Submeter_Oferta extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7591271829371238865L;

	private Oferta oferta;

	@Inject
	private OfertaService ofertaService;

	@PostConstruct
	public void init() {
		if(oferta == null) {
			oferta = new Oferta();
		}
	}

	public String submitOferta() {
		try {
			ofertaService.save(oferta);

		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Oferta submetida para alunos!!");
		oferta = new Oferta();
		return "/paginas/protegido/admin/Lista_Ofertas.xhtml?faces-redirect=true";
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}
	
	


}

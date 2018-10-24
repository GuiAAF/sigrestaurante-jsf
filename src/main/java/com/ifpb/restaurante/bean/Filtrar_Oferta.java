package com.ifpb.restaurante.bean;

import java.util.Collection;
import javax.annotation.PostConstruct;

import javax.inject.Inject;
import javax.inject.Named;

import javax.faces.view.ViewScoped;

import com.ifpb.restaurante.Entities.Grupo;
import com.ifpb.restaurante.Entities.Oferta;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.dao.PersistenciaException;
import com.ifpb.restaurante.filters.OfertaFilter;
import com.ifpb.restaurante.filters.UserFilter;
import com.ifpb.restaurante.service.OfertaService;
import com.ifpb.restaurante.service.ServiceDacaException;
import com.ifpb.restaurante.service.UserService;

@Named
@ViewScoped
public class Filtrar_Oferta extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7975815893107412334L;

	private Collection<Oferta> ofertas;

	private OfertaFilter ofertaFilter;

	private String matriculaAluno;
	
	private Oferta oferta;

	@Inject
	private UserService userService;

	@Inject
	private OfertaService ofertaService;


	@PostConstruct
	public void init() {
		limpar();
		filtrar();
	}

	public String filtrar() {
		try {

			if(matriculaAluno != null) {
				procurarUsuario();
			}
			ofertas = ofertaService.findBy(getOfertaFilter());
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return null;
	}

	public void procurarUsuario() throws ServiceDacaException {
		if(!matriculaAluno.isEmpty()) {
			User us = new User();
			UserFilter filter = new UserFilter();
			filter.setLogin(matriculaAluno);
			filter.setGrupo(Grupo.ALUNO);
			us = userService.alunoLogado(matriculaAluno);
			if(us != null) {
				ofertaFilter.setAluno(us);
			}
		}
	}

	public String limpar() {
		ofertaFilter = new OfertaFilter();
		return null;
	}

	public int quantidade() {
		return ofertas.size();
	}

	public String update() {
		try {
			if(oferta.getAlmoco() != null && oferta.getJanta()!= null) {
				if(oferta.getAlmoco() || oferta.getJanta() ) {
					oferta.setSolicitada(true);
					ofertaService.reduzirCredito(oferta);
				}else {
					oferta.setSolicitada(false);
				}
			}
			ofertaService.update(this.oferta);
		} catch (ServiceDacaException | PersistenciaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Oferta aualizada!!");
		return "/paginas/protegido/admin/Lista_Ofertas.xhtml?faces-redirect=true";
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

	public String getMatriculaAluno() {
		return matriculaAluno;
	}

	public void setMatriculaAluno(String matriculaAluno) {
		this.matriculaAluno = matriculaAluno;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

}

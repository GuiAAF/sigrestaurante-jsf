package com.ifpb.restaurante.bean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.ifpb.restaurante.Entities.Oferta;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.dao.PersistenciaException;
import com.ifpb.restaurante.filters.OfertaFilter;
import com.ifpb.restaurante.service.OfertaService;
import com.ifpb.restaurante.service.ServiceDacaException;
import com.ifpb.restaurante.service.UserService;

@Named
@ViewScoped
public class Lista_Oferta_Aluno extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7975815893107412334L;

	Collection<Oferta> ofertas;

	private OfertaFilter ofertaFilter;

	@Inject
	private OfertaService ofertaService;


	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		limpar();
		filtrar();
	}

	public String filtrar() {
		try {
			Date dataHoje = new Date();
			ofertaFilter.setDataInicial(dataHoje);
			ofertaFilter.setSolicitada(false);
			ofertaFilter.setAluno(this.alunoLogado());
			ofertas = ofertaService.findBy(this.getOfertaFilter());
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return null;
	}

	public String dataFormatada(Date data) {
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd-MM-yyyy");
		return formatoDesejado.format(data);
	}

	public User alunoLogado() {

		return userService.alunoLogado(this.getUserLogin());

	}
	

	public String limpar() {
		ofertaFilter = new OfertaFilter();
		return null;
	}

	public String update(Oferta oferta) {
		try {
			if(oferta.getAlmoco() != null && oferta.getJanta()!= null) {
				if(oferta.getAlmoco() || oferta.getJanta() ) {
					oferta.setSolicitada(true);
					ofertaService.reduzirCredito(oferta);
				}else {
					oferta.setSolicitada(false);
				}
			}
			ofertaService.update(oferta);
		} catch (ServiceDacaException | PersistenciaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Sua intenção do dia " + oferta.getDataOferta() + ", foi salva!!");
		return "/paginas/protegido/aluno/Index_Aluno.xhtml?faces-redirect=true";
	}


	public Collection<Oferta> getOfertas() {
		return ofertas;
	}

	public void setIntencoes(Collection<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public OfertaFilter getOfertaFilter() {
		return ofertaFilter;
	}

	public void setOfertaFilter(OfertaFilter ofertaFilter) {
		this.ofertaFilter = ofertaFilter;
	}
}

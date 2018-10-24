package com.ifpb.restaurante.Entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "Oferta")
public class Oferta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5660536599662066890L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	//a baixo dados repassados pela CAEST na hora de disponibilizar a oferta
	
	@Temporal(TemporalType.DATE)
	private Date dataOferta;
	
	//caso naquele dia ela for ou não ofertada pelo restaurante
	private boolean ofertada;
	
	//Caso a refeição não for ofertada, aqui vem a msg de explicacao.
	private String msgExplicacao;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private User aluno;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Edital edital;

	//A baixo dados respondidos pelo aluno que fara a solicitacao

	private Boolean solicitada;

	private Boolean almoco;

	private Boolean almocoConcretizado;

	private Boolean janta;

	private Boolean jantaConcretizada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataOferta() {
		return dataOferta;
	}

	public void setDataOferta(Date dataOferta) {
		this.dataOferta = dataOferta;
	}

	public boolean isOfertada() {
		return ofertada;
	}

	public void setOfertada(boolean ofertada) {
		this.ofertada = ofertada;
	}

	public String getMsgExplicacao() {
		return msgExplicacao;
	}

	public void setMsgExplicacao(String msgExplicacao) {
		this.msgExplicacao = msgExplicacao;
	}

	public User getAluno() {
		return aluno;
	}

	public void setAluno(User aluno) {
		this.aluno = aluno;
	}

	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}

	public Boolean getSolicitada() {
		return solicitada;
	}

	public void setSolicitada(Boolean solicitada) {
		this.solicitada = solicitada;
	}

	public Boolean getAlmoco() {
		return almoco;
	}

	public void setAlmoco(Boolean almoco) {
		this.almoco = almoco;
	}

	public Boolean getAlmocoConcretizado() {
		return almocoConcretizado;
	}

	public void setAlmocoConcretizado(Boolean almocoConcretizado) {
		this.almocoConcretizado = almocoConcretizado;
	}

	public Boolean getJanta() {
		return janta;
	}

	public void setJanta(Boolean janta) {
		this.janta = janta;
	}

	public Boolean getJantaConcretizada() {
		return jantaConcretizada;
	}

	public void setJantaConcretizada(Boolean jantaConcretizada) {
		this.jantaConcretizada = jantaConcretizada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataOferta == null) ? 0 : dataOferta.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oferta other = (Oferta) obj;
		if (dataOferta == null) {
			if (other.dataOferta != null)
				return false;
		} else if (!dataOferta.equals(other.dataOferta))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}

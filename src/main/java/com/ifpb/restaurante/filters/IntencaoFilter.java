package com.ifpb.restaurante.filters;

import java.util.Date;

import com.ifpb.restaurante.Entities.User;

public class IntencaoFilter {

	private boolean ofertada;

	private Date dataOferta;
	
	private Date dataInicio;

	private User aluno;

	private boolean solicitada;

	private boolean almoco;

	private boolean almocoConcretizado;

	private boolean janta;

	private boolean jantaConcretizada;

	public boolean isOfertada() {
		return ofertada;
	}
	

	public void setOfertada(boolean ofertada) {
		this.ofertada = ofertada;
	}

	
	
	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	public Date getDataOferta() {
		return dataOferta;
	}

	public void setDataOferta(Date dataOferta) {
		this.dataOferta = dataOferta;
	}

	public User getAluno() {
		return aluno;
	}

	public void setAluno(User aluno) {
		this.aluno = aluno;
	}

	public boolean isSolicitada() {
		return solicitada;
	}

	public void setSolicitada(boolean solicitada) {
		this.solicitada = solicitada;
	}

	public boolean isAlmoco() {
		return almoco;
	}

	public void setAlmoco(boolean almoco) {
		this.almoco = almoco;
	}

	public boolean isAlmocoConcretizado() {
		return almocoConcretizado;
	}

	public void setAlmocoConcretizado(boolean almocoConcretizado) {
		this.almocoConcretizado = almocoConcretizado;
	}

	public boolean isJanta() {
		return janta;
	}

	public void setJanta(boolean janta) {
		this.janta = janta;
	}

	public boolean isJantaConcretizada() {
		return jantaConcretizada;
	}

	public void setJantaConcretizada(boolean jantaConcretizada) {
		this.jantaConcretizada = jantaConcretizada;
	}
	
	

}

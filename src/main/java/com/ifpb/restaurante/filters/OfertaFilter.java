package com.ifpb.restaurante.filters;

import java.util.Date;

import com.ifpb.restaurante.Entities.User;

public class OfertaFilter {
	
	private Date dataInicial;

	private Date dataIntervaloInicial;

	private Date dataIntervaloFinal;
	
	private User aluno;
	
	private Boolean solicitada;
	
	private Boolean ofertada;
	
	private String matricula;
	
	private Date dataDoDia;
	

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Date getDataDoDia() {
		return dataDoDia;
	}

	public void setDataDoDia(Date dataDoDia) {
		this.dataDoDia = dataDoDia;
	}

	public Boolean getOfertada() {
		return ofertada;
	}

	public void setOfertada(Boolean ofertada) {
		this.ofertada = ofertada;
	}

	public Boolean getSolicitada() {
		return solicitada;
	}

	public void setSolicitada(Boolean solicitada) {
		this.solicitada = solicitada;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataIntervaloInicial() {
		return dataIntervaloInicial;
	}

	public void setDataIntervaloInicial(Date dataIntervaloInicial) {
		this.dataIntervaloInicial = dataIntervaloInicial;
	}

	public Date getDataIntervaloFinal() {
		return dataIntervaloFinal;
	}

	public void setDataIntervaloFinal(Date dataIntervaloFinal) {
		this.dataIntervaloFinal = dataIntervaloFinal;
	}

	public User getAluno() {
		return aluno;
	}

	public void setAluno(User aluno) {
		this.aluno = aluno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
		result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
		result = prime * result + ((dataIntervaloFinal == null) ? 0 : dataIntervaloFinal.hashCode());
		result = prime * result + ((dataIntervaloInicial == null) ? 0 : dataIntervaloInicial.hashCode());
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
		OfertaFilter other = (OfertaFilter) obj;
		if (aluno == null) {
			if (other.aluno != null)
				return false;
		} else if (!aluno.equals(other.aluno))
			return false;
		if (dataInicial == null) {
			if (other.dataInicial != null)
				return false;
		} else if (!dataInicial.equals(other.dataInicial))
			return false;
		if (dataIntervaloFinal == null) {
			if (other.dataIntervaloFinal != null)
				return false;
		} else if (!dataIntervaloFinal.equals(other.dataIntervaloFinal))
			return false;
		if (dataIntervaloInicial == null) {
			if (other.dataIntervaloInicial != null)
				return false;
		} else if (!dataIntervaloInicial.equals(other.dataIntervaloInicial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OfertaFilter [dataInicial=" + dataInicial + ", dataIntervaloInicial=" + dataIntervaloInicial
				+ ", dataIntervaloFinal=" + dataIntervaloFinal + ", aluno=" + aluno + "]";
	}

	

}

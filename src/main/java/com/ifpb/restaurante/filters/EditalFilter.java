package com.ifpb.restaurante.filters;

import java.util.Date;

public class EditalFilter {

	private Date dataIntervaloInicial;

	private Date dataIntervaloFinal;

	private Boolean vigente;

	private String numero;

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

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	

	
}

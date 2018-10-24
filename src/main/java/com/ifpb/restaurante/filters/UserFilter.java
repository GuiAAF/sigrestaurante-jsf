package com.ifpb.restaurante.filters;



import com.ifpb.restaurante.Entities.Edital;
import com.ifpb.restaurante.Entities.Grupo;

public class UserFilter {

	private String Nome;
	
	private String login;
	
	private Grupo grupo;
	
	private Edital edital;
	

	public UserFilter() {

	}


	public String getNome() {
		return Nome;
	}


	public void setNome(String nome) {
		Nome = nome;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public Grupo getGrupo() {
		return grupo;
	}


	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}


	public Edital getEdital() {
		return edital;
	}


	public void setEdital(Edital edital) {
		this.edital = edital;
	}
	
	
}

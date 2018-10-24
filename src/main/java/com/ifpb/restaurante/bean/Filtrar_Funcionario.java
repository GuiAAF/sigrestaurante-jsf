package com.ifpb.restaurante.bean;

import java.util.Collection;
import javax.annotation.PostConstruct;

import javax.inject.Inject;
import javax.inject.Named;

import javax.faces.view.ViewScoped;

import com.ifpb.restaurante.Entities.Grupo;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.filters.UserFilter;
import com.ifpb.restaurante.service.ServiceDacaException;
import com.ifpb.restaurante.service.UserService;

@Named
@ViewScoped
public class Filtrar_Funcionario extends AbstractBean{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 80317304625155634L;

	Collection<User> funcionarios;

	private UserFilter funcionarioFilter;
	
	@Inject
	UserService funcionarioService;


	@PostConstruct
	public void init() {
		limpar();
		filtrar();
	}

	public String filtrar() {
		try {
			funcionarioFilter.setGrupo(Grupo.FUNCIONARIO);
			funcionarios = funcionarioService.findBy(getFuncionarioFilter());
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return null;
	}

	public String limpar() {
		funcionarioFilter = new UserFilter();
		return null;
	}

	public String delete(User aluno) {
		try {
			funcionarioService.delete(aluno);
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		reportarMensagemDeSucesso("FuncionarioAluno deletado!!");
		return "/paginas/protegido/admin/Lista_Funcionarios.xhtm?faces-redirect=true";
	}

	public String update(User aluno) {
		try {
			funcionarioService.update(aluno, false);
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("O funcionario foi atualizado!");
		return "/paginas/protegido/admin/Lista_Funcionarios.xhtm?faces-redirect=true";
	}

	public Collection<User> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(Collection<User> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public UserFilter getFuncionarioFilter() {
		return funcionarioFilter;
	}

	public void setFuncionarioFilter(UserFilter funcionarioFilter) {
		this.funcionarioFilter = funcionarioFilter;
	}


}

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
public class Filtrar_Aluno extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7975815893107412334L;

	Collection<User> alunos;

	private UserFilter alunoFilter;
	
	@Inject
	UserService alunoService;


	@PostConstruct
	public void init() {
		limpar();
		filtrar();
	}

	public String filtrar() {
		try {
			alunoFilter.setGrupo(Grupo.ALUNO);
			alunos = alunoService.findBy(getAlunoFilter());
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return null;
	}

	public String limpar() {
		alunoFilter = new UserFilter();
		return null;
	}

	public String update(User aluno) {
		try {
			alunoService.update(aluno, false);
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("O aluno " + aluno.getNome() + "foi atualizado!");
		return "/paginas/protegido/admin/Lista_Alunos.xhtml?faces-redirect=true";
	}

	public UserFilter getAlunoFilter() {
		return alunoFilter;
	}

	public void setEditalFilter(UserFilter alunoFilter) {
		this.alunoFilter = alunoFilter;
	}


	public Collection<User> getAlunos() {
		return alunos;
	}

	public void setAlunos(Collection<User> alunos) {
		this.alunos = alunos;
	}
}

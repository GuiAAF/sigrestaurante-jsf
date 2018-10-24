package com.ifpb.restaurante.bean;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ifpb.restaurante.Entities.Edital;
import com.ifpb.restaurante.Entities.Grupo;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.service.EditalService;
import com.ifpb.restaurante.service.ServiceDacaException;
import com.ifpb.restaurante.service.UserService;


@Named
@ViewScoped
public class Salvar_Aluno extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1466747777612502825L;

	@Inject
	private UserService userService;
	
	@Inject
	private EditalService editalService;
	
	private List<Edital> editais;

	private User aluno;
	

	@PostConstruct
	public void init() {
		aluno = null;
		try {
			if(aluno == null){
				aluno = new User();
			}
			editais = editalService.getAll();
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}
	

	public String saveUser() {
		try {
			aluno.setGrupo(Grupo.ALUNO);
			aluno.setCreditos(20);
			aluno.setSenha(aluno.getLogin());
			userService.save(aluno);

		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Aluno(a) " + aluno.getNome() + " salvo(a)!");
		aluno = new User();
		return "/paginas/protegido/admin/Lista_Alunos.xhtml?faces-redirect=true";
	}

	public List<Edital> getEditais() {
		return editais;
	}

	public void setEditais(List<Edital> editais) {
		this.editais = editais;
	}

	public User getAluno() {
		return aluno;
	}

	public void setAluno(User aluno) {
		this.aluno = aluno;
	}
	
}

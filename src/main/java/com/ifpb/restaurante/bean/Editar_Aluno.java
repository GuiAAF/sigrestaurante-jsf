package com.ifpb.restaurante.bean;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.service.ServiceDacaException;
import com.ifpb.restaurante.service.UserService;


@Named
@ViewScoped
public class Editar_Aluno extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1466747777612502825L;

	@Inject
	private UserService userService;
	
	private User aluno;

	@PostConstruct
	public void init() {
		if(aluno == null){
			aluno = new User();
		}
	}

	public String saveUser() {
		try {
			userService.update(aluno, false);
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		reportarMensagemDeSucesso("Aluno atualizado!");
		return "/paginas/protegido/admin/Lista_Alunos.xhtml?faces-redirect=true";
	}
	
//	public void oncapture(CaptureEvent captureEvent) {
//        byte[] data = captureEvent.getData();
//        
//        aluno.setFoto(data);
//	}

	public User getAluno() {
		return aluno;
	}

	public void setAluno(User aluno) {
		this.aluno = aluno;
	}

}

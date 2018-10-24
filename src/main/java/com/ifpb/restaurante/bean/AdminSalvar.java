package com.ifpb.restaurante.bean;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.ifpb.restaurante.Entities.Grupo;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.service.ServiceDacaException;
import com.ifpb.restaurante.service.UserService;


@Named
@ViewScoped
public class AdminSalvar extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1466747777612502825L;

	@Inject
	private UserService userService;

	private User admin;

	@PostConstruct
	public void init() {
		if(admin == null){
			admin = new User();
			admin.setGrupo(Grupo.ADMIN);
		}
	}

	public String saveUser() {
		try {

			admin.setSenha(admin.getLogin());
			userService.save(admin);

		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		reportarMensagemDeSucesso("Admin'" + admin.getNome() + "' salvo!");
		admin = new User();
		return null;
	}
	
//	public void oncapture(CaptureEvent captureEvent) {
//        byte[] data = captureEvent.getData();
//        
//        aluno.setFoto(data);
//	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
}

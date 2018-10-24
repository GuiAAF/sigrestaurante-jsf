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
public class Editar_Funcionario extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1466747777612502825L;

	@Inject
	private UserService userService;
	
	private User funcionario;

	@PostConstruct
	public void init() {
		if(funcionario == null){
			funcionario = new User();
		}
	}

	public String saveUser() {
		try {
			userService.update(funcionario, false);
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		reportarMensagemDeSucesso("O funcionario(a) '" + funcionario.getNome() + " foi atualizado!");
		return "/paginas/protegido/admin/Lista_Funcionarios.xhtm?faces-redirect=true";
	}

	public User getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(User funcionario) {
		this.funcionario = funcionario;
	}

	

}

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
public class Salvar_Funcionario extends AbstractBean{

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
			funcionario.setGrupo(Grupo.FUNCIONARIO);
		}

	}

	public String saveUser() {
		try {

			userService.save(funcionario);

		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		reportarMensagemDeSucesso("Funcionario(a) " + funcionario.getNome() + " salvo(a)!");
		funcionario = new User();
		return "/paginas/protegido/admin/Lista_Funcionarios.xhtm?faces-redirect=true";
	}


	public User getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(User funcionario) {
		this.funcionario = funcionario;
	}

}

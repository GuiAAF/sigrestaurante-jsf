package com.ifpb.restaurante.bean;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.service.ServiceDacaException;
import com.ifpb.restaurante.service.UserService;

@Named
@ViewScoped
public class UserEditPassword extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3457254845220549030L;
			
	private User user;

	private String confirmacaoPasswordAtual;

	protected String passwordAtualHash;

	@Inject
	private UserService userService;

	public void armazenarSenhaAtualDoUsuario() {
		passwordAtualHash = user.getSenha();
	}

	public String changePassword() {

		try {
			// Confirmar que senha atual equivale a armazenada
			if (!senhaAtualConfere()) {
				reportarMensagemDeErro("Sua senha atual está incorreta!");
				return null; // Permanecer na mesma página
			}

			userService.update(user, true);
		} catch (ServiceDacaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		
		reportarMensagemDeSucesso("Senha alterada com sucesso!!");
		
		return null;
	}

	private boolean senhaAtualConfere() throws ServiceDacaException {
		
		return userService.senhaAtualConfere(passwordAtualHash, getConfirmacaoPasswordAtual());
	}

	public String getConfirmacaoPasswordAtual() {
		return confirmacaoPasswordAtual;
	}

	public void setConfirmacaoPasswordAtual(String confirmacaoPasswordAtual) {
		this.confirmacaoPasswordAtual = confirmacaoPasswordAtual;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}

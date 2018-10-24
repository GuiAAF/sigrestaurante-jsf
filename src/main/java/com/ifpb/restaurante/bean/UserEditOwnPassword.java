package com.ifpb.restaurante.bean;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.filters.UserFilter;
import com.ifpb.restaurante.service.ServiceDacaException;
import com.ifpb.restaurante.service.UserService;

@Named
@ViewScoped
public class UserEditOwnPassword extends UserEditPassword {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5860793285634698186L;

	@Inject
	private UserService userService;
	
	public void init() {
		String login = getUserLogin();
		UserFilter filter = new UserFilter();
		filter.setLogin(login);
		List<User> users = null;
		try {
			users = userService.findBy(filter);
		} catch (ServiceDacaException e) {
			// XXX In order to avoid: WELD-001471: Interceptor method postConstructor defined on class UserEditOwnPassword
			// is not defined according to the specification. It should not throw ServiceDacException, 
			// which is a checked exception.
			throw new RuntimeException("Some error ocurred trying to initialize.", e);
		}
		setUser(users.get(0));
		this.passwordAtualHash = getUser().getSenha();
	}
}

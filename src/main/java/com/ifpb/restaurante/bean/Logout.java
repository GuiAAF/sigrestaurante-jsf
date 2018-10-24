package com.ifpb.restaurante.bean;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class Logout extends AbstractBean {

	private static final long serialVersionUID = -7437667367775973347L;

	public void efetuarLogout() throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		session.invalidate();
		ec.redirect(ec.getApplicationContextPath() + "/paginas/protegido/index.xhtml?faces-redirect=true");
	}

}

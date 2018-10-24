package com.ifpb.restaurante.dao;


public class PersistenciaException extends Exception {
	
	
	private static final long serialVersionUID = 1780243892137781599L;

	public PersistenciaException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}

	

	public PersistenciaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}

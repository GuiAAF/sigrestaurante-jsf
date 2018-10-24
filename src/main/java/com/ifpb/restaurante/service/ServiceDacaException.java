package com.ifpb.restaurante.service;

import com.ifpb.restaurante.RestauranteException;

public class ServiceDacaException extends RestauranteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3082351960302866350L;

	public ServiceDacaException(String mensagem) {
		super(mensagem);
	}

	public ServiceDacaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}

package com.ifpb.restaurante;

public class RestauranteException extends Exception {

	private static final long serialVersionUID = -7669751088704144947L;

	public RestauranteException(String mensagem) {
		super(mensagem);
	}

	public RestauranteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}

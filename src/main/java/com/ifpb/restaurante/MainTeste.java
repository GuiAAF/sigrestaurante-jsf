package com.ifpb.restaurante;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import com.ifpb.restaurante.Entities.Grupo;
import com.ifpb.restaurante.Entities.User;

public class MainTeste {

	public void generateData(EntityManager em) {

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		try {

			User admin = new User();
			admin.setGrupo(Grupo.ADMIN);
			admin.setNome("Admin");
			admin.setLogin("Admin");
			admin.setSenha("1234567");

			this.calcularHashDaSenha(admin);


			em.persist(admin);
			

			transaction.commit();
		}catch (PersistenceException | RestauranteException e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	public String calcularHashDaSenha(User user) throws RestauranteException {
		user.setSenha(hash(user.getSenha()));
		return user.getSenha();
	}

	private String hash(String password) throws RestauranteException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			//BigInteger bigInt = new BigInteger(1, digest);
			//String output = bigInt.toString(16);
			String output = Base64.getEncoder().encodeToString(digest);
			return output;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RestauranteException("Could not calculate hash!", e);
		}
	}
	
	public static void main(String[] args) {
		MainTeste t = new MainTeste();
		User u = new User();
		u.setSenha("1234567");
		try {
			System.out.println(t.calcularHashDaSenha(u));
		} catch (RestauranteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

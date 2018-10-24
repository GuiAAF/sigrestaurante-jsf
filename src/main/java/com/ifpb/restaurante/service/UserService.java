package com.ifpb.restaurante.service;



import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import javax.inject.Inject;
import com.ifpb.restaurante.Entities.User;
import com.ifpb.restaurante.dao.PersistenciaException;
import com.ifpb.restaurante.dao.UserDAO;
import com.ifpb.restaurante.filters.UserFilter;
import com.ifpb.restaurante.util.TransacionalCdi;

public class UserService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7803325791425670859L;
	
	@Inject
	private UserDAO userDAO;
	
	@TransacionalCdi
	public void save(User user) throws ServiceDacaException {
		try {
			// Verificar se login já existe
			validarLogin(user);
			calcularHashDaSenha(user);
			userDAO.save(user);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	@TransacionalCdi
	public void update(User user, boolean passwordChanged) throws ServiceDacaException {
		
		try {
			// Verificar se login já existe
			validarLogin(user);
			if (passwordChanged) {
				calcularHashDaSenha(user);
			}
			userDAO.update(user);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	@TransacionalCdi
	public void delete(User user) throws ServiceDacaException {
		try {
			userDAO.delete(user);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	public User getByID(int userId) throws ServiceDacaException {
		try {
			return userDAO.getByID(userId);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	public List<User> getAll() throws ServiceDacaException {
		try {
			return userDAO.getAll();
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}

	public List<User> findBy(UserFilter filter) throws ServiceDacaException {
		try {
			return userDAO.findBy(filter);
		} catch (PersistenciaException e) {
			throw new ServiceDacaException(e.getMessage(), e);
		}
	}
	
	private String calcularHashDaSenha(User user) throws ServiceDacaException {
		user.setSenha(hash(user.getSenha()));
		return user.getSenha();
	}

	public boolean senhaAtualConfere(String passwordAtualHash, String confirmacaoPasswordAtual) throws ServiceDacaException {
		
		if (passwordAtualHash == null && confirmacaoPasswordAtual == null) {
			return true;
		}

		if (passwordAtualHash == null || confirmacaoPasswordAtual == null) {
			return false;
		}
		
		String confirmacaoPasswordAtualHash = hash(confirmacaoPasswordAtual);
		
		return passwordAtualHash.equals(confirmacaoPasswordAtualHash);
	}

	private String hash(String password) throws ServiceDacaException {
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
			throw new ServiceDacaException("Could not calculate hash!", e);
		}
	}

	public void validarLogin(User user) throws ServiceDacaException {
		boolean jahExiste = userDAO.existeUsuarioComLogin(user);
		if (jahExiste) {
			throw new ServiceDacaException("Já existe aluno cadastrado com essa matricula"); 
		}
	}
	
	public User alunoLogado(String login) {

		return userDAO.getByLogin(login);

	}
	
}

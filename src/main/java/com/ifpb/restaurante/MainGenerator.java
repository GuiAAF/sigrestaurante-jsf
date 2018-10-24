package com.ifpb.restaurante;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainGenerator {
	
	public static void main(String[] args) throws RestauranteException  {
		Map<String, String> properties = getPersistenceUnitProperties();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("restaurante-test", properties);
		EntityManager em = emf.createEntityManager();
		
		MainTeste dao = new MainTeste();
		
		try {
			dao.generateData(em);
		} finally {
			em.close();
			emf.close();
		}
	}
	
	public static Map<String, String> getPersistenceUnitProperties() {
		Map<String, String> properties = new HashMap<>();
		getPersistenceUnitPropertiesPostgresql(properties);
		// XXX: Descomentar linha abaixo e comentar a linha de cima caso for utilizar o MySQL 
		//getPersistenceUnitPropertiesMySQL(properties);
		
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.use_sql_comments", "true");
		properties.put("hibernate.c3p0.min_size", "5");
		properties.put("hibernate.c3p0.max_size", "20");
		properties.put("hibernate.c3p0.timeout", "3000");
		properties.put("hibernate.c3p0.max_statements", "50");
		properties.put("hibernate.c3p0.idle_test_period", "300");
		return properties;
	}
	
	/**
	 * TODO: Atualizar configuraÃ§Ã£o das propriedades aqui caso seu BD seja o Postgresql 
	 * 
	 * @param properties
	 */
	public static void getPersistenceUnitPropertiesPostgresql(Map<String, String> properties) {
		properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5433/restauranteEstudantil");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
		properties.put("hibernate.connection.username", "postgres");
		properties.put("hibernate.connection.password", "admin");
		properties.put("hibernate.default_schema", "public");
	}


}

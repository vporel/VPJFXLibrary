package vplibrary.hibernate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Properties;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import repositories.Repository;


public class Hibernate {
	private static SessionFactory sessionFactory;
	private static Session session;
	
	/**
	 * M�thode qui doit �tre appel� avant toute toute communication avec la base de donn�es
	 * Il est pr�f�rable de l'appeler dans la fonction main qu programme
	 * @param propertiesFile
	 * @param entitiesClasses
	 * @return
	 */
	public static Session init(String propertiesFile, Class<?>[] entitiesClasses) {
		Properties hibernateProperties = new Properties();
		try {
			hibernateProperties.load(new FileInputStream(propertiesFile));
			
			Configuration configuration = new Configuration();
			for(Class<?> entityClass:entitiesClasses) {
				configuration.addAnnotatedClass(entityClass);
			}
			configuration.setProperties(hibernateProperties);
			sessionFactory = configuration.buildSessionFactory(
					new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
			return getSession();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public static SessionFactory getSessionFactory() throws DBConnectionException{
		if(sessionFactory != null)
			return sessionFactory;
		else
			throw new DBConnectionException();
	}
	
	/**
	 * Session utilisant un cacheMode NORMAL
	 * @return
	 */
	public static Session getSession() {
		if(session == null) {
			try {
				session = getSessionFactory().openSession();
				session.setCacheMode(CacheMode.NORMAL);
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return session;
	}
	
	/**
	 * Retorune une instance du repository correspondant l'entit� e, param_tre
	 * @param entityClass
	 * @return
	 */
	public static Repository<?> getRepository(Class<?> entityClass){
		try {
			return (Repository<?>) Class.forName("pharmacie.repositories."+entityClass.getSimpleName()+"Repository").getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static class DBConnectionException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DBConnectionException() {
			super("Impossible de se connecter � la base de donn�es");
		}
	}
}

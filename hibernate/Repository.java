package vplibrary.hibernate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

public abstract class Repository<T extends Entity> {
	protected static Session session;
	
	static {
		session = Hibernate.getSession();
	}
	
	public abstract Class<T> getEntityClass();
	
	public T find(int id) {
		return (T) session.get(getEntityClass(), id);
	}
	
	/**
	 * Warning
	 * Fonction trop sp�cifique
	 * A utiliser uniquement avec des entit�s qui ont l'attribut id
	 * @param id
	 * @return
	 */
	public boolean exists(int id) {
		try {
			return exists("id", id);
		} catch (NoSuchDatabaseFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean exists(String propertyName, Object value) throws NoSuchDatabaseFieldException {
		return exists(new HashMap<String, Object>() {{put(propertyName, value);}});
	}
	
	public boolean exists(Map<String, ? extends Object > criteria) throws NoSuchDatabaseFieldException {
		return findOneBy(criteria) != null;
	}
	
	public T findOneBy(String propertyName, Object value) throws NoSuchDatabaseFieldException {
		return findOneBy(new HashMap<String, Object>() {{put(propertyName, value);}});
	}
	
	public T findOneBy(Map<String, ? extends Object > criteria) throws NoSuchDatabaseFieldException{
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getEntityClass());
		Root<T> root = query.from(getEntityClass());
		query.select(root);
		for(Entry<String, ? extends Object> entry : criteria.entrySet()) {
			if(HibernateUtils.existsDatabaseField(getEntityClass(), entry.getKey()))
				query.where(builder.equal(root.get(entry.getKey()), entry.getValue()));
			else{
				throw new NoSuchDatabaseFieldException(entry.getKey());
			}
		}
		try {
			return (T) session.createQuery(query).getResultList().get(0);
		}catch(NoResultException|IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public List<T> simpleFindBy(String propertyName, Object value){
		return simpleFindBy(propertyName, value, null);
	}
	
	public List<T> simpleFindBy(String propertyName, Object value, String order){
		return simpleFindBy(propertyName, value, order, Integer.MAX_VALUE);
	}
	
	public List<T> simpleFindBy(String propertyName, Object value, String order, int maxResults){
		if(order != null) {
			ArrayList<String> orders = new ArrayList<>(){{add(order);}};
			return findBy(new HashMap<>(){{put(propertyName, value);}}, orders, maxResults);
		}
		return findBy(new HashMap<>() {{put(propertyName, value);}}, null, maxResults);
	}
	
	/**
	 * @param HashMap criteria 
	 * @return List<T>
	 */
	public List<T> findBy(Map<String, ?> criteria){
		return findBy(criteria, null);
	}
	
	/**
	 * With this method, the maximum result is defined by Integer.MAX_VALUE
	 * @param criteria
	 * @param List<Order> order
	 * @return List<T>
	 */
	public List<T> findBy(Map<String, ?> criteria, ArrayList<String> orders){
		return findBy(criteria, orders, Integer.MAX_VALUE);
	}
	/**
	 * @param criteria
	 * @param List<Order> order
	 * @param maxResults
	 * @return
	 */
	public List<T> findBy(Map<String, ?> criteria, ArrayList<String> orders, int maxResults){
		return findBy(criteria, orders, maxResults, 0);
	}
	/**
	 * 
	 * @param criteria
	 * @param List<Order> order
	 * @param maxResults
	 * @param firstResult
	 * @return
	 */
	public List<T> findBy(Map<String, ?> criteria, ArrayList<String> orders, int maxResults, int firstResult) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getEntityClass());
		Root<T> root = query.from(getEntityClass());
		query.select(root);
		if(criteria != null) {
			criteria.forEach((key, value) -> {
				query.where(builder.equal(root.get(key), value));
			});
		}
		if(orders == null) {
			orders = new ArrayList<>();
			String naturalOrderField = Entity.getEntityNaturalOrderField(getEntityClass());
			if(!orders.contains(naturalOrderField))
				orders.add(naturalOrderField);
		}
		for(String o: orders) {  // Order
			if(!o.startsWith("-"))
				query.orderBy(builder.asc(root.get(o)));
			else {
				query.orderBy(builder.desc(root.get(o.substring(1))));
			}
		}
		
		try {
			return (List<T>) session.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
		}catch(NoResultException e) {
			return new ArrayList<T>();
		}
	}
	
	public List<T> findAll() {
		return findBy(null);
	}
	
}

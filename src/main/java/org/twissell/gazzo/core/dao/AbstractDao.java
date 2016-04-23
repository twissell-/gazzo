package org.twissell.gazzo.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.twissell.gazzo.core.annotation.GenericDao;
import org.twissell.gazzo.core.model.Entity;
 
/**
 * Abstract super class DAOs. This class has the responsibility of define the
 * common behavior for all system DAOs.
 * 
 * All DAOs <b>must</b> extend this class
 * 
 * @author Demo
 *
 * @param <T> The type this DAO will manage.
 * @param <K> The type of the primary key.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractDao<T extends Entity<K>, K> {
 
    @Autowired
    private SessionFactory sessionFactory;
    
	@SuppressWarnings("unused")
	private Class<?> persistentClass;
	private String className;
	private String alias;
    
    public AbstractDao() {
		// gets the annotation data
		GenericDao genericDao = this.getClass().getAnnotation(GenericDao.class);
		if(genericDao != null){
			this.persistentClass = genericDao.clazz();
			this.className = genericDao.clazz().getName();
	
			String name = genericDao.clazz().getSimpleName();
			this.alias = name.substring(0, 1).toLowerCase() + name.substring(1);
		}
	}
 
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    
    /**
     * Persists a entity in the db doing insert or update as necessary.
     * 
     * @param entity the entity to persist.
     */
    public void save(T entity) {
		this.getSession().saveOrUpdate(entity);
	}
 
    /**
     * Performs an insert into the db.
     * 
     * @param entity the entity to be inserted
     * @return
     */
    @SuppressWarnings("unchecked")
	public K insert(T entity) {
		return (K) this.getSession().save(entity);
	}
    
    /**
     * Performs an insert into the db.
     * 
     * @param entity the entity to be updated.
     */
    public void update(T entity) {
		this.getSession().update(entity);
	}
    
    /**
     * Performs a logical delete of the entity, that is, makes {@code entity.enabled = false}
     * and persist it. 
     * 
     * @param entity the entity to be logical deleated.
     */
    public void delete(T entity) {
    	entity.setEnabled(false);
    	this.update(entity);
	}
    
    /**
     * @param id the of the entity you are looking for.
     * @return the entity corresponding to that id
     */
    @SuppressWarnings("unchecked")
	public T findById(K id) {
		return (T) this.findAllWhere("id = " + id.toString());
	}
    
    
    /**
     * Equivalent to {@code findAllWhere("enabled = false")}
     * 
     * @return all "not logical" objects of the class
     */
    public List<T> findAll() {
    	return this.findAllWhere("enabled = false");
	}
    
    /**
     * Perform a simple select query with a single field where clause
     * 
     * @param whereClause the condition. e.g. "enabled = false"
     * @return all objects matching conditions
     */
    @SuppressWarnings("unchecked")
	public List<T> findAllWhere(String whereClause) {
    	StringJoiner sj = new StringJoiner(" ");
    	sj.add("from").add(className).add(alias).add("where").add(whereClause);
		
    	Query query = this.getSession().createQuery(sj.toString());
    	
    	return ((List<T>) query.list());
	}
    
    /**
     * Execute a named query defined in the entity type.
     * 
     * @param queryName the name of the namedQuery to be executed.
     * @return the result of the query as a list.
     */
    public List<T> execute(String queryName) {
    	return this.execute(queryName, new HashMap<String, Object>());
    }
    
    /**
     * @return the name of the namedQuery to be executed.
     * @param parameters a map where the key's are the name of the parameters
     * @return the result of the query as a list.
     */
    @SuppressWarnings("unchecked")
	public List<T> execute(String queryName, Map<String, Object> parameters) {
    	
    	Query query = this.getSession().getNamedQuery(queryName);
    	
    	for (Entry<String, Object> param : parameters.entrySet()) {
    		query.setParameter(param.getKey(), param.getValue());
		}
    	
    	return (List<T>) query.list();
    }
}

package org.twissell.gazzo.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.twissell.gazzo.core.dao.AbstractDao;
import org.twissell.gazzo.core.model.Entity;

@Service
@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractService<T extends Entity<K>, K> {
 
    @Autowired
    private AbstractDao<T, K> dao;
    
    /**
     * Performs an insert into the db.
     * 
     * @param entity the entity to be inserted
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public K insert(T entity) {
		return this.dao.insert(entity);
	}
    
    /**
     * Performs an insert into the db.
     * 
     * @param entity the entity to be updated.
     */
    public void update(T entity) {
		this.dao.update(entity);
	}

 
     /**
     * Performs a logical delete of the entity, that is, makes {@code entity.enabled = false}
     * and persist it. 
     * 
     * @param entity the entity to be logical deleated.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void delete(T entity) {
    	this.dao.delete(entity);
	}
    
    /**
     * @param id the of the entity you are looking for.
     * @return the entity corresponding to that id
     */
    @Transactional(readOnly = true)
    public T findById(K id) {
		return (T) this.dao.findById(id);
	}
    
    
    /**
     * Equivalent to {@code findAllWhere("enabled = false")}
     * 
     * @return all "not logical" objects of the class
     */
    @Transactional(readOnly = true)
    public List<T> findAll() {
    	return this.dao.findAll();
	}
}


package org.twissell.gazzo.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.twissell.gazzo.core.model.Entity;
import org.twissell.gazzo.core.service.AbstractService;

@RestController
public abstract class AbstractController<T extends Entity<K>, K> {

	@Autowired 
	private AbstractService<T, K> service;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<T> findAll() {
		return service.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public T findById(@PathVariable("id") K id) {
		return service.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public K insert(@RequestBody T entity) {
		return service.insert(entity);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void update(@RequestBody T entity) {
		service.update(entity);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delte(@RequestBody T entity) {
		service.delete(entity);
	}

	
}

package org.twissell.gazzo.core.model;

import javax.persistence.Version;

/**
 * @author Demo
 *
 * @param <K> Class of the id field.
 */
@javax.persistence.Entity
public abstract class Entity <K> { 

	private Boolean enabled;
	
	@Version
	private Integer version;
	
	public abstract K getId();
	public abstract void setId(K id);
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
}

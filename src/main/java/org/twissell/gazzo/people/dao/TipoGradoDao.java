package org.twissell.gazzo.people.dao;

import org.twissell.gazzo.core.annotation.GenericDao;
import org.twissell.gazzo.core.dao.AbstractDao;
import org.twissell.gazzo.people.model.TipoGrado;

@GenericDao(clazz = TipoGrado.class)
public class TipoGradoDao extends AbstractDao<TipoGrado, Integer> {

}

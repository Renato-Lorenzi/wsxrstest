package br.com.rml.utils;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PersistenceUtils {

	public static EntityManager createEntityManager(Entity entity) {
		return Persistence.createEntityManagerFactory(entity.getEntityName()).createEntityManager();
	}

	public static void persist(Object entity) {
		EntityManager manager = PersistenceUtils.createEntityManager(Entity.byClass(entity.getClass()));
		manager.getTransaction().begin();
		manager.persist(entity);
		manager.getTransaction().commit();

	}
}

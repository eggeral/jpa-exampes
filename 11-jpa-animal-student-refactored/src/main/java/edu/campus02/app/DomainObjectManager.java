package edu.campus02.app;

import edu.campus02.domain.Animal;
import edu.campus02.domain.DomainObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.List;

public class DomainObjectManager {
	private EntityManagerFactory factory;

	public DomainObjectManager(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public void persist(DomainObject entity) { // Animal oder Student
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.persist(entity);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public void persistIfDoesNoExist(DomainObject entity) {
		if (find(entity) == null) {
			persist(entity);
		}
	}

	public void update(DomainObject entity) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.merge(entity);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public void remove(DomainObject entity) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			DomainObject attachedEntity = manager.merge(entity);
			manager.getTransaction().begin();
			manager.remove(attachedEntity);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public <T extends DomainObject> T find(T entity) {
		if (entity == null) {
			return null;
		}
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			return (T) manager.find(entity.getClass(), entity.getId());
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public <T extends DomainObject> List<T> findAll(Class<T> objectClass) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Query query = manager.createQuery("SELECT a from " + objectClass.getSimpleName() + " a");
			return query.getResultList();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}
	
	public List<Animal> findAllBySpecies(String species) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Query query = manager.createQuery("SELECT a from Animal a where a.species = :speciesParam");
			query.setParameter("speciesParam", species);
			return query.getResultList();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}
	
}

package edu.campus02.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import edu.campus02.domain.Animal;

public class AnimalManager {
	private EntityManagerFactory factory;

	public AnimalManager(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public void persist(Animal animal) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.persist(animal);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public void persistIfDoesNoExist(Animal animal) {
		if (find(animal.getName()) == null) {
			persist(animal);
		}
	}

	public void update(Animal animal) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.merge(animal);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public void remove(Animal animal) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Animal attachedAnimal = manager.merge(animal);
			manager.getTransaction().begin();
			manager.remove(attachedAnimal);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public Animal find(String name) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			return manager.find(Animal.class, name);
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public List<Animal> findAll() {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Query query = manager.createQuery("SELECT a from Animal a");
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

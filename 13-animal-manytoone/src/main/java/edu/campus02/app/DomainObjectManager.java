package edu.campus02.app;

import edu.campus02.domain.Animal;
import edu.campus02.domain.DomainObject;
import edu.campus02.domain.Species;
import edu.campus02.domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DomainObjectManager {
	private EntityManagerFactory factory;

	private static Random randomGenerator = new Random();
	private static final ArrayList<String> namePool = new ArrayList<>();
	static {
		namePool.add("Hansi");
		namePool.add("Franzi");
		namePool.add("Seppi");
		namePool.add("Kurti");
		namePool.add("Fritzi");
		namePool.add("Maxi");
	}
	
	public DomainObjectManager(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public void persist(DomainObject entity) { // Animal oder Student
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
	
	public List<Animal> findAllBySpecies(Species species) {
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
	
	public <T extends DomainObject> T refresh(T entity) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			T attached_entity = manager.merge(entity);
			manager.refresh(attached_entity);
			return attached_entity;
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	// Warum ist diese Hilfsmethode im DomainObjectManager und nicht direkt in JpaMain?
	// -> wir hätten sie dann als static definiert und in dieser statischen Signatur den Student dringehabt
	// -> und dann funktioniert das dynamische JPA Enhancement nicht mehr!
	public void setRandomSurname(Student student) {
		String surname = namePool.get(randomGenerator.nextInt(namePool.size()));
		while (surname.equals(student.getSurname())) {
			surname = namePool.get(randomGenerator.nextInt(namePool.size()));
		}
		student.setSurname(surname);
	}

	public Species workaroundForLazyFetch(Species species) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Species attached_entity = manager.merge(species);
			manager.refresh(attached_entity);
			attached_entity.getAnimals(); // triggers DB access when fetching LAZY
			return attached_entity;
		} finally {
			if (manager != null) {
				manager.close();
			}
		}		
	}
	
}

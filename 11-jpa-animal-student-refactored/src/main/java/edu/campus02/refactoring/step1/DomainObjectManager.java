package edu.campus02.refactoring.step1;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import edu.campus02.domain.Animal;
import edu.campus02.domain.Student;

/**
 * AnimalManager:
 *  - Umbenennen des AnimalManagers in etwas Allgemeineres wie DomainObjectManager
 *  - Einführen der Animal-Methoden auch für Student-Entitäten. 
 *  - Bei gleicher Signatur wird der Typ der Entität in den Namen aufgenommen 
 *    (zB. findAllStudents bzw. findAllAnimals).
 */
public class DomainObjectManager {
	private EntityManagerFactory factory;

	public DomainObjectManager(EntityManagerFactory factory) {
		this.factory = factory;
	}

// ANIMAL	
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

	public List<Animal> findAllAnimals() {
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

	// STUDENT
	public void persist(Student student) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.persist(student);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public void persistIfDoesNoExist(Student student) {
		if (find(student.getName()) == null) {
			persist(student);
		}
	}

	public void update(Student student) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.merge(student);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public void remove(Student student) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Student attachedStudent = manager.merge(student);
			manager.getTransaction().begin();
			manager.remove(attachedStudent);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public Student findStudent(String name) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			return manager.find(Student.class, name);
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public List<Student> findAllStudents() {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Query query = manager.createQuery("SELECT s from Student s");
			return query.getResultList();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

}

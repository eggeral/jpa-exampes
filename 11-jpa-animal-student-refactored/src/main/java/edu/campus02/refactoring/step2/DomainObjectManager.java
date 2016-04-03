package edu.campus02.refactoring.step2;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import edu.campus02.domain.Animal;
import edu.campus02.domain.DomainObject;

/**
 * AnimalManager:
 *  - Die Methoden für Student und Animal sind praktisch gleich, können aber aufgrund des unterschiedlichen
 *    Typs nicht wiederverwendet werden. 
 *  - Daher: Einführen eines neuen Basistyps für Student und Animal (DomainObject). Dazu reicht ein 
 *    Interface, welches lediglich die Methode "String getId()" definiert. 
 *  - Umstellen der Methoden, die für Student und Animal gleich sind auf DomainObject und Löschen der 
 *    Duplikate. Jetzt reicht eine Methode für beide Klassen, das Resultat ist allerdings ein 
 *    DomainObject und muss gecastet werden.
 *  - Spezialfall Methode findAll, für die der Typ der Entität bekannt sein muss (wollen wir alle Animal-
 *    oder alle Studenten-Entitäten finden?). Hier führen wir die Klasse als Parameter ein. 
 *  - Spezialfall Methode find, für die sowohl der Typ der Entität bekannt sein muss, als auch der gesuchte
 *    Primary Key. Zugriff auf beide Informationen haben wir, wenn wir eine Instanz einer Entität als 
 *    Parameter verwenden (also Student oder Animal) anstatt des Strings, für den wir nicht unterscheiden
 *    können, ob wir Animal oder Student suchen.  
 */
public class DomainObjectManager {
	private EntityManagerFactory factory;

	public DomainObjectManager(EntityManagerFactory factory) {
		this.factory = factory;
	}

	/*
	 * Diese Methode bleibt gleich, weil sie auch nur bei "Animal" Sinn macht, man kann daher 
	 * direkt auf Animal arbeiten.  
	 */
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

	/*
	 * persist(Animal) und persist(Student) kann einfach durch persist(DomainObject) ersetzt
	 * werden, der Code ist für beide bisherigen Varianten praktisch völlig gleich.
	 */
	public void persist(DomainObject entity) {
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

	/*
	 * update(Animal) und update(Student) kann einfach durch update(DomainObject) ersetzt
	 * werden, der Code ist für beide bisherigen Varianten praktisch völlig gleich.
	 */
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

	/*
	 * remove(Animal) und remove(Student) kann einfach durch remove(DomainObject) ersetzt
	 * werden, der Code ist für beide bisherigen Varianten praktisch völlig gleich, wenn man 
	 * anstelle der dezidierten Klasse der Entität "DomainObject" verwendet.
	 */
	public void remove(DomainObject entity) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			DomainObject attachedDomainObject = manager.merge(entity);
			manager.getTransaction().begin();
			manager.remove(attachedDomainObject);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	/*
	 * Aus findAnimal(String) und findStudent(String) wird in diesem Refactoring-Schritt ein 
	 * find(DomainObject). Allerdings liefert das nicht direkt ein Animal oder einen Student 
	 * zurück, sondern kann nur die gemeinsame Basis zurückliefern, d.h. in der aufrufenden 
	 * Klasse muss entsprechend gecastet werden.
	 * Anstelle der hardcoded Verwendung von zB. Animal.class wird hier entity.getClass() 
	 * verwendet, statt des Primary Keys als Parameter kann man den Primary Key über die im 
	 * Interface definierte Methode getId() ermitteln.  
	 */
	public DomainObject find(DomainObject entity) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			return manager.find(entity.getClass(), entity.getId());
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	/*
	 * persistIfDoesNoExist(Animal) und persistIfDoesNoExist(Student) kann ebenfalls einfach durch
	 * persistIfDoesNoExist(DomainObject) ersetzt werden. 
	 * Im Body der Methode kann man jetzt aber nicht mehr "getName" verwenden, weil das auf DomainObject
	 * nicht definiert ist. Stattdessen können wir aber getId() verwenden, das genau so definiert ist, 
	 * dass es für alle unsere Entitäten den Primary Key zurückgibt. 
	 * Und: find verwendet jetzt die Entität selbst statt des PrimaryKeys.
	 */
	public void persistIfDoesNoExist(DomainObject entity) {
		if (find(entity) == null) {
			persist(entity);
		}
	}


	/*
	 * Aus findAllAnimals() und findAllStudents() wird in diesem Refactoring-Schritt ein 
	 * findAll(Class). Allerdings liefert das nicht direkt ein Animal oder einen Student 
	 * zurück, sondern kann nur die gemeinsame Basis zurückliefern, d.h. in der aufrufenden 
	 * Klasse muss entsprechend gecastet werden (was bei einer Liste manchmal lästig sein kann). 
	 * Anstelle der hardcoded Verwendung des Typnamens (zB. Animal) wird hier classObj.getSimpleName()  
	 * verwendet.  
	 */
	public List<DomainObject> findAll(Class classObj) { // Parameter kann nicht "class" heißen, weil reservierter Identifier.
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Query query = manager.createQuery("SELECT a from " + classObj.getSimpleName() + " a");
			return query.getResultList();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

}

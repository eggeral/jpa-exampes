package edu.campus02.refactoring.step1;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.campus02.refactoring.step1.DomainObjectManager;
import edu.campus02.domain.Animal;
import edu.campus02.domain.Student;

/**
 * Motivation des Refactorings:
 *  - Wir möchten uns selbst um das Persistieren von "Student" Entitäten kümmern und nicht länger
 *    CascadeType.ALL verwenden. 
 *  - Damit das ebenso komfortabel funktioniert wie für "Animal"s sollte der AnimalManager 
 *    entsprechend angepasst werden. 
 *  - Man soll diesen angepassten DomainObjectManager für Student und Animal gleich verwenden können. 
 *
 * SCHRITT 1:
 * 
 * AnimalManager:
 *  - Umbenennen des AnimalManagers in etwas Allgemeineres wie DomainObjectManager
 *  - Einführen der Animal-Methoden auch für Student-Entitäten. 
 *  - Bei gleicher Signatur wird der Typ der Entität in den Namen aufgenommen 
 *    (zB. findAllStudents bzw. findAllAnimals).
 *  
 * JpaMain:
 *  - Ablauf gleich wie im Endergebnis (edu.campus02.app.JpaMain), nur sind die Methoden, für die
 *    man zwischen Animal und Student unterscheiden muss, extra implementiert und haben unterschiedliche
 *    Namen.   
 */

public class JpaMain {

	public static void main(String[] args) {
		
		// Eigene DB fürs Refactoring, damit wir am Endergebnis sicher nix ändern ....
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("refactoringDB");
		DomainObjectManager mgr = new DomainObjectManager(factory);

		Animal animal1 = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal1.setBirthdate(calendar.getTime());
		animal1.setName("Milia");
		animal1.setRace("Hase");
		animal1.setSpecies("Säugetier");

		Student student1 = mgr.findStudent("Streber"); // mgr braucht Klasse und Id
		if (student1 == null) {
			// Student gibt es noch nicht
			student1 = new Student("Streber");
			student1.setSurname("Rudi");
			student1.setAge(17);
			mgr.persist(student1);
		}
		
		animal1.setOwner(student1);
		
		Animal animal2 = new Animal();
		calendar.set(2012, 3, 5);
		animal2.setBirthdate(calendar.getTime());
		animal2.setName("Alvin");
		animal2.setRace("Landschildkröte");
		animal2.setSpecies("Reptil");
		
		mgr.persistIfDoesNoExist(animal1);
		System.out.println("created: " + animal1);

		mgr.persistIfDoesNoExist(animal2);
		System.out.println("created: " + animal2);


		List<Animal> animals = mgr.findAllBySpecies("Säugetier");
		for (Animal animal : animals) {
			System.out.println("found:" + animal);
		}

		
		animals = mgr.findAllAnimals();
		for (Animal animal : animals) {
			mgr.remove(animal);
			System.out.println("removed: " + animal);
		}

		animals = mgr.findAllAnimals();
		System.out.println("Found " + animals.size() + " animals.");
		
		
		// Den Studenten gibt es aber noch
		List<Student> students = mgr.findAllStudents();
		for (Student student : students) {
			System.out.println("found:" + student);
		}

 		factory.close();
	}

}

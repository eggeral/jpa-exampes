package edu.campus02.refactoring.step3;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
 * SCHRITT 3: GENERICS
 * 
 * AnimalManager:
 *  - Bei den find-Methoden (find und findAll) ist zwar klar, was zurückkommen soll (nämlich dasselbe, was
 *    wir auch als Input-Parameter hineingeben), es kommt abe rvorerst nur der Basistyp DomainObject
 *    zurück.  
 *  - Daher: Einführen eines generischen Typ-Parameters "T", der ein Unter-Typ der gemeinsamen Basis
 *    DomainObject ist und Verwendung desselben in den find-Methoden. 
 *  - Die anderen Methoden können gleich bleiben. 
 * 
 * Student   
 *  - Aus Bequemlichkeitsgründen Konstruktor Student(String name) einführen, damit man den Student-Parameter
 *    für DomainObjectManager.find() in einer Zeile als DomainObjectManager.find(new Student("Name"))
 *    schreiben kann. 
 *  - JPA verlässt sich auf einen default-Konstruktor ohne Argumente, weil es jetzt einen anderen Konstruktor auch 
 *    gibt, muss man den default-Konstruktor ohne Argumente selbst schreiben. 
 * 
 * JpaMain:
 *  - Ablauf wieder gleich wie im Endergebnis (edu.campus02.app.JpaMain), genaugenommen exakt gleich, weil
 *    "step3" eigentlich nur die für das Refactoring kommentierte Variante des Endergebnisses ist. 
 */
public class JpaMain {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("refactoringDB");
						
		DomainObjectManager mgr = new DomainObjectManager(factory);

		Animal animal1 = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal1.setBirthdate(calendar.getTime());
		animal1.setName("Milia");
		animal1.setRace("Hase");
		animal1.setSpecies("Säugetier");

		Student student1 = mgr.find(new Student("Streber")); // mgr braucht Klasse und Id
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
		// in dem Fall kann dem Student auch eine Schildkröte UND ein Hase gehören
		//animal2.setOwner(student1);
		
		
		mgr.persistIfDoesNoExist(animal1);
		System.out.println("created: " + animal1);

		mgr.persistIfDoesNoExist(animal2);
		System.out.println("created: " + animal2);


		List<Animal> animals = mgr.findAllBySpecies("Säugetier");
		for (Animal animal : animals) {
			System.out.println("found:" + animal);
		}

		
		animals = mgr.findAll(Animal.class);
		for (Animal animal : animals) {
			mgr.remove(animal);
			System.out.println("removed: " + animal);
		}

		animals = mgr.findAll(Animal.class);
		System.out.println("Found " + animals.size() + " animals.");
		
		
		// Den Studenten gibt es aber noch
		List<Student> students = mgr.findAll(Student.class);
		for (Student student : students) {
			System.out.println("found:" + student);
		}

 		factory.close();
	}

}

package edu.campus02.app;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.campus02.domain.Animal;
import edu.campus02.domain.Student;

public class JpaMain {
	
	public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("campusDB");
						
		DomainObjectManager mgr = new DomainObjectManager(factory);

		Animal animal1 = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal1.setBirthdate(calendar.getTime());
		animal1.setName("Milia");
		animal1.setRace("Hase");
		animal1.setSpecies("Säugetier");

		Student student1 = mgr.find(new Student("Hasenpappi")); 
		if (student1 == null) {
			// Student gibt es noch nicht
			student1 = new Student("Hasenpappi");
			mgr.setRandomSurname(student1);			
			student1.setAge(21);
			mgr.persist(student1);
		}
		
		animal1.setOwner(student1);						
		mgr.persistIfDoesNoExist(animal1);
		
		System.out.println("created: " + animal1);

		// In der DB kennt der Student sein Animal schon, im java Context noch nicht automatisch
		System.out.println("  Student now with pet? " + student1);
		
		// Änderung des Studenten
		mgr.setRandomSurname(student1);
		//student1.setSurname(getRandomStudentSurname());
		
		// Neu laden von der DB bringt das Haustier in den EM Context
		Student reloaded_student1 = mgr.find(student1);
		System.out.println("  Student re-found in database: " + reloaded_student1);
		System.out.println("  Original student in memory:" + student1);
		
		// Selbst ein Update bringt das Haustier nicht in den java Context 
		// (weil unser update das Objekt nicht vom merge übernimmt)
		mgr.update(student1);
		System.out.println("  Updated original student: " + student1);
		
		student1 = mgr.refresh(student1);
		System.out.println("  Refreshed original student: " + student1);
		
		// Anderen Studenten als Owner zuweisen
		Student student2 = mgr.find(new Student("Hasenmammi")); 
		if (student2 == null) {
			// Student gibt es noch nicht
			student2 = new Student("Hasenmammi");
			student2.setSurname("Helene");
			student2.setAge(21);
			mgr.persist(student2);
		}
		
		System.out.println("Students in DB:");
		List<Student> students = mgr.findAll(Student.class);
		for (Student student : students) {
			System.out.println("  found:" + student);
		}
		
		// null setzen mit orphanRemoval löscht auch den Studenten
		animal1.setOwner(null);
		mgr.update(animal1);
		student1.setPet(null);
		mgr.update(student1);
		animal1.setOwner(student2);
		student2.setPet(animal1);
		mgr.update(animal1);
		mgr.update(student2);
		System.out.println("Updated animal: " + animal1);

		// Falls orphanRemoval: student1 ist weg
		System.out.println("Students in DB after animal update:");
		students = mgr.findAll(Student.class);
		for (Student student : students) {
			System.out.println("  found:" + student);
		}
		
		List<Animal> animals = mgr.findAll(Animal.class);
		System.out.println("Remove all animals");
		for (Animal animal : animals) {
			// CascadeType.REMOVE/ALL oder orphanRemoval = true l�scht Studenten mit, ansonsten bleibt er in DB
			mgr.remove(animal);
			System.out.println("  removed: " + animal);
		}

		System.out.println("Students in DB after animal removal:");
		students = mgr.findAll(Student.class);
		for (Student student : students) {
			System.out.println("  found:" + student);
		}
		
		System.out.println("Remove all students");
		students = mgr.findAll(Student.class);
		for (Student student : students) {
			mgr.remove(student);
			System.out.println("  removed:" + student);
		}
		
 		factory.close();
	}

}

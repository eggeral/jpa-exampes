package edu.campus02.app;

import edu.campus02.domain.Animal;
import edu.campus02.domain.DomainObject;
import edu.campus02.domain.Student;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Calendar;
import java.util.List;

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

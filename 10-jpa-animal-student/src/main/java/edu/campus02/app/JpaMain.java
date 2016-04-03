package edu.campus02.app;

import edu.campus02.domain.Animal;
import edu.campus02.domain.Student;

import javax.persistence.CascadeType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;

import java.util.Calendar;
import java.util.List;

public class JpaMain {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("campusDB");
						
		Animal animal1 = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal1.setBirthdate(calendar.getTime());
		animal1.setName("Milia");
		animal1.setRace("Hase");
		animal1.setSpecies("Säugetier");

		// Student erzeugen ....
		Student student1 = new Student();
		student1.setName("Streber");
		student1.setSurname("Rudi");
		student1.setAge(17);
		// ... und als Owner zuweisen, den Rest macht @OneToOne(cascade= CascadeType.ALL)		
		animal1.setOwner(student1);
		
		Animal animal2 = new Animal();
		calendar.set(2012, 3, 5);
		animal2.setBirthdate(calendar.getTime());
		animal2.setName("Alvin");
		animal2.setRace("Landschildkröte");
		animal2.setSpecies("Reptil");
		
		AnimalManager mgr = new AnimalManager(factory);
		
		mgr.persistIfDoesNoExist(animal1);
		System.out.println("created: " + animal1);

		mgr.persistIfDoesNoExist(animal2);
		System.out.println("created: " + animal2);


		List<Animal> animals = mgr.findAllBySpecies("Säugetier");
		for (Animal animal : animals) {
			System.out.println("found:" + animal);
		}

		
		animals = mgr.findAll();
		for (Animal animal : animals) {
			mgr.remove(animal);
			System.out.println("removed: " + animal);
		}

		animals = mgr.findAll();
		System.out.println("Found " + animals.size() + " animals.");

 		factory.close();
	}

}

package edu.campus02.app;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.apache.derby.tools.sysinfo;

import edu.campus02.domain.Animal;

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
		
		Animal animal2 = new Animal();
		calendar.set(2012, 3, 5);
		animal2.setBirthdate(calendar.getTime());
		animal2.setName("Mellisso");
		animal2.setRace("Hase");
		animal2.setSpecies("Säugetier");
		
		AnimalManager mgr = new AnimalManager(factory);
		
		mgr.persistIfDoesNoExist(animal1);
		System.out.println("created: " + animal1);

		mgr.persistIfDoesNoExist(animal2);
		System.out.println("created: " + animal2);

		List<Animal> animals = mgr.findAll();
		for (Animal animal : animals) {
			System.out.println("found:" + animal);
			mgr.remove(animal);
			System.out.println("removed: " + animal1);
		}

		animals = mgr.findAll();
		System.out.println("Found " + animals.size() + " animals.");

 		factory.close();
	}

}

package edu.campus02.app;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.campus02.domain.Animal;
import edu.campus02.domain.Country;

public class JpaMain {
	
	public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("campusDB");
						
		DomainObjectManager mgr = new DomainObjectManager(factory);

		Country europe = new Country("Europa");
		mgr.persistIfDoesNoExist(europe);
		Country africa = new Country("Afrika");
		mgr.persistIfDoesNoExist(africa);

		Animal animal1 = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal1.setBirthdate(calendar.getTime());
		animal1.setName("Milia");
		animal1.setRace("Hase");
		animal1.addHabitat(europe);
		animal1.addHabitat(africa);
		
		Animal animal2 = new Animal();
		calendar.set(1901, 4, 5);
		animal2.setBirthdate(calendar.getTime());
		animal2.setName("Alvin");
		animal2.setRace("Landschildkröte");
		animal2.addHabitat(africa);

		Animal animal3 = new Animal();
		calendar.set(2013, 10, 5);
		animal3.setBirthdate(calendar.getTime());
		animal3.setName("Backe");
		animal3.setRace("Hamster");
		animal3.addHabitat(europe);

		System.out.println("Create animals:");
		mgr.persistIfDoesNoExist(animal1);		
		System.out.println("  created: " + animal1);
		mgr.persistIfDoesNoExist(animal2);		
		System.out.println("  created: " + animal2);
		mgr.persistIfDoesNoExist(animal3);		
		System.out.println("  created: " + animal3);
		
		List<Country> countries = mgr.findAll(Country.class);
		System.out.println("Remove all countries");
		for (Country country : countries) {
			country = mgr.refresh(country); // refresh with DB changes if already in memory
			mgr.remove(country);
			System.out.println("  removed: " + country);
		}
		
		List<Animal> animals = mgr.findAll(Animal.class);
		System.out.println("Remove all animals");
		for (Animal animal : animals) {
			mgr.remove(animal);
			System.out.println("  removed: " + animal);
		}

 		factory.close();
	}

}

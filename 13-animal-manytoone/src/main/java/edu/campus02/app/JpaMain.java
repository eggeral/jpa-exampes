package edu.campus02.app;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.campus02.domain.Animal;
import edu.campus02.domain.Species;
import edu.campus02.domain.Student;

public class JpaMain {
	
	public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("campusDB");
						
		DomainObjectManager mgr = new DomainObjectManager(factory);

		Species species1 = new Species("Säugetier");
		mgr.persistIfDoesNoExist(species1);
		Species species2 = new Species("Reptil");
		mgr.persistIfDoesNoExist(species2);

		Animal animal1 = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal1.setBirthdate(calendar.getTime());
		animal1.setName("Milia");
		animal1.setRace("Hase");
		animal1.setSpecies(species1);
		
		Animal animal2 = new Animal();
		calendar.set(1901, 4, 5);
		animal2.setBirthdate(calendar.getTime());
		animal2.setName("Alvin");
		animal2.setRace("Landschildkröte");
		animal2.setSpecies(species2);

		Animal animal3 = new Animal();
		calendar.set(2013, 10, 5);
		animal3.setBirthdate(calendar.getTime());
		animal3.setName("Backe");
		animal3.setRace("Hamster");
		animal3.setSpecies(species1);

		System.out.println("Create animals:");
		mgr.persistIfDoesNoExist(animal1);		
		System.out.println("  created: " + animal1);
		mgr.persistIfDoesNoExist(animal2);		
		System.out.println("  created: " + animal2);
		mgr.persistIfDoesNoExist(animal3);		
		System.out.println("  created: " + animal3);

		// Funktioniert findBySpecies, wenn man statt String die Klasse verwendet?
		System.out.println("Find animals by species: " + species1.getName());
		List<Animal> animals = mgr.findAllBySpecies(species1);
		for (Animal animal : animals) {
			System.out.println("  found: " + animal);
		}
		System.out.println("Find animals by species: " + species2.getName());
		animals = mgr.findAllBySpecies(species2);
		for (Animal animal : animals) {
			System.out.println("  found: " + animal);
		}

		System.out.println("Find species in database:");
		List<Species> species = mgr.findAll(Species.class);
		for (Species spec : species) {
			spec = mgr.refresh(spec); // take care that db-changes are reflected in memory
			System.out.println("  found: " + spec);
		}
		
		animals = mgr.findAll(Animal.class);
		System.out.println("Remove all animals");
		for (Animal animal : animals) {
			mgr.remove(animal);
			System.out.println("  removed: " + animal);
		}

		System.out.println("Remove species in database:");
		species = mgr.findAll(Species.class);
		for (Species spec : species) {
			spec = mgr.refresh(spec); // take care that db-changes are reflected in memory
			System.out.println("  found: " + spec);
			mgr.remove(spec);
			System.out.println("  removed: " + spec);
		}
		

 		factory.close();
	}

}

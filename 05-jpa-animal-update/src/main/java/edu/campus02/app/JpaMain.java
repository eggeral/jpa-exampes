package edu.campus02.app;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import edu.campus02.domain.Animal;

public class JpaMain {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("campusDB");
						
		Animal animal = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal.setBirthdate(calendar.getTime());
		animal.setName("Milia");
		animal.setRace("Hase");
		animal.setSpecies("Säugetier");
		
		AnimalManager mgr = new AnimalManager(factory);
		
		mgr.persistIfDoesNoExist(animal);
		System.out.println("created: " + animal);

		Animal readAnimal = mgr.find("Milia");
		System.out.println("found: " + readAnimal);

		readAnimal.setRace("Hund");
		mgr.update(readAnimal);
		System.out.println("updated: " + readAnimal);

		readAnimal = mgr.find("Milia");
		System.out.println("found: " + readAnimal);

		mgr.remove(readAnimal);
		System.out.println("removed: " + readAnimal);

		readAnimal = mgr.find("Milia");
		System.out.println("found: " + readAnimal);

		factory.close();
	}

}

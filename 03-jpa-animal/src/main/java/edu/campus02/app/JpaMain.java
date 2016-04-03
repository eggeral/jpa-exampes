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
		EntityManager manager = factory.createEntityManager();
		
				
		Animal animal = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal.setBirthdate(calendar.getTime());
		animal.setName("Milia");
		animal.setRace("Hase");
		animal.setSpecies("Säugetier");
		
		
		try {
			manager.getTransaction().begin();
			manager.persist(animal);			
			manager.getTransaction().commit();
	    	System.out.println("Successfully created " + animal);
		} catch (RollbackException e) {
			if (e.getCause() instanceof EntityExistsException) {
				System.out.println(animal + " already exists");				
			} else {
				e.printStackTrace();
			}
		}

		Animal readAnimal;
		readAnimal = manager.find(Animal.class, "Milia");
		System.out.println(readAnimal);

		manager.close();
		factory.close();
	}

}

package edu.campus02.app;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.campus02.domain.Animal;
import edu.campus02.domain.Dog;
import edu.campus02.domain.Fish;

public class JpaMain {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("campusDB");
						
		Fish fish1 = new Fish();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		fish1.setBirthdate(calendar.getTime());
		fish1.setName("Nemo");
		fish1.setOcean("Atlantic");
		
		Fish fish2 = new Fish();
		calendar.set(2012, 3, 5);
		fish2.setBirthdate(calendar.getTime());
		fish2.setName("Dori");
		fish2.setOcean("Pacific");

		
		Dog dog1 = new Dog();
		calendar.set(2012, 3, 5);
		dog1.setBirthdate(calendar.getTime());
		dog1.setName("Sammi");
		dog1.setToy("Mouse");
		
		Dog dog2 = new Dog();
		calendar.set(2012, 3, 5);
		dog2.setBirthdate(calendar.getTime());
		dog2.setName("Charly");
		dog2.setToy("Ball");

		AnimalManager mgr = new AnimalManager(factory);
		
		mgr.persistIfDoesNoExist(fish1);
		System.out.println("created: " + fish1);

		mgr.persistIfDoesNoExist(fish2);
		System.out.println("created: " + fish2);

		mgr.persistIfDoesNoExist(dog1);
		System.out.println("created: " + dog1);

		mgr.persistIfDoesNoExist(dog2);
		System.out.println("created: " + dog2);

		List<Animal> animals = mgr.findAll();
		for (Animal animal : animals) {
			System.out.println("found:" + animal);
			mgr.remove(animal);
			System.out.println("removed: " + animal);
		}

		animals = mgr.findAll();
		System.out.println("Found " + animals.size() + " animals.");

		factory.close();
 		
	}

}
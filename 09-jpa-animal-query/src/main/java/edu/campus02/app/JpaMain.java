package edu.campus02.app;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.campus02.domain.Animal;
import edu.campus02.domain.Dog;
import edu.campus02.domain.Fish;
import edu.campus02.domain.Owner;

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
        calendar.set(2010, 1, 4);
        fish2.setBirthdate(calendar.getTime());
        fish2.setName("Dori");
        fish2.setOcean("Pacific");


        Dog dog1 = new Dog();
        calendar.set(2007, 2, 6);
        dog1.setBirthdate(calendar.getTime());
        dog1.setName("Sammi");
        dog1.setToy("Mouse");

        Dog dog2 = new Dog();
        calendar.set(2013, 1, 1);
        dog2.setBirthdate(calendar.getTime());
        dog2.setName("Charly");
        dog2.setToy("Ball");

        Owner owner1 = new Owner();
        owner1.setName("Max");
        owner1.getAnimals().add(fish1);
        owner1.getAnimals().add(fish2);
        owner1.getAnimals().add(dog1);
        fish1.setOwner(owner1);
        fish2.setOwner(owner1);
        dog1.setOwner(owner1);

        Owner owner2 = new Owner();
        owner2.setName("Moritz");
        owner2.getAnimals().add(dog2);
        dog2.setOwner(owner2);

        AnimalManager mgr = new AnimalManager(factory);

        mgr.persistIfDoesNoExist(fish1);
        mgr.persistIfDoesNoExist(fish2);
        mgr.persistIfDoesNoExist(dog1);
        mgr.persistIfDoesNoExist(dog2);

        queryAndPrintResult(factory, "SELECT a FROM Animal a");
        queryAndPrintResult(factory, "SELECT f FROM Fish f");
        queryAndPrintResult(factory, "SELECT d FROM Dog d WHERE d.toy = 'Mouse'");
        queryAndPrintResult(factory, "SELECT a FROM Animal a WHERE a.name LIKE 'N%'");
        queryAndPrintResult(factory, "SELECT f FROM Fish f WHERE f.name LIKE 'N%'");
        queryAndPrintResult(factory, "SELECT a FROM Animal a WHERE a.birthdate > '2012-1-1'");
        queryAndPrintResult(factory, "SELECT a FROM Animal a WHERE a.owner.name LIKE 'Ma%'");

        // does not work with table per class!
        // use Owner.animals fetch=FetchType.EAGER in order load the animals of the owner!
        queryAndPrintResult(factory, "SELECT a.owner FROM Animal a WHERE a.birthdate > '2012-1-1'");

        factory.close();

    }

    private static void queryAndPrintResult(EntityManagerFactory factory,
                                            String queryStr) {
        EntityManager manager = null;
        try {
            manager = factory.createEntityManager();
            Query query = manager.createQuery(queryStr);
            List<Object> result = query.getResultList();
            System.out.println();
            System.out.println("=======");
            System.out.println("Q: '" + queryStr + "' - FOUND:");
            for (Object obj : result) {
                System.out.println(obj);
            }
            System.out.println("=======");
            System.out.println();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

}
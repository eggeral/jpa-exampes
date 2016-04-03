package edu.campus02.refactoring.step2;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.campus02.refactoring.step2.DomainObjectManager;
import edu.campus02.domain.Animal;
import edu.campus02.domain.DomainObject;
import edu.campus02.domain.Student;

/**
 * Motivation des Refactorings:
 *  - Wir möchten uns selbst um das Persistieren von "Student" Entitäten kümmern und nicht länger
 *    CascadeType.ALL verwenden. 
 *  - Damit das ebenso komfortabel funktioniert wie für "Animal"s sollte der AnimalManager 
 *    entsprechend angepasst werden.
 *  - Man soll diesen angepassten DomainObjectManager für Student und Animal gleich verwenden können. 
 *
 * SCHRITT 2:
 * 
 * AnimalManager:
 *  - Die Methoden für Student und Animal sind praktisch gleich, können aber aufgrund des unterschiedlichen
 *    Typs nicht wiederverwendet werden. 
 *  - Daher: Einführen eines neuen Basistyps für Student und Animal (DomainObject). Dazu reicht ein 
 *    Interface, welches lediglich die Methode "String getId()" definiert. 
 *  - Umstellen der Methoden, die für Student und Animal gleich sind auf DomainObject und Löschen der 
 *    Duplikate. Jetzt reicht eine Methode für beide Klassen, das Resultat ist allerdings ein 
 *    DomainObject und muss gecastet werden.
 *  - Spezialfall Methode findAll, für die der Typ der Entität bekannt sein muss (wollen wir alle Animal-
 *    oder alle Studenten-Entitäten finden). Hier führen wir die Klasse als Parameter ein. 
 *  - Spezialfall Methode find, für die sowohl der Typ der Entität bekannt sein muss, als auch der gesuchte
 *    Primary Key. Zugriff auf beide Informationen haben wir, wenn wir eine Instanz einer Entität als 
 *    Parameter verwenden (also Student oder Animal) anstatt des Strings, für den wir nicht unterscheiden
 *    können, ob wir Animal oder Student suchen.  
 * 
 * Student, Animal   
 *  - Student und Animal implementieren dieses neue Interface DomainObject und liefern in "getId" ihren 
 *    PrimaryKey zurück. Dadurch kann auch "find" für alle DomainObject Instanzen gleich implementiert
 *    werden. 
 *  
 * JpaMain:
 *  - Ablauf wieder gleich wie im Endergebnis (edu.campus02.app.JpaMain), nur wird wesentlich mehr auf 
 *    Ebene von DomainObject gearbeitet, weil man den tatsächlichen Typ vom DomainObjectManager nicht 
 *    zurückbekommt. Bei Einzelergebnissen kann man ganz gut casten, bei Listen ist das allerdings nicht
 *    so leicht möglich. 
 */

public class JpaMain {

	public static void main(String[] args) {
		
		// Eigene DB fürs Refactoring, damit wir am Endergebnis sicher nix ändern ....
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("refactoringDB");
		DomainObjectManager mgr = new DomainObjectManager(factory);

		Animal animal1 = new Animal();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 3, 5);
		animal1.setBirthdate(calendar.getTime());
		animal1.setName("Milia");
		animal1.setRace("Hase");
		animal1.setSpecies("Säugetier");

		Student search_student = new Student();
		search_student.setName("Streber");
		Student student1 = (Student) mgr.find(search_student); // mgr braucht Klasse und Id
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
		
		mgr.persistIfDoesNoExist(animal1);
		System.out.println("created: " + animal1);

		mgr.persistIfDoesNoExist(animal2);
		System.out.println("created: " + animal2);


		// Hier ändert sich nix, weil die Methode ja direkt auf Animal arbeitet.
		List<Animal> animals = mgr.findAllBySpecies("Säugetier");
		for (Animal animal : animals) {
			System.out.println("found:" + animal);
		}

		// Listen casten ist eher blöd, also muss man mit einer List<DomainObject> arbeiten
		List<DomainObject> animal_objects = mgr.findAll(Animal.class);
		for (DomainObject animal : animal_objects) {
			mgr.remove(animal);
			System.out.println("removed: " + animal);
		}

		animal_objects = mgr.findAll(Animal.class);
		System.out.println("Found " + animal_objects.size() + " animals.");
		
		
		// Den Studenten gibt es aber noch
		List<DomainObject> student_objects = mgr.findAll(Student.class);
		for (DomainObject student : student_objects) {
			System.out.println("found:" + student);
		}

 		factory.close();
	}

}

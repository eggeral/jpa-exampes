package edu.campus02.app;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import edu.campus02.domain.Student;

public class JpaMain {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("campusDB");
		EntityManager manager = factory.createEntityManager();
		
				
		Student student = new Student();
		student.setAge(38);
		student.setName("Mair");
		student.setSurname("Martin");

		
		try {
			manager.getTransaction().begin();
			manager.persist(student);
			manager.getTransaction().commit();
	    	System.out.println("Successfully created " + student);
		} catch (RollbackException e) {
			if (e.getCause() instanceof EntityExistsException) {
				System.out.println(student + " already exists");				
			} else {
				e.printStackTrace();
			}
		}

		
		Student read_student;
		read_student = manager.find(Student.class, "Mair");
		System.out.println(read_student); 
		
		manager.close();
		factory.close();
	}

}

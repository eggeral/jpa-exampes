package edu.campus02.app;

import java.sql.SQLException;

import edu.campus02.domain.Student;
import edu.campus02.jdbc.EntityManager;

public class JdbcMain {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		EntityManager manager;
		try {
			manager = new EntityManager();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			manager.createTable();
			System.out.println("Successfully created table STUDENTS");
			
		} catch (SQLException e) {
			if (e.getSQLState().equals("X0Y32")) {
				System.out.println("Table already exists");
			} else {
			    e.printStackTrace();
			}
		}
		
		
		
		Student student = new Student();
		student.setAge(38);
		student.setName("Mair");
		student.setSurname("Martin");
		try {
			manager.persist(student);
			System.out.println("Successfully created " + student);
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				System.out.println(student + " already exists");
			} else {
			    e.printStackTrace();
			}
		}

		
		Student read_student;
		try {
			read_student = manager.find("Mair");
			System.out.println(read_student); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		manager.cleanup();
	}

}

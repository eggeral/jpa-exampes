package edu.campus02.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.campus02.domain.Student;

public class EntityManager {
	
	private Connection connection;
	
	public EntityManager() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
	    connection = DriverManager.getConnection("jdbc:derby:jdbcDB;create=true");
	}

	public void createTable() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				  "CREATE TABLE STUDENTS "
				+ "(name varchar(20) PRIMARY KEY, surname varchar(20), age int)");
		statement.execute();
	}
    // Derby Errorcodes: http://db.apache.org/derby/docs/10.8/ref/rrefexcept71493.html
	public void persist(Student student) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO STUDENTS values(?,?,?)");
		statement.setString(1, student.getName());
		statement.setString(2, student.getSurname());
		statement.setInt(3, student.getAge());
		statement.executeUpdate();		
	}

	public Student find(String name) throws SQLException {
		Student student = null;
		PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM STUDENTS WHERE name = ?");
		statement.setString(1, name);
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			student = new Student();
			student.setName(rs.getString(1));
			student.setSurname(rs.getString(2));
			student.setAge(rs.getInt(3));
		}
		return student;
	}

	public void cleanup() {
		if (null != connection) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

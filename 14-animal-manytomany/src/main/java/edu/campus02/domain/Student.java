package edu.campus02.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Student implements DomainObject {
	
	@Id
	private String name; // PRIMARY KEY
	
	@Basic
	private String surname;
	private Integer age;
	
	@OneToOne(mappedBy = "owner")
	private Animal pet;
	
	public Student() {		
	}
	
	/*
	 * Der "Bequemlichkeits-Konstruktor".
	 */
	public Student(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Animal getPet() {
		return pet;
	}

	public void setPet(Animal pet) {
		this.pet = pet;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", surname=" + surname + ", age="
				+ age + ", pet=" + (pet != null ? pet.getName() : "") + "]";
	}
	@Override
	public String getId() {
		return getName(); // getter verwenden
	}

}

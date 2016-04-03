package edu.campus02.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Animal {
	
	@Id
	private String name; // PRIMARY KEY
	
	private Date birthdate;
	
	private String race;
	private String species;
	
	@OneToOne(cascade= CascadeType.ALL)   // damit wird der Student mit seinem Haustier "verwaltet"
	private Student owner;

	public Student getOwner() {
		return owner;
	}
	public void setOwner(Student owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		String dateStr = "null";
		if (birthdate != null) {
			dateStr = format.format(birthdate);
		}
		return "Animal [name=" + name + ", birthdate=" + dateStr + ", race="
				+ race + ", species=" + species + ", owner=" + owner + "]";
	}	
	
}

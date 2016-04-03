package edu.campus02.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Animal implements DomainObject {
	
	@Id
	private String name; // PRIMARY KEY
	
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	private String race;
	private String species;
	
	// @OneToOne (cascade = CascadeType.REMOVE) // mit mgr.remove(animal) wird auch student mitgelöscht
	// @OneToOne (orphanRemoval = true) // wie CascadeType.REMOVE, zusätzlich löscht ein update auf ein animal mit
    									// animal.setOwner(null) ebenfalls den student      
	@OneToOne  // kein Cascade Type, wir kümmern uns selber um alles
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
	
	@Override
	public String getId() {
		return getName();  // getter verwenden
	}
	
	
}

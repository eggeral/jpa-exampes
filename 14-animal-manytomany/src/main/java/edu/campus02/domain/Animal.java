package edu.campus02.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
	private Species species;
	
	// @OneToOne (cascade = CascadeType.REMOVE) // mit mgr.remove(animal) wird auch student mitgelöscht
	// @OneToOne (orphanRemoval = true) // wie CascadeType.REMOVE, zusätzlich löscht ein update auf ein animal mit
    									// animal.setOwner(null) ebenfalls den student      
	@OneToOne  // kein Cascade Type, wir kümmern uns selber um alles
	private Student owner;
	
	@ManyToMany (fetch = FetchType.EAGER)
	private List<Country> habitat;

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
	public Species getSpecies() {
		return species;
	}
	public void setSpecies(Species species) {
		this.species = species;
	}	
	
	public List<Country> getHabitat() {
		return habitat;
	}
	public void setHabitat(List<Country> habitat) {
		this.habitat = habitat;
	}
	
	public void addHabitat(Country country) {
		if (getHabitat() == null) {
			setHabitat(new ArrayList<Country>());
		}
		getHabitat().add(country);
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		String dateStr = "null";
		if (birthdate != null) {
			dateStr = format.format(birthdate);
		}
		StringBuilder habitatStr = new StringBuilder();
		String delimiter = "";
		habitatStr.append("[");
		for (Country country : getHabitat()) {
			habitatStr.append(delimiter);
			habitatStr.append(country.getName());
			delimiter = ", ";
		}		
		habitatStr.append("]");
		
		return "Animal [name=" + name + ", birthdate=" + dateStr + ", race="
				+ race + ", species=" + (species != null ? species.getName() : null)  + ", owner=" + owner 
				+ ", habitat=" + habitatStr.toString() + "]";
	}
	
	@Override
	public String getId() {
		return getName();  // getter verwenden
	}
	
	
}

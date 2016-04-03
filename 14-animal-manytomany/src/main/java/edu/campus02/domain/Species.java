package edu.campus02.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Species implements DomainObject {

	@Id
	private String name;
	
	private String latin;
	
	// default fetch type: bei "ToOne" eager, bei "ToMany" LAZY vorgeschlagen, kommt auf Implementierung an. 
	@OneToMany (mappedBy = "species")//, fetch = FetchType.EAGER)
	private List<Animal> animals;
	
	public Species() {		
	}
	
	public Species(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public String getLatin() {
		return latin;
	}

	public void setLatin(String latin) {
		this.latin = latin;
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	@Override
	public String getId() {
		return getName();
	}

	@Override
	public String toString() {
		return "Species [name=" + name + ", animals=" + getAnimals() + "]";
	}	
	
	
}

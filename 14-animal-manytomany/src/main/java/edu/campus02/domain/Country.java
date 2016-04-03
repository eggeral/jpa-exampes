package edu.campus02.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Country implements DomainObject {
	
	@Id
	private String name;
	
	@ManyToMany (mappedBy = "habitat", fetch = FetchType.EAGER)
	private List<Animal> animals;

	public Country() {}
	
	public Country(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + ", animals=" + animals + "]";
	}

	@Override
	public String getId() {
		return getName();
	}

}

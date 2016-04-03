package edu.campus02.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Owner {

	@Id
	private String name;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Animal> animals = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}
	
	@Override
	public String toString() {
		return "Owner [name=" + name + ", animals=" + animals + "]";
	}
}

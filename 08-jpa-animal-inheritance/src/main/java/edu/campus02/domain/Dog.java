package edu.campus02.domain;

import javax.persistence.Entity;

@Entity
public class Dog extends Animal {

	private String toy;

	public String getToy() {
		return toy;
	}

	public void setToy(String toy) {
		this.toy = toy;
	}

	@Override
	public String toString() {
		return "Dog [toy=" + toy + "]";
	}

}

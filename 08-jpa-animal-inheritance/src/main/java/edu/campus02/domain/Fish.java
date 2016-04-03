package edu.campus02.domain;

import javax.persistence.Entity;

@Entity
public class Fish extends Animal {

	private String ocean;

	public String getOcean() {
		return ocean;
	}

	public void setOcean(String ocean) {
		this.ocean = ocean;
	}

	@Override
	public String toString() {
		return "Fish [ocean=" + ocean + "]";
	}

	
}

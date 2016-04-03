package edu.campus02.domain;

import java.text.SimpleDateFormat;

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
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		String dateStr = format.format(getBirthdate());

		return "Dog [name=" + getName() + ", birthdate=" + dateStr + ", toy=" + toy   
				+ ", owner=" + getOwner().getName() + "]";

	}

	

}

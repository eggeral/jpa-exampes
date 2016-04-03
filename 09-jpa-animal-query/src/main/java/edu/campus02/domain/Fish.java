package edu.campus02.domain;

import java.text.SimpleDateFormat;

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
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		String dateStr = format.format(getBirthdate());

		return "Fish [name=" + getName() + ", birthdate=" + dateStr + ", ocean=" + ocean   
				+ ", owner=" + getOwner().getName() + "]";
	}

	
	
}

package edu.campus02.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy=InheritanceType.JOINED)
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Animal {
	
	@Id
	private String name; // PRIMARY KEY

	@Temporal(TemporalType.DATE)
	private Date birthdate;
	

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

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY---MM---dd");
		String dateStr = "null";
		if (birthdate != null) {
			dateStr = format.format(birthdate);
		}
		return "Animal [name=" + name + ", birthdate=" + dateStr + "]";
	}

	
}

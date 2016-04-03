package edu.campus02.computershop.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderLine {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Order order;
	@ManyToOne(cascade=CascadeType.ALL)
	private Computer computer;
	private long amount;
	private double price;
		
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Computer getComputer() {
		return computer;
	}
	public void setComputer(Computer computer) {
		this.computer = computer;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "OrderLine [id=" + id + ", order=" + order.getId() + ", computer="
				+ computer + ", amount=" + amount + ", price=" + price + "]";
	}
	
	
	
}

package edu.campus02.computershop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="OrderTable")
public class Order {
	@Id
	@GeneratedValue
	private Long id;
	
	private String address;
	@ManyToOne
	private Customer customer;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)	
	private List<OrderLine> orderLines = new ArrayList<>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	
	@Override
	public String toString() {		
		String customerId = "null";
		if (customer != null) {
			customerId = customer.getId().toString();
		}
		return "Order [id=" + id + ", customer=" + customerId + ", address="
				+ address + ", orderLines=" + orderLines + "]";
	}
	
}

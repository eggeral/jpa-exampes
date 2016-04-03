package edu.campus02.computershop;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.campus02.computershop.domain.Computer;
import edu.campus02.computershop.domain.Customer;
import edu.campus02.computershop.domain.Order;
import edu.campus02.computershop.domain.OrderLine;

public class ComputerShopTest {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("computerShopDb");
		CustomerService customerService = new CustomerService(factory);
		
		Customer c1 = new Customer();
		c1.setAddress("Körblergasse 111, Graz");
		c1.setName("Max Muster");
		
		Order o1 = new Order();
		o1.setAddress("Nibelungengasse 6, Graz");		
		o1.setCustomer(c1);
		c1.getOrders().add(o1);
		
		Computer co1 = new Computer();
		co1.setModel("Macbook Pro");
		
		Computer co2 = new Computer();
		co2.setModel("HP Mini");

		Computer co3 = new Computer();
		co3.setModel("Chrome Book");

		OrderLine ol1 = new OrderLine();
		ol1.setOrder(o1);
		ol1.setComputer(co1);
		ol1.setAmount(1);
		ol1.setPrice(100.0);
		
		OrderLine ol2 = new OrderLine();
		ol2.setOrder(o1);
		ol2.setComputer(co2);
		ol2.setAmount(2);
		ol2.setPrice(60.0);

		OrderLine ol3 = new OrderLine();
		ol3.setOrder(o1);
		ol3.setComputer(co3);
		ol3.setAmount(7);
		ol3.setPrice(120.0);

		o1.getOrderLines().add(ol1);
		o1.getOrderLines().add(ol2);
		o1.getOrderLines().add(ol3);
		
		customerService.addCustomer(c1);		
		System.out.println("Added customer: " + c1);

		c1 = customerService.findCustomerById(c1.getId());
		System.out.println("Found customer: " + c1);
		
		
		customerService.removeCustomer(c1);
		System.out.println("Removed customer: " + c1);

	}
}

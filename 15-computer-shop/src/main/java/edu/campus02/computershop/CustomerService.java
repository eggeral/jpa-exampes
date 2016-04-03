package edu.campus02.computershop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import edu.campus02.computershop.domain.Customer;

public class CustomerService {
	private EntityManagerFactory factory;

	public CustomerService(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public void addCustomer(Customer customer) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.persist(customer);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public void removeCustomer(Customer customer) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			customer = manager.merge(customer);
			manager.getTransaction().begin();
			manager.remove(customer);
			manager.getTransaction().commit();
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	public Customer findCustomerById(Long id) {
		EntityManager manager = null;
		try {
			manager = factory.createEntityManager();
			Customer customer = manager.find(Customer.class, id);
			return customer;
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}
}

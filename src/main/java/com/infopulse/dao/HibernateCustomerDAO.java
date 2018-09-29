package com.infopulse.dao;

import com.infopulse.entity.Customer;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class HibernateCustomerDAO implements CustomerDAO{
    private final SessionFactory sessionFactory;

    public HibernateCustomerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Override
    public void insertCustomer(Customer customer) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateCustomer(Customer customer) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Customer> getAllCustomers() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Customer> result = entityManager.createQuery( "from Customer", Customer.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    @Override
    public void deleteAll(){
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DELETE FROM customers");
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Customer findCustomer(String name, String surename) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Customer customer = entityManager
                .createQuery( "from Customer c where c.name = :name AND c.surename = :surename", Customer.class)
                .setParameter("name", name).setParameter("surename", surename).getResultList().get(0);
        entityManager.getTransaction().commit();
        entityManager.close();
        return customer;

    }
}

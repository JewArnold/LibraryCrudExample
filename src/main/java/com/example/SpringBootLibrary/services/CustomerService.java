package com.example.SpringBootLibrary.services;

import com.example.SpringBootLibrary.model.Book;
import com.example.SpringBootLibrary.model.Customer;
import com.example.SpringBootLibrary.repositories.CustomerRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CustomerService {


    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.stream().findAny().orElse(null);
    }

    @Transactional
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    public void update(int id, Customer customer) {
        customer.setId(id);
        customerRepository.save(customer);
    }

    @Transactional
    public void delete(int id) {
        customerRepository.deleteById(id);
    }

    public List<Book> bookList(int id) {
        Customer customer = findById(id);
        Hibernate.initialize(customer.getBooks());

        return customer.getBooks();
    }

    public Optional<Customer> findByName(String name) {
        return customerRepository.findCustomerByName(name);
    }
}

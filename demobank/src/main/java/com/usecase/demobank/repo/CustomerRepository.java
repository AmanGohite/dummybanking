package com.usecase.demobank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usecase.demobank.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	List<Customer> findAll();

	List<Customer> findByCustomerType(String status);

	List<Customer> findAllByCustomerTypeAndAccountBalanceGreaterThan(String string, Double i);

	List<Customer> findAllByCustomerTypeAndAccountBalanceLessThan(String string, Double i);

	List<Customer> findAllByCustomerType(String string);

}

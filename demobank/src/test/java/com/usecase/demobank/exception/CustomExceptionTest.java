package com.usecase.demobank.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.usecase.demobank.model.TransactionDTO;
import com.usecase.demobank.repo.CustomerRepository;
import com.usecase.demobank.repo.TransactionRepository;
import com.usecase.demobank.service.CustomerServiceImpl;

public class CustomExceptionTest {
	
	@Mock
	CustomerRepository customerRepo;

	@Mock
	TransactionRepository transactionRepo;

	@Mock
	CustomerServiceImpl service;
	
	@Test
	public void customerNotFoundExceptionTest() {
//		TransactionDTO transaction = new TransactionDTO();
//		transaction.setCustomerId(100);
//		CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,()->{service.debitAmount(transaction);});
//		assertEquals("customer not found",exception.getMessage());
//		
	    }
	}



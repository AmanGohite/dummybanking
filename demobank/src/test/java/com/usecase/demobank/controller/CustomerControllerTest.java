package com.usecase.demobank.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import com.usecase.demobank.model.Customer;
import com.usecase.demobank.model.Transaction;
import com.usecase.demobank.model.TransactionDTO;
import com.usecase.demobank.model.TransactionResponse;
import com.usecase.demobank.service.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

	@Mock
	CustomerServiceImpl service;

	@InjectMocks
	CustomerController controller;
	
	@Mock
	KafkaTemplate<String, List<String>> kafkaJsonTemplate;

	static Customer customer;

	static TransactionDTO transactionDto;

	static TransactionResponse resp;

	static String date1 = "9999-01-01";
	static String date2 = "9999-02-02";

	@Test
	void creditAmountTest() {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAmount(1000.0);
		transaction.setCustomerId(1);
		transaction.setType("credit");
		resp = new TransactionResponse("success", 0.0);
		when(service.depositAmount(transaction)).thenReturn(resp);
		ResponseEntity<TransactionResponse> customerTest = controller.creditAmount(transaction);
		verify(service).depositAmount(transaction);
		assertEquals(resp.getMessage(), customerTest.getBody().getMessage());
	}

	@Test
	void debitAmountTest() {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAmount(1000.0);
		transaction.setCustomerId(1);
		transaction.setType("debit");
		resp = new TransactionResponse("success", 0.0);
		when(service.debitAmount(transaction)).thenReturn(resp);
		ResponseEntity<TransactionResponse> customerTest = controller.debitAmount(transaction);
		verify(service).debitAmount(transaction);
		assertEquals(resp.getMessage(), customerTest.getBody().getMessage());
	}

	@Test
	void showPriorityCustomerTest() {
		java.util.List<Customer> customer = new ArrayList<>();
		when(service.showPriorityCustomer()).thenReturn(customer);
		ResponseEntity<java.util.List<Customer>> customerTest = controller.showPriorityCustomer();
		verify(service).showPriorityCustomer();
		assertEquals(customer, customerTest.getBody());
	}

	@Test
	void getAllTransactionInDateRangeTest() {
		java.util.List<Transaction> transaction = new ArrayList<>();
		when(service.getAllTransactionInDateRange(date1, date2)).thenReturn(transaction);
		ResponseEntity<List<Transaction>> transactionTest = controller.getAllTransactionInDateRange(date1, date2);
		verify(service).getAllTransactionInDateRange(date1, date2);
		assertEquals(transaction, transactionTest.getBody());
	}
	
	@Test
	void postPriorityCustomerToKafkaTest() {
		java.util.List<String> customer = new ArrayList<>();
		when(service.publishPriorityCustomerNames()).thenReturn(customer);
		controller.postPriorityCustomerToKafka();
		verify(service).publishPriorityCustomerNames();
		java.util.List<String> dummy = new ArrayList<>();
		assertEquals(dummy,customer);
	}

}

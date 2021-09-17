package com.usecase.demobank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usecase.demobank.model.Customer;
import com.usecase.demobank.model.Transaction;
import com.usecase.demobank.model.TransactionDTO;
import com.usecase.demobank.model.TransactionResponse;
import com.usecase.demobank.repo.CustomerRepository;
import com.usecase.demobank.repo.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepo;

	@Mock
	TransactionRepository transactionRepo;

	@InjectMocks
	CustomerServiceImpl service;

	static Customer customer = new Customer();

	static TransactionDTO transactionDto = new TransactionDTO(1, "dummy", 1.0);

	static TransactionResponse resp;

	static String date1 = "9999-01-01";
	static String date2 = "9999-02-02";

	LocalDate from = LocalDate.parse(date1);
	LocalDate to = LocalDate.parse(date2);

	static final String PRIORITY_STRING = "priority";

	@Test
	void showPriorityCustomerTest() {
		java.util.List<Customer> priorityCustomerTest = new ArrayList<>();
		when(customerRepo.findByCustomerType(PRIORITY_STRING)).thenReturn(priorityCustomerTest);
		java.util.List<Customer> dummy = service.showPriorityCustomer();
		verify(customerRepo).findByCustomerType(PRIORITY_STRING);
		assertEquals(priorityCustomerTest, dummy);
	}

	@Test
	void getAllTransactionInDateRangeTest() {
		java.util.List<Transaction> transaction = new ArrayList<>();
		when(transactionRepo.findAllByTransactionDateBetween(from, to)).thenReturn(transaction);
		List<Transaction> transactionTest = service.getAllTransactionInDateRange(date1, date2);
		verify(transactionRepo).findAllByTransactionDateBetween(from, to);
		assertEquals(transaction, transactionTest);
	}

//	@Test
//	void depositAmountTest() {
//		Transaction transaction = new Transaction(transactionDto.getCustomerId(), transactionDto.getType(),
//				transactionDto.getAmount());
//		when(transactionRepo.save(transaction)).thenReturn(transaction);
//		TransactionResponse response = new TransactionResponse();
//		response.setMessage("dummy");
//		assertEquals(response.getMessage(), transaction.getType());
//	}

	@Test
	void publishPriorityCustomerNamesTest() {
		List<Customer> priorityCustomerTest = new ArrayList<>();
		when(customerRepo.findAllByCustomerType("priority")).thenReturn(priorityCustomerTest);
		List<String> dummy = service.publishPriorityCustomerNames();
		verify(customerRepo).findAllByCustomerType("priority");
		assertEquals(dummy, priorityCustomerTest);

	}
	
	@Test
	void createNonPriorityCustomerTest() {
		List<Customer> NonpriorityCustomerTest = new ArrayList<>();
		when(customerRepo.findAllByCustomerTypeAndAccountBalanceLessThan("priority", 10000.0)).thenReturn(NonpriorityCustomerTest);
		service.createNonPriorityCustomer();
		List<Customer> dummy = new ArrayList<>();
		verify(customerRepo).findAllByCustomerTypeAndAccountBalanceLessThan("priority",10000.0);
		assertEquals(dummy, NonpriorityCustomerTest);
	}
	
	@Test
	void createPriorityCustomerTest() {
		List<Customer> priorityCustomerTest = new ArrayList<>();
		when(customerRepo.findAllByCustomerTypeAndAccountBalanceGreaterThan("regular", 10000.0)).thenReturn(priorityCustomerTest);
		service.createPriorityCustomer();
		List<Customer> dummy = new ArrayList<>();
		verify(customerRepo).findAllByCustomerTypeAndAccountBalanceGreaterThan("regular",10000.0);
		assertEquals(dummy, priorityCustomerTest);

	}
}

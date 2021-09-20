package com.usecase.demobank.exception;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.usecase.demobank.model.Customer;
import com.usecase.demobank.model.TransactionDTO;
import com.usecase.demobank.repo.CustomerRepository;
import com.usecase.demobank.service.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomExceptionTest {

	@InjectMocks
	CustomerServiceImpl service;

	@Mock
	CustomerRepository customerRepo;

	@Test
	public void minimumBalanceTest() {
		TransactionDTO transaction = new TransactionDTO(1, "dummy", 1000.0);
		Customer cust = new Customer();
		cust.setCustomerId(1);
		cust.setAccountBalance(1000.0);
		cust.setCustomerName("dummy");
		cust.setCustomerType("dummy");
		;
		when(customerRepo.findById(transaction.getCustomerId())).thenReturn(Optional.of(cust));
		Double debitAmount = 1000.0;
		Double remainingBal = (cust.getAccountBalance() - debitAmount);
		if (remainingBal <= 500.0) {
			MinimumBalanceException e = assertThrows(MinimumBalanceException.class, () -> {
				service.debitAmount(transaction);
			});
			assertEquals("minimum balance has to be maintained", e.getMessage());
		}
	}

	@Test
	public void customerNotFoundTest() {
		TransactionDTO transaction = new TransactionDTO(1000, "dummy", 1000.0);
		Optional<Customer> cust = Optional.ofNullable(null);

		when(customerRepo.findById(transaction.getCustomerId())).thenReturn((cust));
		if (!(cust.isPresent())) {
			CustomerNotFoundException e = assertThrows(CustomerNotFoundException.class, () -> {
				service.debitAmount(transaction);
			});
			CustomerNotFoundException e1 = assertThrows(CustomerNotFoundException.class, () -> {
				service.depositAmount(transaction);
			});
			assertEquals("Customer not found in application", e.getMessage());
			assertEquals("Customer not found in application", e1.getMessage());

		}

	}

}

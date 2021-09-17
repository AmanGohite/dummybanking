package com.usecase.demobank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usecase.demobank.exception.CustomerNotFoundException;
import com.usecase.demobank.exception.MinimumBalanceException;
import com.usecase.demobank.model.Customer;
import com.usecase.demobank.model.Transaction;
import com.usecase.demobank.model.TransactionDTO;
import com.usecase.demobank.model.TransactionResponse;
import com.usecase.demobank.repo.CustomerRepository;
import com.usecase.demobank.repo.TransactionRepository;

import ch.qos.logback.classic.Logger;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	TransactionRepository transactionRepo;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	static final Double PRIORITY_BALANCE = 10000.0;

	static final String PRIORITY_STRING = "priority";

	@Override
	public TransactionResponse depositAmount(TransactionDTO transaction) {
		TransactionResponse response = new TransactionResponse();

		Optional<Customer> cust = customerRepo.findById(transaction.getCustomerId());
		if (!(cust.isPresent())) {
			throw new CustomerNotFoundException("Customer not found in application");
		}

		Customer customer = cust.get();

		if (transaction.getAmount() > 0) {
			logger.info("inside crediting logic");
			Double currentBalance = customer.getAccountBalance();
			Double updatedBalance = (currentBalance + transaction.getAmount());
			customer.setAccountBalance(updatedBalance);
			customerRepo.save(customer);
			Transaction trans = new Transaction(transaction.getCustomerId(), transaction.getType(),
					transaction.getAmount());
			transactionRepo.save(trans);
			response.setMessage("successfully credit amount to account");
			response.setUpdatedBalance(updatedBalance);
			return response;
		}

		return response;
	}

	@Override
	public TransactionResponse debitAmount(TransactionDTO transaction) {
		TransactionResponse response = new TransactionResponse();

		Optional<Customer> cust = customerRepo.findById(transaction.getCustomerId());
		if (!cust.isPresent()) {
			throw new CustomerNotFoundException("Customer not found in application");
		}

		Customer customer = cust.get();

		logger.info("inside debiting logic");
		if (customer.getAccountBalance() - transaction.getAmount() < 500) {
			throw new MinimumBalanceException("minimum balance has to be maintained");
		}

		Double currentBalance = customer.getAccountBalance();
		Double updatedBalance = (currentBalance - transaction.getAmount());
		customer.setAccountBalance(updatedBalance);
		Transaction trans = new Transaction(transaction.getCustomerId(), transaction.getType(),
				transaction.getAmount());
		customerRepo.save(customer);
		transactionRepo.save(trans);
		response.setMessage("successfully withdrawn amount from account");
		response.setUpdatedBalance(updatedBalance);
		return response;

	}

	@Override
	public List<Customer> showPriorityCustomer() {

		return customerRepo.findByCustomerType(PRIORITY_STRING);

	}

	@Override
	public List<Transaction> getAllTransactionInDateRange(String fromDate, String toDate) {
		LocalDate from = LocalDate.parse(fromDate);
		LocalDate to = LocalDate.parse(toDate);
		return transactionRepo.findAllByTransactionDateBetween(from, to);

	}

	public void createPriorityCustomer() {
		List<Customer> priority = customerRepo.findAllByCustomerTypeAndAccountBalanceGreaterThan("regular",
				PRIORITY_BALANCE);
		priority.forEach(c -> c.setCustomerType(PRIORITY_STRING));
		customerRepo.saveAll(priority);
	}

	public void createNonPriorityCustomer() {
		List<Customer> nonPriority = customerRepo.findAllByCustomerTypeAndAccountBalanceLessThan(PRIORITY_STRING,
				PRIORITY_BALANCE);
		nonPriority.forEach(c -> c.setCustomerType("regular"));
		customerRepo.saveAll(nonPriority);
	}

	@Override
	public List<String> publishPriorityCustomerNames() {
		this.createPriorityCustomer();
		this.createNonPriorityCustomer();
			List<Customer> priorityCustomer = customerRepo.findAllByCustomerType(PRIORITY_STRING);
		return priorityCustomer.stream().map(Customer::getCustomerName).collect(Collectors.toList());

	}

}

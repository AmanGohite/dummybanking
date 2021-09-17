package com.usecase.demobank.service;

import java.util.List;

import com.usecase.demobank.model.Customer;
import com.usecase.demobank.model.Transaction;
import com.usecase.demobank.model.TransactionDTO;
import com.usecase.demobank.model.TransactionResponse;

public interface CustomerService {

	TransactionResponse depositAmount(TransactionDTO transaction);

	TransactionResponse debitAmount(TransactionDTO transaction);

	List<Customer> showPriorityCustomer();

	List<Transaction> getAllTransactionInDateRange(String fromDate, String toDate);

	List<String> publishPriorityCustomerNames();

}

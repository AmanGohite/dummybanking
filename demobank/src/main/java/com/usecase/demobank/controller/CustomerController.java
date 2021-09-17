package com.usecase.demobank.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usecase.demobank.model.Customer;
import com.usecase.demobank.model.Transaction;
import com.usecase.demobank.model.TransactionDTO;
import com.usecase.demobank.model.TransactionResponse;
import com.usecase.demobank.service.CustomerService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping(value = "/api")
public class CustomerController {

	@Autowired
	@Qualifier("customerService")
	CustomerService service;

	@Autowired
	KafkaTemplate<String, List<String>> kafkaJsonTemplate;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	static final String TOPIC_NAME = "priority-customer";

	@PostMapping(value = "/credit")
	public ResponseEntity<TransactionResponse> creditAmount(@Valid @RequestBody TransactionDTO transaction) {
		return new ResponseEntity<>(service.depositAmount(transaction), HttpStatus.OK);
	}

	@PostMapping(value = "/debit")
	public ResponseEntity<TransactionResponse> debitAmount(@Valid @RequestBody TransactionDTO transaction) {
		return new ResponseEntity<>(service.debitAmount(transaction), HttpStatus.OK);
	}

	@GetMapping(value = "/prioritized")
	public ResponseEntity<List<Customer>> showPriorityCustomer() {
		return new ResponseEntity<>(service.showPriorityCustomer(), HttpStatus.OK);
	}

	@GetMapping(value = "/transaction/{from}/{to}")
	public ResponseEntity<List<Transaction>> getAllTransactionInDateRange(@PathVariable("from") String from,
			@PathVariable("to") String to) {
		return new ResponseEntity<>(service.getAllTransactionInDateRange(from, to), HttpStatus.OK);

	}

	@Scheduled(cron = "0 0/2 * * * ?")
	public void postPriorityCustomerToKafka() {
		List<String> priorityCustomerList =service.publishPriorityCustomerNames();
		kafkaJsonTemplate.send(TOPIC_NAME, priorityCustomerList);
		logger.info("message published");
	}
}

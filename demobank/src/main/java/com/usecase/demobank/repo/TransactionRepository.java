package com.usecase.demobank.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usecase.demobank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findAllByTransactionDateBetween(LocalDate fromDate, LocalDate toDate);

}

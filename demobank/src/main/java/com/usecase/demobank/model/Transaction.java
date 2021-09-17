package com.usecase.demobank.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transaction_id")
	private Integer transactionId;

	@Column(name = "customer_id")
	private Integer customerId;

	private String type;

	@Min(value = 1L, message = "The amount should be greater than 1")
	private Double amount;

	@CreationTimestamp
	private LocalDate transactionDate;

	public Transaction(Integer customerId, String type, Double amount) {
		super();
		this.customerId = customerId;
		this.type = type;
		this.amount = amount;
	}

}

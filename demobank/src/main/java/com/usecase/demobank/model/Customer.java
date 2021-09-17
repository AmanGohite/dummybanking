package com.usecase.demobank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

	@Column(name = "customer_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "account_balance")
	private Double accountBalance;

	@Column(name = "customer_type")
	private String customerType;

}

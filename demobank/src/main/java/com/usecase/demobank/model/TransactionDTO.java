package com.usecase.demobank.model;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

	private Integer customerId;

	private String type;

	@Min(value = 1L, message = "The amount should be greater than 1")
	private Double amount;

	public TransactionDTO(int customerId, String type, double amount) {
		super();
		this.customerId = customerId;
		this.type = type;
		this.amount = amount;
	}

}

package com.usecase.demobank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionResponse {

	private String message;
	private Double updatedBalance;

	public TransactionResponse(String message, Double updatedBalance) {
		super();
		this.message = message;
		this.updatedBalance = updatedBalance;
	}

}

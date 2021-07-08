package com.file.handler.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Transaction {
	private final Long id;
	private final Long userId;
	private final boolean isBought;
	private final String currency;
	private final BigDecimal amount;
	private final Date transactionTime;
}

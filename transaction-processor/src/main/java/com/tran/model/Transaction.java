package com.tran.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@JsonDeserialize(builder = Transaction.Builder.class)
public class Transaction {

  private final Long id;
  private final Long userId;
  private final boolean isBought;
  private final String currency;
  private final BigDecimal amount;
  private final Date transactionTime;
  private final String type;

  public Transaction(Long id, Long userId, boolean isBought, String currency, BigDecimal amount, String type) {
    this.id = id;
    this.userId = userId;
    this.isBought = isBought;
    this.currency = currency;
    this.amount = amount;
    this.transactionTime = new Date();
    this.type = type;
  }

  public static class Builder {
    private Long id;
    private Long userId;
    private boolean isBought;
    private String currency;
    private BigDecimal amount;
    private Date transactionTime;
    private String type;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder userId(Long userId) {
      this.userId = userId;
      return this;
    }

    public Builder bought(boolean bought) {
      isBought = bought;
      return this;
    }

    public Builder currency(String currency) {
      this.currency = currency;
      return this;
    }

    public Builder amount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder transactionTime(Date transactionTime) {
      this.transactionTime = transactionTime;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }
  }
}

package com.file.handler.converter;

import com.file.handler.model.Transaction;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.sql.Date;

import static com.file.handler.utils.Constants.*;

public class Converter {

  public static Transaction toTransaction(CSVRecord csvRecord) {
    String id = csvRecord.get(TRANSACTION_ID);
    String userId = csvRecord.get(TRANSACTION_USER_ID);
    String isBought = csvRecord.get(TRANSACTION_IS_BOUGHT);
    String currency = csvRecord.get(TRANSACTION_CURRENCY);
    String amount = csvRecord.get(TRANSACTION_AMOUNT);
    String transactionTime = csvRecord.get(TRANSACTION_TRANSACTION_TIME);

    return Transaction.builder().id(Long.valueOf(id))
            .userId(Long.valueOf(userId))
            .isBought(Boolean.valueOf(isBought))
            .currency(currency)
            .amount(new BigDecimal(amount))
            .transactionTime(Date.valueOf(transactionTime)).build();
  }
}

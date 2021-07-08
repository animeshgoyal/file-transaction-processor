package com.tran.rules;

import com.tran.downstream.systems.DownstreamMessageSender;
import com.tran.model.Transaction;
import lombok.AllArgsConstructor;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.mvel.MVELRule;

import java.util.List;

import static com.tran.utils.Constants.TRANSACTION_FACT_NAME;

@AllArgsConstructor
public class TransactionProcessingRule extends MVELRule {

  private final List<String> downstreamSystemNames;
  private final DownstreamMessageSender downstreamMessageSender;

  @Override
  public void execute(Facts facts) throws Exception {
    Transaction transaction = (Transaction) facts.get(TRANSACTION_FACT_NAME);
    downstreamSystemNames.forEach(systemName -> {
      downstreamMessageSender.send(systemName, transaction);
    });
  }
}

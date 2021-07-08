package com.tran.message.handler;

import com.tran.model.Transaction;
import lombok.AllArgsConstructor;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import static com.tran.utils.Constants.TRANSACTION_FACT_NAME;

@AllArgsConstructor
public class MessageHandlerRunnable implements Runnable {

  private final Transaction transaction;
  private final Rules rules;
  private final RulesEngine rulesEngine;

  @Override
  public void run() {
    Facts facts = new Facts();
    facts.put(TRANSACTION_FACT_NAME, transaction);
    rulesEngine.fire(rules, facts);
  }
}

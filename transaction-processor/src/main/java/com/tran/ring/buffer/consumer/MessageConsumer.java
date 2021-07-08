package com.tran.ring.buffer.consumer;

import com.tran.ring.buffer.objects.MessageObject;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.tran.metrics.TPCustomerReportMetricsStore.timer;
import static com.tran.utils.Constants.TRANSACTION_FACT_NAME;
import static com.tran.utils.MetricUtils.MESSAGE_CONSUMER_TIMER;

@Component("messageConsumer")
@Slf4j
public class MessageConsumer implements IObjectConsumer<MessageObject> {

  private final Rules rules;
  private final RulesEngine rulesEngine;

  public MessageConsumer(Rules rules,
                         RulesEngine rulesEngine) {
    this.rules = rules;
    this.rulesEngine = rulesEngine;
  }

  @Override
  public void handle(MessageObject messageObject) {
    Timer timer = timer(MESSAGE_CONSUMER_TIMER);
    long start = System.currentTimeMillis();
    Facts facts = new Facts();
    facts.put(TRANSACTION_FACT_NAME, messageObject.getTransaction());
    rulesEngine.fire(rules, facts);
    timer.record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
  }
}

package com.tran.downstream.systems;

import com.tran.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import static com.tran.metrics.TPCustomerReportMetricsStore.counter;
import static com.tran.utils.MetricUtils.DS_FILE_MESSAGE_COUNTER;

@Getter
@AllArgsConstructor
@Slf4j
public class FileHandler implements DownstreamMessageHandler {

  private final String name;
  private final String fileLocation;
  private final String fileName;

  @Override
  public void setup() {
    DownStreamSystemConfig.getInstance().register(getName(), this);
  }

  @Override
  public void send(Transaction transaction) {
    log.info("Message delivery to file handler");
    counter(DS_FILE_MESSAGE_COUNTER).increment();
  }
}

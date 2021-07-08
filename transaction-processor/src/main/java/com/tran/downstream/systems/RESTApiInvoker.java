package com.tran.downstream.systems;

import com.tran.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import static com.tran.metrics.TPCustomerReportMetricsStore.counter;
import static com.tran.utils.MetricUtils.DS_REST_MESSAGE_COUNTER;

@Getter
@AllArgsConstructor
@Slf4j
public class RESTApiInvoker implements DownstreamMessageHandler {

  private final String name;
  private final String url;
  private final HttpMethod method;

  @Override
  public void setup() {
    DownStreamSystemConfig.getInstance().register(getName(), this);
  }

  @Override
  public void send(Transaction transaction) {
    log.info("Message delivery to REST endpoint");
    counter(DS_REST_MESSAGE_COUNTER).increment();
  }
}

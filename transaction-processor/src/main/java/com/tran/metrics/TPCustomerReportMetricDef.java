package com.tran.metrics;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.function.BiFunction;

public class TPCustomerReportMetricDef<T extends Meter> {

  private final String metricName;
  private final BiFunction<String, MeterRegistry, T> producer;

  public TPCustomerReportMetricDef(String metricName, BiFunction<String, MeterRegistry, T> producer) {
    this.metricName = metricName;
    this.producer = producer;
  }

  String getMetricName() {
    return metricName;
  }

  BiFunction<String, MeterRegistry, T> getProducer() {
    return producer;
  }
}

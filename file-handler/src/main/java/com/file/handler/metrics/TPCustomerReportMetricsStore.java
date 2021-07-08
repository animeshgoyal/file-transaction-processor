package com.file.handler.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TPCustomerReportMetricsStore {
  private static MeterRegistry meterRegistry;
  private static final ConcurrentMap<Id, Meter> metrics = new ConcurrentHashMap<>();

  public static TPCustomerReportMetricsStore getInstance(MeterRegistry meterRegistryInstance) {
    return new TPCustomerReportMetricsStore(meterRegistryInstance);
  }

  private TPCustomerReportMetricsStore(MeterRegistry meterRegistryInstance) {
    TPCustomerReportMetricsStore.setMeterRegistry(meterRegistryInstance);
  }

  private static void setMeterRegistry(MeterRegistry meterRegistry) {
    if(TPCustomerReportMetricsStore.meterRegistry == null) {
      TPCustomerReportMetricsStore.meterRegistry = meterRegistry;
    }
  }

  public static <T extends Meter> T createOrGet(TPCustomerReportMetricDef<T> metricDef) {
    Meter meter = metricDef.getProducer().apply(metricDef.getMetricName(), meterRegistry);
    Id id = meter.getId();
    return (T) metrics.computeIfAbsent(id, s -> meter);
  }

  public static Timer timer(String metricName) {
    return TPCustomerReportMetricsStore.createOrGet(new TPCustomerReportMetricDef<>(
      metricName, (name, registry) -> Timer.builder(name).register(registry)));
  }

  public static Counter counter(String metricName) {
    return TPCustomerReportMetricsStore.createOrGet(new TPCustomerReportMetricDef<>(
            metricName, (name, registry) -> Counter.builder(name).register(registry)));
  }
}

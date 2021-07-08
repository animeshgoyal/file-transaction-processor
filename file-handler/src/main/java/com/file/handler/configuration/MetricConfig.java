package com.file.handler.configuration;

import com.file.handler.metrics.TPCustomerReportMetricsStore;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfig {

  @Bean
  public MeterRegistry meterRegistry() {
    return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
  }

  @Bean
  public TPCustomerReportMetricsStore metricsStore(MeterRegistry meterRegistry) {
    return TPCustomerReportMetricsStore.getInstance(meterRegistry);
  }
}

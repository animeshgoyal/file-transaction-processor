package com.file.handler.file.strategy.csv;

import com.file.handler.file.processor.csv.CSVFileProcessor;
import com.file.handler.file.strategy.FileHandlerStrategy;
import com.file.handler.file.strategy.FileHandlerStrategyFactory;
import com.file.handler.utils.Constants;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.Executor;

import static com.file.handler.metrics.TPCustomerReportMetricsStore.counter;
import static com.file.handler.utils.MetricUtils.CSV_FILE_RECEIVED_COUNTER;

@Component
public class CSVFileHandlerStrategyImpl implements FileHandlerStrategy {
  private final Executor fileHandlerExecutor;
  private final String type = Constants.CSV_FILE_EXTENSION;

  @Autowired
  public CSVFileHandlerStrategyImpl(Executor fileHandlerExecutor) {
    this.fileHandlerExecutor = fileHandlerExecutor;
  }

  @PostConstruct
  public void init() {
    FileHandlerStrategyFactory.getInstance().registerStrategy(type, this);
  }

  @Override
  public void handleFile(File csvFile) {
    Counter counter = counter(CSV_FILE_RECEIVED_COUNTER);
    try {
      fileHandlerExecutor.execute(new CSVFileProcessor(csvFile));
    } finally {
      counter.increment();
    }
  }

  @Override
  public String toString() {
    return "CSVFileHandlerStrategyImpl{" +"type=" + type +'}';
  }
}

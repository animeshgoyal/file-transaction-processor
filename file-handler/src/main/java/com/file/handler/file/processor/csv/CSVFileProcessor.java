package com.file.handler.file.processor.csv;

import com.file.handler.file.strategy.DataTypeFileHandlerStrategy;
import com.file.handler.file.strategy.DataTypeFileHandlerStrategyFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static com.file.handler.metrics.TPCustomerReportMetricsStore.timer;
import static com.file.handler.metrics.TPCustomerReportMetricsStore.counter;
import static com.file.handler.utils.MetricUtils.*;

@Slf4j
public class CSVFileProcessor implements Runnable {
  private final File csvFile;

  public CSVFileProcessor(File csvFile) {
    this.csvFile = csvFile;
  }

  @Override
  public void run() {
    timer(CSV_FILE_PROCESS_TIMER).record(() -> {
      DataTypeFileHandlerStrategy strategy = DataTypeFileHandlerStrategyFactory.getInstance().getDataTypeFileHandlerStrategy(csvFile);
      if (strategy != null) {
        strategy.handleFile(csvFile);
        counter(CSV_FILE_ACCEPTED_COUNTER).increment();
      } else {
        counter(CSV_FILE_REJECTED_COUNTER).increment();
      }
    });
  }
}

package com.file.handler.file.strategy;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public final class DataTypeFileHandlerStrategyFactory {
  private static final DataTypeFileHandlerStrategyFactory factory = new DataTypeFileHandlerStrategyFactory();
  private List<DataTypeFileHandlerStrategy> dataTypeFileHandlerStrategyList = new ArrayList<>();
  private final ReentrantLock lock = new ReentrantLock();

  private DataTypeFileHandlerStrategyFactory() {
  }

  public static DataTypeFileHandlerStrategyFactory getInstance() {
    return factory;
  }

  public DataTypeFileHandlerStrategy getDataTypeFileHandlerStrategy(File file) {
    String name = file.getName();
    log.info("Processing file with name {}",name);
    for(DataTypeFileHandlerStrategy strategy : dataTypeFileHandlerStrategyList) {
      log.info("Matching with pattern {} ", strategy.getFilePattern());
      if(strategy.getFilePattern().matcher(name).matches()) {
        return strategy;
      }
    }
    log.warn("Unable to match file name '{}' with any of the strategy patterns.",name);
    return null;
  }

  public void registerStrategy(DataTypeFileHandlerStrategy strategy) {
    lock.lock();
    try {
      dataTypeFileHandlerStrategyList.add(strategy);
    } finally {
      lock.unlock();
    }
  }
}

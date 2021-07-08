package com.file.handler.file.strategy;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public final class FileHandlerStrategyFactory {
  private static final FileHandlerStrategyFactory factory = new FileHandlerStrategyFactory();
  private Map<String, FileHandlerStrategy> fileHandlerStrategyMap = new ConcurrentHashMap<>();

  private FileHandlerStrategyFactory() {
  }

  public static FileHandlerStrategyFactory getInstance() {
    return factory;
  }

  public FileHandlerStrategy getFileHandlerStrategy(File file) throws IOException {
    String type = Files.probeContentType(file.toPath());
    log.debug("Determined type '{}' for the file '{}'", type, file.toPath());
    FileHandlerStrategy fileHandlerStrategy = fileHandlerStrategyMap.get(type.toLowerCase());
    if(fileHandlerStrategy != null) {
      log.info("Strategy {} selected to handle file with name {} and type {}", fileHandlerStrategy, file.getName(), type);
    } else {
      log.warn("Strategy not defined for the file {}", file.getName());
    }
    return fileHandlerStrategy;
  }

  public void registerStrategy(String type, FileHandlerStrategy strategy) {
    fileHandlerStrategyMap.putIfAbsent(type.toLowerCase(), strategy);
  }
}

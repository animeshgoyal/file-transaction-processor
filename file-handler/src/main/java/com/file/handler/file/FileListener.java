package com.file.handler.file;

import com.file.handler.file.strategy.FileHandlerStrategy;
import com.file.handler.file.strategy.FileHandlerStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static com.file.handler.metrics.TPCustomerReportMetricsStore.counter;
import static com.file.handler.utils.MetricUtils.FILE_RECEIVED_COUNTER;
import static com.file.handler.utils.MetricUtils.FILE_REJECTED_COUNTER;

@Slf4j
public class FileListener implements FileChangeListener {

  @Override
  public void onChange(Set<ChangedFiles> changeSet) {
    for (ChangedFiles changedFiles : changeSet) {
      for (ChangedFile changedfile : changedFiles.getFiles()) {
        if (changedfile.getType().equals(ChangedFile.Type.ADD)) {
          addFile(changedfile);
        }
      }
    }
  }

  private void addFile(ChangedFile changedfile) {
    boolean fileAccepted = false;
    File file = changedfile.getFile();
    try {
      FileHandlerStrategy fileHandlerStrategy = FileHandlerStrategyFactory.getInstance().getFileHandlerStrategy(file);
      if (fileHandlerStrategy != null) {
        fileHandlerStrategy.handleFile(file);
        fileAccepted = true;
      }
    } catch (IOException ex) {
      log.error("Unable to determine the extension of the file '{}'", file.getPath(), ex);
    } finally {
      counter(fileAccepted ? FILE_RECEIVED_COUNTER : FILE_REJECTED_COUNTER).increment();
    }
  }
}

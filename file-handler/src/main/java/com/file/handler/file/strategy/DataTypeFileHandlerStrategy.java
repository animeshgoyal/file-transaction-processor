package com.file.handler.file.strategy;

import java.io.File;
import java.util.regex.Pattern;

public interface DataTypeFileHandlerStrategy {
  void handleFile(File csvFile);
  Pattern getFilePattern();
}

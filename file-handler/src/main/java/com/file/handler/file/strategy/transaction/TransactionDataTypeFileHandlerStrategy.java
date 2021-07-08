package com.file.handler.file.strategy.transaction;

import com.file.handler.converter.Converter;
import com.file.handler.file.strategy.DataTypeFileHandlerStrategy;
import com.file.handler.file.strategy.DataTypeFileHandlerStrategyFactory;
import com.file.handler.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.regex.Pattern;

@Component
@Slf4j
public class TransactionDataTypeFileHandlerStrategy implements DataTypeFileHandlerStrategy {

  private final Pattern pattern;

  public TransactionDataTypeFileHandlerStrategy(@Value("${transaction.csv.file.format}") String fileFormat) {
    pattern = Pattern.compile(fileFormat);
  }

  @PostConstruct
  public void init() {
    DataTypeFileHandlerStrategyFactory.getInstance().registerStrategy(this);
  }

  @Override
  public void handleFile(File csvFile) {
    try (Reader reader = Files.newBufferedReader(csvFile.toPath());
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT, 0L, 10)) {
      for (CSVRecord csvRecord : csvParser) {
        log.info("csvRecord {}", csvRecord);
        Transaction transaction = Converter.toTransaction(csvRecord);
      }
    } catch (IOException ex) {
      log.error("Count not process file with path {}", csvFile.getPath(), ex);
    }
  }

  @Override
  public Pattern getFilePattern() {
    return pattern;
  }
}

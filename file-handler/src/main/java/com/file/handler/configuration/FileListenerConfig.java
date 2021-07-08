package com.file.handler.configuration;

import com.file.handler.file.FileListener;
import com.file.handler.threadpool.TPThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.Executor;

@Configuration
@Slf4j
public class FileListenerConfig {

  private static final int AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();
  private static final int KEEP_ALIVE_SECONDS = 10;

  @Autowired
  private Environment env;

  @Bean
  public FileSystemWatcher fileSystemWatcher(@Value("${file.source.directory.path}") String fileSourceDirectoryPath) {
    FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true,
            Duration.ofMillis(5000L), Duration.ofMillis(3000L));
    fileSystemWatcher.addSourceDirectory(new File(fileSourceDirectoryPath));
    fileSystemWatcher.addListener(new FileListener());
    fileSystemWatcher.start();
    return fileSystemWatcher;
  }

  @Bean
  public Executor fileHandlerExecutor(@Value("${file.handler.core.threads:4}") int coreThreads,
                                      @Value("${file.handler.max.threads:8}") int maxThreads,
                                      @Value("${file.handler.queue.size:128}") int queueSize) {
    if (coreThreads > AVAILABLE_CORES / 2) {
      log.warn("Specified value for 'file.handler.core.threads' not acceptable, reseting it to {}", (AVAILABLE_CORES / 2));
      coreThreads = AVAILABLE_CORES / 2;
    }
    if (maxThreads > AVAILABLE_CORES) {
      log.warn("Specified value for 'file.handler.max.threads' not acceptable, reseting it to {}", (AVAILABLE_CORES));
      coreThreads = AVAILABLE_CORES;
    }
    return new TPThreadPoolExecutor(coreThreads, maxThreads, KEEP_ALIVE_SECONDS, queueSize, "file-handler-thread");
  }

}

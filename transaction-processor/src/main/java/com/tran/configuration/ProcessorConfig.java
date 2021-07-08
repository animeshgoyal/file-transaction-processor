package com.tran.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tran.bootstrap.BootstrapHandler;
import com.tran.bootstrap.steps.DownStreamConfigStep;
import com.tran.bootstrap.steps.RuleConfigStep;
import com.tran.downstream.systems.DownstreamMessageSender;
import com.tran.threadpool.TPThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Rules;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Configuration
@Slf4j
public class ProcessorConfig {

  private static final int AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();
  private static final int KEEP_ALIVE_SECONDS = 10;

  @Bean
  public Executor messageHandlerExecutor(@Value("${message.handler.core.threads:8}") int coreThreads,
                                      @Value("${message.handler.max.threads:16}") int maxThreads,
                                      @Value("${message.handler.queue.size:1024}") int queueSize) {
    if (coreThreads > AVAILABLE_CORES) {
      log.warn("Specified value for 'message.handler.core.threads' not acceptable, resetting it to {}", (AVAILABLE_CORES / 2));
      coreThreads = AVAILABLE_CORES;
    }
    if (maxThreads > AVAILABLE_CORES * 2) {
      log.warn("Specified value for 'file.handler.max.threads' not acceptable, resetting it to {}", (AVAILABLE_CORES));
      coreThreads = AVAILABLE_CORES * 2;
    }
    return new TPThreadPoolExecutor(coreThreads, maxThreads, KEEP_ALIVE_SECONDS, queueSize, "message-handler-thread");
  }

  @Bean("messageRingBufferManagerThreadPool")
  public ExecutorService messageRingBufferManagerThreadPool(@Value("${message.ring.buffer.manager.core.threads:8}") int coreThreads,
                                                            @Value("${message.ring.buffer.manager.max.threads:16}") int maxThreads,
                                                            @Value("${message.ring.buffer.manager.queue.size:2048}") int queueSize) {
    return new TPThreadPoolExecutor(coreThreads, maxThreads, KEEP_ALIVE_SECONDS, queueSize,
            "message-ring-buffer");
  }

  @Bean("dsMessageRingBufferManagerThreadPool")
  public ExecutorService dsMessageRingBufferManagerThreadPool(@Value("${message.ring.buffer.manager.core.threads:8}") int coreThreads,
                                                            @Value("${message.ring.buffer.manager.max.threads:16}") int maxThreads,
                                                            @Value("${message.ring.buffer.manager.queue.size:2048}") int queueSize) {
    return new TPThreadPoolExecutor(coreThreads, maxThreads, KEEP_ALIVE_SECONDS, queueSize,
            "ds-message-ring-buffer");
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public DownStreamConfigStep downStreamConfigStep(ObjectMapper objectMapper,
                                                   @Value("${downstream.system.config.file.name}") String fileName) {
    return new DownStreamConfigStep(objectMapper, fileName);
  }

  @Bean
  public RuleConfigStep ruleConfigStep(ObjectMapper objectMapper,
                                       @Value("${rules.config.file.name}") String fileName,
                                       Rules rules,
                                       DownstreamMessageSender downstreamMessageSender) {
    return new RuleConfigStep(objectMapper, fileName, rules, downstreamMessageSender);
  }

  @Bean
  public BootstrapHandler bootstrapHandler(DownStreamConfigStep downStreamConfigStep,
                                           RuleConfigStep ruleConfigStep) {
    return BootstrapHandler.builder()
            .addStep(downStreamConfigStep)
            .addStep(ruleConfigStep).build();
  }
}

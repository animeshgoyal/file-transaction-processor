package com.tran.appevent.listener;

import com.tran.bootstrap.BootstrapHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ApplicationStartupListener {

  private final BootstrapHandler bootstrapHandler;

  public ApplicationStartupListener(BootstrapHandler bootstrapHandler) {
    this.bootstrapHandler = bootstrapHandler;
  }

  @EventListener(classes = ApplicationReadyEvent.class)
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.info("Context Ready, bootstrapping application now.");
    try {
      TimeUnit.SECONDS.sleep(30);
    } catch(InterruptedException ex) {
      log.error("Thread got interrupted",ex);
      Thread.currentThread().interrupt();
    }
    bootstrapHandler.execute();
  }
}

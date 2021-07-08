package com.file.handler.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class TPRejectedExecutionHandler implements RejectedExecutionHandler {
  private static final Logger logger = LoggerFactory.getLogger(TPRejectedExecutionHandler.class);
  private static final int WAIT_RESUBMIT = 500;

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    logger.info("Task rejected by executor, retrying in sometime");
    try {
      Thread.sleep(WAIT_RESUBMIT);
      executor.getQueue().put(r);
    } catch (InterruptedException e) {
      logger.error("Failed during reinserting the task");
      Thread.currentThread().interrupt();
    }
  }

}

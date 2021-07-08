package com.file.handler.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TPThreadPoolExecutor extends ThreadPoolExecutor {

  private static final Logger logger = LoggerFactory.getLogger(TPThreadPoolExecutor.class);

  public TPThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTimeSeconds,
                              int queueSize, String threadName) {
    super(corePoolSize, maximumPoolSize, keepAliveTimeSeconds, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize), new VAMThreadFactory(threadName));
    this.setRejectedExecutionHandler(new TPRejectedExecutionHandler());
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    super.afterExecute(r, t);
    if (t != null) {
      logger.error("Failed during execution", t);
    }
  }

  public static final class VAMThreadFactory implements ThreadFactory {
    private final String baseThreadName;
    private final AtomicInteger counter = new AtomicInteger(0);

    public VAMThreadFactory(String baseThreadName) {
      this.baseThreadName = baseThreadName;
    }

    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(r);
      t.setName(baseThreadName + "-" + counter.incrementAndGet());
      return t;
    }
  }
}

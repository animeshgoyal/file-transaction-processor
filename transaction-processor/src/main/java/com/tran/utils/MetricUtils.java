package com.tran.utils;

public final class MetricUtils {
  private MetricUtils() {
  }

  public static final String MESSAGE_RECEIVED_COUNTER = "message.incoming.counter";
  public static final String MESSAGE_FAILED_COUNTER = "message.failed.counter";
  public static final String MESSAGE_CONSUMER_TIMER = "message.consumer.timer";
  public static final String DS_MESSAGE_CONSUMER_TIMER_PREFIX = ".ds.message.consumer.timer";
  public static final String DS_FILE_MESSAGE_COUNTER = "ds.file.message.counter";
  public static final String DS_MQ_MESSAGE_COUNTER = "ds.mq.message.counter";
  public static final String DS_REST_MESSAGE_COUNTER = "ds.rest.message.counter";
}

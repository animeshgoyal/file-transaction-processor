package com.tran.ring.buffer;

import com.lmax.disruptor.EventHandler;
import com.tran.ring.buffer.consumer.IObjectConsumer;

import java.util.concurrent.ExecutorService;

public class ObjectHandler<T> implements EventHandler<ValueEvent<T>> {

  private final IObjectConsumer<T> objectConsumer;
  public final ExecutorService executorService;

  public ObjectHandler(IObjectConsumer<T> objectConsumer, ExecutorService executorService) {
    this.objectConsumer = objectConsumer;
    this.executorService = executorService;
  }

  @Override
  public void onEvent(ValueEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
    T value = event.getValue();
    executorService.submit(() -> objectConsumer.handle(value));
  }
}

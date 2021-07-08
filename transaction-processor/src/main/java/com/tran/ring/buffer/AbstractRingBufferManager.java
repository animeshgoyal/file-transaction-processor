package com.tran.ring.buffer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tran.ring.buffer.consumer.IObjectConsumer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

@Slf4j
public abstract class AbstractRingBufferManager<T> implements IRingBufferManager<T> {
  private final RingBuffer<ValueEvent<T>> ringBuffer;

  public AbstractRingBufferManager(IObjectConsumer<T> objectConsumer, String metricName,
                                   ExecutorService executorService, int ringSize) {
    log.info("Initialized RingBuffer with name {}",metricName);
    Disruptor<ValueEvent<T>> disruptor = new Disruptor<>(ValueEvent.factory(),
      ringSize, getThreadFactory(metricName), ProducerType.MULTI, new BlockingWaitStrategy());
    ringBuffer = disruptor.getRingBuffer();
    disruptor.handleEventsWith(new ObjectHandler<>(objectConsumer, executorService));
    disruptor.start();
  }

  @Override
  public void add(T t) {
    long sequence = ringBuffer.next();
    try {
      ValueEvent<T> event = ringBuffer.get(sequence);
      event.setValue(t); // this could be more complex with multiple fields
    } finally {
      ringBuffer.publish(sequence);
    }
  }

  public ThreadFactory getThreadFactory(String name) {
    return new ThreadFactoryBuilder().setNameFormat(name + "-consumer-%d").build();
  }
}

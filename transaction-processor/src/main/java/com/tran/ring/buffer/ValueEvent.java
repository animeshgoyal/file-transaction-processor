package com.tran.ring.buffer;

import com.lmax.disruptor.EventFactory;

public final class ValueEvent<T> {
  private T value;

  public T getValue() {
    return value;
  }

  public void setValue(final T value) {
    this.value = value;
  }

  public static <T> EventFactory<ValueEvent<T>> factory() {
    return ValueEvent::new;
  }
}

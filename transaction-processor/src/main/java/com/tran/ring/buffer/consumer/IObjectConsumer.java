package com.tran.ring.buffer.consumer;

public interface IObjectConsumer<T> {
  void handle(T t);
}

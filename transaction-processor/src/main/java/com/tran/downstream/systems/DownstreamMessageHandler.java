package com.tran.downstream.systems;

import com.tran.model.Transaction;

public interface DownstreamMessageHandler {
  void setup();
  void send(Transaction transaction);
  String getName();
}

package com.tran.downstream.systems;

import com.tran.model.Transaction;
import com.tran.ring.buffer.IRingBufferManager;
import com.tran.ring.buffer.objects.DownstreamMessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DownstreamMessageSender {

  private final IRingBufferManager<DownstreamMessageObject> downstreamMessageRingBufferManager;

  @Autowired
  public DownstreamMessageSender(@Qualifier("downstreamMessageRingBufferManager") IRingBufferManager<DownstreamMessageObject> downstreamMessageRingBufferManager) {
    this.downstreamMessageRingBufferManager = downstreamMessageRingBufferManager;
  }

  public void send(String systemName, Transaction transaction) {
    downstreamMessageRingBufferManager.add(DownstreamMessageObject.builder().downstreamSystemName(systemName).transaction(transaction).build());
  }
}

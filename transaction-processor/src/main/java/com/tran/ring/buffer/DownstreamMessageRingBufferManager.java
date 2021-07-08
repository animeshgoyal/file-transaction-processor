package com.tran.ring.buffer;

import com.tran.ring.buffer.consumer.IObjectConsumer;
import com.tran.ring.buffer.objects.DownstreamMessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component("downstreamMessageRingBufferManager")
public class DownstreamMessageRingBufferManager extends AbstractRingBufferManager<DownstreamMessageObject> {

  @Autowired
  public DownstreamMessageRingBufferManager(@Qualifier("downstreamSystemMessageConsumer") IObjectConsumer<DownstreamMessageObject> objectConsumer,
                                            @Qualifier("dsMessageRingBufferManagerThreadPool") ExecutorService executorService,
                                            @Value("${message.ring.buffer.size:1024}") int ringBufferSize) {
    super(objectConsumer, "ds.message.ring.buffer", executorService, ringBufferSize);
  }
}

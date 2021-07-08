package com.tran.ring.buffer;

import com.tran.ring.buffer.consumer.IObjectConsumer;
import com.tran.ring.buffer.objects.MessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component("messageRingBufferManager")
public class MessageRingBufferManager extends AbstractRingBufferManager<MessageObject> {

  @Autowired
  public MessageRingBufferManager(@Qualifier("messageConsumer") IObjectConsumer<MessageObject> objectConsumer,
                                  @Qualifier("messageRingBufferManagerThreadPool") ExecutorService executorService,
                                  @Value("${message.ring.buffer.size:1024}") int ringBufferSize) {
    super(objectConsumer, "message.ring.buffer", executorService, ringBufferSize);
  }
}

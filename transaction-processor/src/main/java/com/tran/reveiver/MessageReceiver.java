package com.tran.reveiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tran.model.Transaction;
import com.tran.ring.buffer.IRingBufferManager;
import com.tran.ring.buffer.objects.MessageObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

import static com.tran.metrics.TPCustomerReportMetricsStore.counter;
import static com.tran.utils.MetricUtils.MESSAGE_FAILED_COUNTER;
import static com.tran.utils.MetricUtils.MESSAGE_RECEIVED_COUNTER;

@Component
@Slf4j
public class MessageReceiver {

  private final ObjectMapper objectMapper;
  private final IRingBufferManager<MessageObject> messageRingBufferManager;

  @Autowired
  public MessageReceiver(ObjectMapper objectMapper,
                         @Qualifier("messageRingBufferManager") IRingBufferManager<MessageObject> messageRingBufferManager) {
    this.objectMapper = objectMapper;
    this.messageRingBufferManager = messageRingBufferManager;
  }

  @JmsListener(destination = "${tran.mq.queue.name}", containerFactory = "jmsListenerContainerFactory")
  public void receiveMessage(final Message jsonMessage) throws JMSException {
    String messageData = null;
    String response = null;
    log.debug("Message Received");
    counter(MESSAGE_RECEIVED_COUNTER).increment();
    if (jsonMessage instanceof TextMessage) {
      TextMessage textMessage = (TextMessage) jsonMessage;
      messageData = textMessage.getText();
      try {
        Transaction transaction = objectMapper.readValue(messageData, Transaction.class);
        messageRingBufferManager.add(MessageObject.builder().transaction(transaction).build());
      } catch (IOException ex) {
        log.error("Unable to consume the message from the queue.", ex);
        counter(MESSAGE_FAILED_COUNTER).increment();
      }
    }
  }

}

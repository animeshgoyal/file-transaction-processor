package com.file.handler.message.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.file.handler.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProducer {

  private final JmsTemplate jmsTemplate;
  private final String queueName;
  private final ObjectMapper objectMapper;

  public MessageProducer(JmsTemplate jmsTemplate,
                         @Value("${tran.mq.queue.name}") String queueName,
                         ObjectMapper objectMapper) {
    this.jmsTemplate = jmsTemplate;
    this.queueName = queueName;
    this.objectMapper = objectMapper;
  }

  public void sendMessage(final Transaction transaction) throws JsonProcessingException {
    log.info("Sending message to queue {}", queueName);
    String message = objectMapper.writeValueAsString(transaction);
    jmsTemplate.send(queueName, new FileHandlerMessageCreator(message));
  }

}

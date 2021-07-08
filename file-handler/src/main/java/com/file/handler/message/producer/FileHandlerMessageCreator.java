package com.file.handler.message.producer;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class FileHandlerMessageCreator implements MessageCreator {
  private final String message;
  public FileHandlerMessageCreator(String message) {
    this.message = message;
  }
  public Message createMessage(Session session) throws JMSException {
    TextMessage message = session.createTextMessage(message);
    return message;
  }
}

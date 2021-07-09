package com.file.handler.message.producer;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class FileHandlerMessageCreator implements MessageCreator {
  private final String textMessage;
  public FileHandlerMessageCreator(String textMessage) {
    this.textMessage = textMessage;
  }
  public Message createMessage(Session session) throws JMSException {
    TextMessage message = session.createTextMessage(textMessage);
    return message;
  }
}

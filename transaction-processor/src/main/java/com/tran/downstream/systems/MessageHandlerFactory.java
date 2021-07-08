package com.tran.downstream.systems;

import com.tran.model.DownStreamSystem;

public class MessageHandlerFactory {
  private static final MessageHandlerFactory factory = new MessageHandlerFactory();

  private MessageHandlerFactory() {
  }

  public static MessageHandlerFactory getInstance() {
    return factory;
  }

  public DownstreamMessageHandler getMessageHandler(DownStreamSystem system) {
    String name = system.getName();
    DownstreamMessageHandler messageHandler = null;
    switch (system.getType()) {
      case MQ:
        messageHandler = new MQProducer(system.getName(), system.getMqBrokerUrl(),
                system.getMqUserName(), system.getMqUserPassword(),
                system.getMqQueueName());
      case REST:
        messageHandler = new RESTApiInvoker(system.getName(),
                system.getRestUrl(), system.getRestUrlMethod());
      case FILE:
        messageHandler = new FileHandler(system.getName(), system.getFileLocation(),
                system.getFileName());
    }
    return messageHandler;
  }

}

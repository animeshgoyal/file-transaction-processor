package com.file.handler.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class TransactionMQConfig {

  @Bean
  public ConnectionFactory connectionFactory(@Value("${transaction.queue.broker-url}") String brokerURL,
                                             @Value("${transaction.queue.username}") String userName,
                                             @Value("${transaction.queue.password}") String password){
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(brokerURL);
    connectionFactory.setPassword(userName);
    connectionFactory.setUserName(password);
    return connectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory){
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory);
    return template;
  }
}

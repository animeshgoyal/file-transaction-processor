package com.tran.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class JMSConfig {

  @Bean
  public ConnectionFactory connectionFactory(@Value("${tran.mq.broker-url}") String brokerUrl,
                                             @Value("${tran.mq.user.name}") String userName,
                                             @Value("${tran.mq.user.password}") String password){
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(brokerUrl);
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

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setConcurrency("1-1");
    return factory;
  }
}

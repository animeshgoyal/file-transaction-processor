package com.tran.configuration;

import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfiguration {
  @Bean
  public Rules rules() {
    return new Rules();
  }

  @Bean
  public RulesEngine rulesEngine() {
    return new DefaultRulesEngine();
  }

}

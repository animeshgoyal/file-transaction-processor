package com.file.handler.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ProjectConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}

package com.tran.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tran.enums.DownStreamSystemType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DownStreamSystem {

  private DownStreamSystemType type;
  private String name;
  /** Rest Specific properties **/
  @JsonProperty("rest-url")
  private String restUrl;

  @JsonProperty("rest-url-method")
  private HttpMethod restUrlMethod;

  /** MQ Specific properties **/
  @JsonProperty("mq-broker-url")
  private String mqBrokerUrl;

  @JsonProperty("mq-queue-name")
  private String mqQueueName;

  @JsonProperty("mq-user-name")
  private String mqUserName;

  @JsonProperty("mq-user-password")
  private String mqUserPassword;

  /** File Specific properties **/
  @JsonProperty("file-location")
  private String fileLocation;

  @JsonProperty("file-name")
  private String fileName;
}

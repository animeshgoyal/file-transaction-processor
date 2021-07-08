package com.tran.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RuleConfig {
  private String name;
  private String description;
  private int priority;
  private String condition;
  @JsonProperty("downstream-system-names")
  private List<String> downstreamSystemNames;
}

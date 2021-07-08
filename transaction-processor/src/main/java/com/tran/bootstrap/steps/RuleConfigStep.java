package com.tran.bootstrap.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tran.downstream.systems.DownstreamMessageSender;
import com.tran.exception.InvalidConfigurationException;
import com.tran.model.RuleConfig;
import com.tran.rules.TransactionProcessingRule;
import lombok.AllArgsConstructor;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
public class RuleConfigStep implements IBootstrapStep {
  private final ObjectMapper objectMapper;
  private final String rulesConfigFileName;
  private final Rules rules;
  private final DownstreamMessageSender downstreamMessageSender;

  @Override
  public void process() {
    try {
      InputStream is = DownStreamConfigStep.class.getResourceAsStream(rulesConfigFileName);
      List<RuleConfig> ruleList = objectMapper.readValue(is, new TypeReference<List<RuleConfig>>() {});
      ruleList.forEach(rule -> rules.register(convertToRule(rule, rule.getDownstreamSystemNames())));
    } catch (IOException ex) {
      throw new InvalidConfigurationException(ex);
    }
  }

  public Rule convertToRule(RuleConfig rule, List<String> dsSystems) {
    return new TransactionProcessingRule(dsSystems, downstreamMessageSender)
            .name(rule.getName())
            .description(rule.getDescription())
            .priority(rule.getPriority())
            .when(rule.getCondition());
  }

}

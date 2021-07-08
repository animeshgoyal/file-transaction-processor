package com.tran.bootstrap.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tran.downstream.systems.DownstreamMessageHandler;
import com.tran.downstream.systems.MessageHandlerFactory;
import com.tran.exception.InvalidConfigurationException;
import com.tran.model.DownStreamSystem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class DownStreamConfigStep implements IBootstrapStep {

  private final ObjectMapper objectMapper;
  private final String downStreamConfigFileName;

  @Override
  public void process() {
    try {
      InputStream is = DownStreamConfigStep.class.getResourceAsStream(downStreamConfigFileName);
      List<DownStreamSystem> downStreamSystemList = objectMapper.readValue(is, new TypeReference<List<DownStreamSystem>>() {
      });
      downStreamSystemList.forEach(system -> {
        DownstreamMessageHandler messageHandler = MessageHandlerFactory.getInstance().getMessageHandler(system);
        messageHandler.setup();
      });
    } catch (IOException ex) {
      throw new InvalidConfigurationException(ex);
    }
  }
}

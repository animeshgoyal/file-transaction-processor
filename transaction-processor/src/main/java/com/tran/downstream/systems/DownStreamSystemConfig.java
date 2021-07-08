package com.tran.downstream.systems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DownStreamSystemConfig {

  private static final DownStreamSystemConfig systemConfig = new DownStreamSystemConfig();
  public final Map<String, DownstreamMessageHandler> downStreamSystems = new HashMap<>();

  private DownStreamSystemConfig() {
  }

  public static DownStreamSystemConfig getInstance() {
    return systemConfig;
  }

  public void register(String name, DownstreamMessageHandler system) {
    downStreamSystems.put(name, system);
  }

  public DownstreamMessageHandler getDownStreamMessageHandler(String name) {
    return downStreamSystems.get(name);
  }

  public List<DownstreamMessageHandler> getDownStreamMessageHandlers(List<String> names) {
    return downStreamSystems.entrySet().stream().
            filter(entry -> names.contains(entry.getKey())).map(entry -> entry.getValue()).collect(Collectors.toList());
  }
}

package com.tran.bootstrap;

import com.tran.bootstrap.steps.IBootstrapStep;
import java.util.ArrayList;
import java.util.List;

public class BootstrapHandler {

  private final List<IBootstrapStep> steps;

  private BootstrapHandler(final List<IBootstrapStep> steps) {
    this.steps = steps;
  }

  public void execute() {
    if (steps != null && !steps.isEmpty()) {
      steps.stream().forEach(IBootstrapStep::process);
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private List<IBootstrapStep> steps = new ArrayList<>();

    public Builder addStep(IBootstrapStep step) {
      steps.add(step);
      return this;
    }

    public BootstrapHandler build() {
      return new BootstrapHandler(steps);
    }
  }
}

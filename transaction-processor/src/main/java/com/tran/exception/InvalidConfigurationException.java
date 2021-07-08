package com.tran.exception;

public class InvalidConfigurationException extends RuntimeException {

  private static final long serialVersionUID = -349287396200850517L;

  public InvalidConfigurationException() {
    super("Invalid Configuration.");
  }

  public InvalidConfigurationException(String message) {
    super(message);
  }

  public InvalidConfigurationException(Throwable cause) {
    super("Invalid Configuration.", cause);
  }

  public InvalidConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }
}

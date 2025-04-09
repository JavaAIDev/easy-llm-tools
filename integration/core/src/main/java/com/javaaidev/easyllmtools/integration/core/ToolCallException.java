package com.javaaidev.easyllmtools.integration.core;

public class ToolCallException extends RuntimeException {

  private final String toolId;

  public ToolCallException(String toolId, String message, Throwable cause) {
    super(message, cause);
    this.toolId = toolId;
  }

  public String getToolId() {
    return toolId;
  }
}

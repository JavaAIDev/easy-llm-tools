package com.javaaidev.easyllmtools.integration.springai;

public class ToolCallException extends RuntimeException {

  public ToolCallException(String toolId, String message, Throwable cause) {
    super(String.format("Error when calling the tool: %s, message: %s", toolId, message), cause);
  }
}

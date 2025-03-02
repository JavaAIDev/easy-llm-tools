package com.javaaidev.easyllmtools.integration.springai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaaidev.easyllmtools.agenttoolspec.Tool;
import org.springframework.ai.model.function.FunctionCallback;

public class ToolFunctionCallback implements FunctionCallback {

  private final Tool tool;
  private final ObjectMapper objectMapper;

  public ToolFunctionCallback(Tool tool, ObjectMapper objectMapper) {
    this.tool = tool;
    this.objectMapper = objectMapper;
  }

  @Override
  public String getName() {
    return this.tool.getName();
  }

  @Override
  public String getDescription() {
    return this.tool.getDescription();
  }

  @Override
  public String getInputTypeSchema() {
    return this.tool.getParametersSchema();
  }

  @Override
  public String call(String functionInput) {
    var type = objectMapper.getTypeFactory().constructType(tool.getRequestType());
    try {
      var input = objectMapper.readValue(functionInput, type);
      var result = tool.call(input);
      return objectMapper.writeValueAsString(result);
    } catch (Exception e) {
      throw new RuntimeException("Failed to call tool", e);
    }
  }
}

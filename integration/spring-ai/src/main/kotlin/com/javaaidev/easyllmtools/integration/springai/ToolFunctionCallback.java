package com.javaaidev.easyllmtools.integration.springai;

import com.javaaidev.easyllmtools.integration.core.ToolInvoker;
import com.javaaidev.easyllmtools.llmtoolspec.Tool;
import org.springframework.ai.model.function.FunctionCallback;

public class ToolFunctionCallback implements FunctionCallback {

  private final Tool tool;
  private final ToolInvoker toolInvoker;

  public ToolFunctionCallback(Tool tool, ToolInvoker toolInvoker) {
    this.tool = tool;
    this.toolInvoker = toolInvoker;
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
    return toolInvoker.invoke(tool, functionInput);
  }
}

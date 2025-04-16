package com.javaaidev.easyllmtools.integration.springai;

import com.javaaidev.easyllmtools.integration.core.ToolInvoker;
import com.javaaidev.easyllmtools.llmtoolspec.Tool;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

public class ToolFunctionCallback implements ToolCallback {

  private final Tool tool;
  private final ToolInvoker toolInvoker;

  public ToolFunctionCallback(Tool tool, ToolInvoker toolInvoker) {
    this.tool = tool;
    this.toolInvoker = toolInvoker;
  }

  @Override
  public ToolDefinition getToolDefinition() {
    return ToolDefinition.builder()
        .name(this.tool.getName())
        .description(this.tool.getDescription())
        .inputSchema(this.tool.getParametersSchema())
        .build();
  }

  @Override
  public String call(String functionInput) {
    return toolInvoker.invoke(tool, functionInput);
  }
}

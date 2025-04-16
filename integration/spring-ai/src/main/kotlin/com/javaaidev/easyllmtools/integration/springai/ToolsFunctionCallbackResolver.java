package com.javaaidev.easyllmtools.integration.springai;

import com.javaaidev.easyllmtools.integration.core.ToolInvoker;
import com.javaaidev.easyllmtools.llmtoolspec.Tool;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.tool.resolution.ToolCallbackResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ToolsFunctionCallbackResolver implements ApplicationContextAware,
    ToolCallbackResolver {

  private final ToolCallbackResolver fallbackResolver;
  private final Map<String, Tool> toolsMap = new HashMap<>();
  private final ToolInvoker toolInvoker = new ToolInvoker();

  private static final Logger LOGGER = LoggerFactory.getLogger(ToolsFunctionCallbackResolver.class);

  public ToolsFunctionCallbackResolver(ToolCallbackResolver fallbackResolver) {
    this.fallbackResolver = fallbackResolver;
  }

  @Override
  public FunctionCallback resolve(String name) {
    var tool = toolsMap.get(name);
    if (tool != null) {
      return new ToolFunctionCallback(tool, toolInvoker);
    }
    if (fallbackResolver != null) {
      return fallbackResolver.resolve(name);
    }
    return null;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    var tools = applicationContext.getBeansOfType(Tool.class);
    LOGGER.info("Found tools: {}", tools.values().stream().map(Tool::getName).toList());
    for (Tool tool : tools.values()) {
      toolsMap.put(tool.getName(), tool);
    }
  }
}

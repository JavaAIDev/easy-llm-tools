package com.javaaidev.easyllmtools.integration.springai;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javaaidev.easyllmtools.agenttoolspec.Tool;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ToolsFunctionCallbackResolver implements ApplicationContextAware,
    FunctionCallbackResolver {

  private final FunctionCallbackResolver fallbackResolver;
  private final Map<String, Tool> toolsMap = new HashMap<>();
  private final ObjectMapper objectMapper = jacksonMapperBuilder()
      .addModule(new Jdk8Module())
      .addModule(new JavaTimeModule())
      .serializationInclusion(JsonInclude.Include.NON_ABSENT)
      .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
      .disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      .build();

  private static final Logger LOGGER = LoggerFactory.getLogger(ToolsFunctionCallbackResolver.class);

  public ToolsFunctionCallbackResolver(FunctionCallbackResolver fallbackResolver) {
    this.fallbackResolver = fallbackResolver;
  }

  @Override
  public FunctionCallback resolve(String name) {
    var tool = toolsMap.get(name);
    if (tool != null) {
      return new ToolFunctionCallback(tool, objectMapper);
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

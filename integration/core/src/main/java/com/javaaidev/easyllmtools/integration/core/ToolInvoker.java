package com.javaaidev.easyllmtools.integration.core;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javaaidev.easyllmtools.llmtoolspec.Tool;
import java.util.Map;

public class ToolInvoker {

  private final ObjectMapper objectMapper;
  private final ObjectMapper defaultObjectMapper = jacksonMapperBuilder()
      .addModule(new Jdk8Module())
      .addModule(new JavaTimeModule())
      .serializationInclusion(JsonInclude.Include.NON_ABSENT)
      .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
      .disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      .build();

  public ToolInvoker() {
    this.objectMapper = defaultObjectMapper;
  }

  public ToolInvoker(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper != null ? objectMapper : defaultObjectMapper;
  }

  public String invoke(Tool tool, Map<String, Object> input) {
    try {
      return invoke(tool, objectMapper.writeValueAsString(input));
    } catch (JsonProcessingException e) {
      throw new ToolCallException(tool.getId(), e.getMessage(), e);
    }
  }

  public String invoke(Tool tool, String toolInput) {
    try {
      var type = objectMapper.getTypeFactory().constructType(tool.getRequestType());
      var input = objectMapper.readValue(toolInput, type);
      var result = tool.call(input);
      return objectMapper.writeValueAsString(result);
    } catch (Exception e) {
      throw new ToolCallException(tool.getId(), e.getMessage(), e);
    }
  }
}

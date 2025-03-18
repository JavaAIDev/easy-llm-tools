package com.javaaidev.easyllmtools.llmtoolspec;

/**
 * Definition of a tool
 */
public interface ToolDefinition {

  /**
   * Tool id, default to tool's name
   *
   * @return tool id
   */
  default String getId() {
    return this.getName();
  }

  /**
   * Tool name
   *
   * @return tool name
   */
  String getName();

  /**
   * Tool description
   *
   * @return tool description
   */
  String getDescription();

  /**
   * JSON schema of tool's parameters
   *
   * @return Parameters schema
   */
  default String getParametersSchema() {
    return "{}";
  }

  /**
   * JSON schema of tool's return type
   *
   * @return Return type schema
   */
  default String getReturnTypeSchema() {
    return "{}";
  }

  /**
   * Examples to call this tool
   *
   * @return Examples
   */
  default String getExamples() {
    return "{}";
  }
}

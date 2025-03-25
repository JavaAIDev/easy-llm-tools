package com.javaaidev.easyllmtools.llmtoolspec;

/**
 * Factory to create a tool
 */
public interface ToolFactory {

  /**
   * ID of tool created by this factory
   *
   * @return tool id
   */
  String toolId();
}

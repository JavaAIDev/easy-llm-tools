package com.javaaidev.easyllmtools.llmtoolspec;

/**
 * Factory to create a tool without configuration
 *
 * @param <Tool> tool
 */
public interface UnconfigurableToolFactory<Tool> {

  /**
   * Create a tool
   *
   * @return the tool
   */
  Tool create();
}

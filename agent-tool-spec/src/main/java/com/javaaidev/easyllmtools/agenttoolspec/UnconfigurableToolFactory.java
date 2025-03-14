package com.javaaidev.easyllmtools.agenttoolspec;

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

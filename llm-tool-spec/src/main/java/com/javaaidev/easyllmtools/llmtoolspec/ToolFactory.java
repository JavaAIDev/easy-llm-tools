package com.javaaidev.easyllmtools.llmtoolspec;

/**
 * Factory to create tools
 *
 * @param <Tool>   Tool type
 * @param <Config> Configuration type
 */
public interface ToolFactory<Tool, Config> {

  /**
   * Create a tool
   *
   * @param config Configuration
   * @return the tool
   */
  Tool create(Config config);
}

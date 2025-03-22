package com.javaaidev.easyllmtools.llmtoolspec;

/**
 * Factory to create a tool without configuration
 *
 * @param <T> tool
 */
public interface UnconfigurableToolFactory<Request, Response, T extends Tool<? super Request, ? extends Response>> extends
    ToolFactory {

  /**
   * Create a tool
   *
   * @return the tool
   */
  T create();
}

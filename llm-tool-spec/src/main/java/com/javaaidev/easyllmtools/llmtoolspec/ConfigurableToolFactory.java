package com.javaaidev.easyllmtools.llmtoolspec;

/**
 * Factory to create tools
 *
 * @param <T>      Tool type
 * @param <Config> Configuration type
 */
public interface ConfigurableToolFactory<Request, Response, T extends Tool<? super Request, ? extends Response>, Config> extends
    ToolFactory {

  /**
   * Create a tool
   *
   * @param config Configuration
   * @return the tool
   */
  T create(Config config);
}

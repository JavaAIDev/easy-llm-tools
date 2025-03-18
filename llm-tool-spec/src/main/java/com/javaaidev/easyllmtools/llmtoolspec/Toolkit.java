package com.javaaidev.easyllmtools.llmtoolspec;

/**
 * A toolkit of tools. A toolkit is a group of tools.
 */
public interface Toolkit {

  /**
   * Name of this toolkit
   *
   * @return toolkit name
   */
  String getName();

  /**
   * Description of this toolkit
   *
   * @return toolkit description
   */
  default String getDescription() {
    return this.getName();
  }
}

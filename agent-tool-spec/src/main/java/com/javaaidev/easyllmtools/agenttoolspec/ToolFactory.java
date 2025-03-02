package com.javaaidev.easyllmtools.agenttoolspec;

public interface ToolFactory<Tool, Config> {

    Tool create(Config config);
}

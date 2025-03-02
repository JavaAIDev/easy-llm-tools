package com.javaaidev.easyllmtools.agenttoolspec;

import java.lang.reflect.Type;

public interface Tool<Request, Response, Config> extends ToolDefinition {

  Type getRequestType();

  Response call(Request request);
}

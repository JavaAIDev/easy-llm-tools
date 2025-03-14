package com.javaaidev.easyllmtools.agenttoolspec;

import java.lang.reflect.Type;

/**
 * A tool used by LLM
 *
 * @param <Request>  Request type
 * @param <Response> Response type
 */
public interface Tool<Request, Response> extends ToolDefinition {

  /**
   * Type of the request
   *
   * @return type
   */
  Type getRequestType();

  /**
   * Call this tool
   *
   * @param request Request
   * @return Response
   */
  Response call(Request request);
}

package com.javaaidev.easyllmtools.integration.mcp;

import com.javaaidev.easyllmtools.integration.core.ToolCallException;
import com.javaaidev.easyllmtools.integration.core.ToolInvoker;
import com.javaaidev.easyllmtools.llmtoolspec.Tool;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpServerFeatures.AsyncToolSpecification;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import java.util.Map;
import reactor.core.publisher.Mono;

/**
 * Helper class for integration with MCP
 */
public class McpToolHelper {

  private final ToolInvoker toolInvoker = new ToolInvoker();

  /**
   * Convert a tool to MCP sync tool
   *
   * @param tool Tool
   * @return MCP sync tool
   */
  public McpServerFeatures.SyncToolSpecification toSyncTool(Tool tool) {
    return new SyncToolSpecification(
        fromTool(tool), (exchange, args) -> callTool(tool, args)
    );
  }

  /**
   * Convert a tool to MCP async tool
   *
   * @param tool Tool
   * @return MCP async tool
   */
  public McpServerFeatures.AsyncToolSpecification toAsyncTool(Tool tool) {
    return new AsyncToolSpecification(
        fromTool(tool),
        (mcpAsyncServerExchange, args) -> Mono.fromSupplier(() -> callTool(tool, args))
    );
  }

  private McpSchema.Tool fromTool(Tool tool) {
    return new McpSchema.Tool(
        tool.getName(),
        tool.getDescription(),
        tool.getParametersSchema()
    );
  }

  private CallToolResult callTool(Tool tool, Map<String, Object> args) {
    String content;
    boolean isError = false;
    try {
      content = toolInvoker.invoke(tool, args);
    } catch (ToolCallException e) {
      content = e.getMessage();
      isError = true;
    }
    return CallToolResult.builder().addTextContent(content).isError(isError).build();
  }
}

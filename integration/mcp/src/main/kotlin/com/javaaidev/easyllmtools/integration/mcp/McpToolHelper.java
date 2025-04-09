package com.javaaidev.easyllmtools.integration.mcp;

import com.javaaidev.easyllmtools.integration.core.ToolCallException;
import com.javaaidev.easyllmtools.integration.core.ToolInvoker;
import com.javaaidev.easyllmtools.llmtoolspec.Tool;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpServerFeatures.AsyncToolSpecification;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;

public class McpToolHelper {

  private final ToolInvoker toolInvoker = new ToolInvoker();

  public McpServerFeatures.SyncToolSpecification toSyncTool(Tool tool) {
    return new SyncToolSpecification(
        fromTool(tool), (exchange, args) -> callTool(tool, args)
    );
  }

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
    return new CallToolResult(List.of(new TextContent(content)), isError);
  }
}

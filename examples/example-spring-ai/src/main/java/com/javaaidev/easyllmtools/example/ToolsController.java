package com.javaaidev.easyllmtools.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tools")
public class ToolsController {

  private final ChatClient chatClient;

  public ToolsController(ChatClient.Builder chatClientBuilder,
      SimpleLoggerAdvisor simpleLoggerAdvisor) {
    this.chatClient = chatClientBuilder.defaultAdvisors(simpleLoggerAdvisor).build();
  }

  @PostMapping("/getWeather")
  public ChatOutput getWeather(@RequestBody ChatInput chatInput) {
    var content = chatClient.prompt().user(chatInput.input()).functions("GetWeather")
        .call().content();
    return new ChatOutput(content);
  }

  @PostMapping("/exchangeRate")
  public ChatOutput exchangeRate(@RequestBody ChatInput chatInput) {
    var content = chatClient.prompt().user(chatInput.input()).functions("latestBaseCurrencyGet")
        .call().content();
    return new ChatOutput(content);
  }

  public record ChatInput(String input) {

  }

  public record ChatOutput(String output) {

  }
}

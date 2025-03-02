package com.javaaidev.easyllmtools.example;

import com.javaaidev.easyllmtools.integration.springai.ToolsFunctionCallbackResolver;
import com.javaaidev.easyllmtools.tools.mytool.GetWeather;
import com.javaaidev.easyllmtools.tools.mytool.GetWeatherFactory;
import com.javaaidev.easyllmtools.tools.mytool.api.DefaultApiAgentToolkit.AgentToolFactory_latestBaseCurrencyGet;
import com.javaaidev.easyllmtools.tools.mytool.api.DefaultApiAgentToolkit.AgentTool_latestBaseCurrencyGet;
import com.javaaidev.easyllmtools.tools.mytool.api.DefaultApiToolConfiguration;
import com.javaaidev.easyllmtools.tools.mytool.model.GetWeatherConfiguration;
import com.javaaidev.easyllmtools.tools.mytool.model.GetWeatherConfiguration.TemperatureUnit;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.model.function.DefaultFunctionCallbackResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean
  public SimpleLoggerAdvisor simpleLoggerAdvisor() {
    return new SimpleLoggerAdvisor();
  }

  @Bean
  public GetWeather getWeather() {
    var config = new GetWeatherConfiguration();
    config.setTemperatureUnit(TemperatureUnit.C);
    return new GetWeatherFactory().create(config);
  }

  @Bean
  public AgentTool_latestBaseCurrencyGet getExchangeRate() {
    return new AgentToolFactory_latestBaseCurrencyGet().create(new DefaultApiToolConfiguration());
  }

  @Bean
  public ToolsFunctionCallbackResolver toolsFunctionCallbackResolver(
      ApplicationContext applicationContext) {
    DefaultFunctionCallbackResolver fallbackResolver = new DefaultFunctionCallbackResolver();
    fallbackResolver.setApplicationContext(applicationContext);
    return new ToolsFunctionCallbackResolver(fallbackResolver);
  }
}

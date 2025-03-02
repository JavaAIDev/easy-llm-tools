package com.javaaidev.easyllmtools.tools.mytool;

import com.javaaidev.easyllmtools.agenttoolspec.ToolFactory;
import com.javaaidev.easyllmtools.tools.mytool.model.GetWeatherConfiguration;

public class GetWeatherFactory implements ToolFactory<GetWeather, GetWeatherConfiguration> {

    @Override
    public GetWeather create(final GetWeatherConfiguration config) {
        return new GetWeather(config);
    }
}

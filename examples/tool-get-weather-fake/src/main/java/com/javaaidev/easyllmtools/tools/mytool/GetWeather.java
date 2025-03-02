package com.javaaidev.easyllmtools.tools.mytool;

import com.javaaidev.easyllmtools.tools.mytool.model.GetWeatherConfiguration;
import com.javaaidev.easyllmtools.tools.mytool.model.GetWeatherParameters;
import com.javaaidev.easyllmtools.tools.mytool.model.GetWeatherReturnType;
import com.javaaidev.easyllmtools.tools.mytool.model.GetWeatherReturnType.TemperatureUnit;
import java.lang.reflect.Type;

public class GetWeather extends AbstractGetWeather {

  public GetWeather(final GetWeatherConfiguration config) {
    super(config);
  }

  @Override
  public Type getRequestType() {
    return GetWeatherParameters.class;
  }

  @Override
  public GetWeatherReturnType call(final GetWeatherParameters parameters) {
    GetWeatherReturnType result = new GetWeatherReturnType();
    result.setCondition("Sunny");
    result.setTemperature(28.0d);
    result.setTemperatureUnit(TemperatureUnit.C);
    return result;
  }
}

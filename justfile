#!/usr/bin/env just --justfile

build:
  mvn -B -pl '!:examples,!:example-spring-ai,!:get-weather' -DskipTests package

generateCodeGetWeather: build
  java -jar code-generator/cli/target/code-generator-cli.jar simple \
    --output=examples/tool-get-weather-fake \
    --artifact-id=get-weather --artifact-version=0.1.0 \
    examples/get-weather.json

installGetWeather:
  mvn -B -f examples/tool-get-weather-fake/pom.xml install

generateCodeExchangeRate: build
  java -jar code-generator/cli/target/code-generator-cli.jar openapi \
      --output=target/exchange-rate \
      --artifact-id=exchange-rate --artifact-version=0.1.0 \
      examples/exchangerate-openapi.json

installCodeExchangeRate: generateCodeExchangeRate
  mvn -B -f target/exchange-rate/pom.xml install
# Easy LLM Tools

[![build](https://github.com/JavaAIDev/easy-llm-tools/actions/workflows/build.yaml/badge.svg)](https://github.com/JavaAIDev/easy-llm-tools/actions/workflows/build.yaml)
![Maven Central Version](https://img.shields.io/maven-central/v/com.javaaidev.easyllmtools/llm-tool-spec)
[![javadoc](https://javadoc.io/badge2/com.javaaidev.easyllmtools/llm-tool-spec/javadoc.svg)](https://javadoc.io/doc/com.javaaidev.easyllmtools/llm-tool-spec)

**Easy LLM Tools** is a set of tools to create tools used by LLMs, primarily for Java and Spring AI. It consists of three parts:

- LLM tool spec
- LLM tool code generator
- LLM tool integrations

Tools play an important role in building AI applications, especially for agents. Agents use tools to access external information and perform actions.

Tools are not hard to create. Different AI frameworks and libraries have their own ways to create tools. These frameworks and tools usually take a **code-first** approach. Tools are directly built using code.

Easy LLM Tools takes a **spec-first** approach. For each tool, its spec should be create first. By creating tool specs, we can enable advanced usage scenarios of tools:

- Search of tools using RAG
- Dynamic invocation of tools
- Tool UI generation
- Documentation
- Share and reuse

See [doc](https://javaaidev.com/docs/easy-llm-tools/intro/)